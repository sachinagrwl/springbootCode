package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.controller.NotifyQueueController;
import com.nscorp.obis.domain.CustomerNotifyProfile;
import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.NorfolkSouthernEventLog;
import com.nscorp.obis.domain.NotifyQueue;
import com.nscorp.obis.domain.NotifyQueueRetry;
import com.nscorp.obis.domain.ReNotifyView;
import com.nscorp.obis.domain.Shift;
import com.nscorp.obis.domain.Shipment;
import com.nscorp.obis.dto.NotifyQueueDTO;
import com.nscorp.obis.dto.NotifyQueueUpdatedDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerIndexRepository;
import com.nscorp.obis.repository.CustomerNicknameRepository;
import com.nscorp.obis.repository.CustomerNotifyProfileRepository;
import com.nscorp.obis.repository.NorfolkSouthernEventLogRepository;
import com.nscorp.obis.repository.NotifyQueueRepository;
import com.nscorp.obis.repository.ReNotifyViewRepository;
import com.nscorp.obis.repository.ShipmentRepository;

@Service
@Transactional
public class NotifyQueueServiceImpl implements NotifyQueueService {
	private static final Logger logger = LoggerFactory.getLogger(NotifyQueueController.class);
	@Autowired
	NotifyQueueRepository notifyQueueRepository;

	@Autowired
	ReNotifyViewRepository reNotifyViewRepository;

	@Autowired
	NorfolkSouthernEventLogRepository nsEventLogRepo;

	@Autowired
	ShipmentRepository shipmentRepository;
	@Autowired
	CustomerIndexRepository customerIndexRepository;
	@Autowired
	CustomerNotifyProfileRepository customerNotifyProfileRepository;
	@Autowired
	CustomerNicknameRepository customerNicknameRepository;

//	@Autowired
//	NotifyQueueRetryRepository notifyQueueRetryRepo;

	public List<NotifyQueue> updateNotifyQueue(NotifyQueueUpdatedDTO notifyQueueUpdatedDTO,
			Map<String, String> headers) {
		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		List<NotifyQueue> notifyQueueList = new ArrayList<>();
		if (notifyQueueUpdatedDTO.isFlag()) {
			// Bulk Renotify on customer Name
			List<ReNotifyView> reNotifyViewList = reNotifyViewRepository.searchAllByTermIdAndCustomerName(
					notifyQueueUpdatedDTO.getTermId(), notifyQueueUpdatedDTO.getCustomerName());
			if (reNotifyViewList.size() > 0) {
				for (ReNotifyView notify : reNotifyViewList) {
					NotifyQueue notifyQueueObjData = notifyQueueRepository.findByNtfyQueueId(notify.getNtfyQueueId());
					if (notifyQueueObjData != null) {
						notifyQueueObjData.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
						notifyQueueObjData.setUpdateUserId(headers.get(CommonConstants.USER_ID));
						notifyQueueObjData.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
						if (StringUtils.isNotEmpty(notifyQueueObjData.getUversion())) {
							notifyQueueObjData.setUversion(Character.toString(
									(char) ((((int) notifyQueueObjData.getUversion().charAt(0) - 32) % 94) + 33)));
						}
						notifyQueueObjData.setRenotifyCnt(notify.getRenotifyCnt() + 1);
						notifyQueueObjData.setNotifyStat("SEND");
						notifyQueueList.add(notifyQueueRepository.save(notifyQueueObjData));
					} else {
						throw new NoRecordsFoundException("Record Not Found!");
					}
				}
			} else {
				throw new NoRecordsFoundException("Record Not Found!");
			}
		} else {
			for (NotifyQueueDTO notify : notifyQueueUpdatedDTO.getNotifyQueueObjDto()) {
				NotifyQueue notifyQueueObjData = notifyQueueRepository.findByNtfyQueueId(notify.getNtfyQueueId());
				if (notifyQueueObjData != null) {
					notifyQueueObjData.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
					notifyQueueObjData.setUpdateUserId(headers.get(CommonConstants.USER_ID));
					if (StringUtils.isNotEmpty(notifyQueueObjData.getUversion())) {
						notifyQueueObjData.setUversion(Character.toString(
								(char) ((((int) notifyQueueObjData.getUversion().charAt(0) - 32) % 94) + 33)));
					}
					notifyQueueObjData.setRenotifyCnt(notify.getRenotifyCnt());
					notifyQueueObjData.setNotifyStat(notify.getNotifyStat());
					if (notify.getNotifyStat().equalsIgnoreCase("VOID")) {
						notifyQueueObjData.setEventCode("VOID");
					}
					if (StringUtils.isNotBlank(extensionSchema)) {
						notifyQueueObjData.setUpdateExtensionSchema(extensionSchema.toUpperCase());
					} else {
						throw new NullPointerException("Extension Schema should not be null, empty or blank");
					}
					notifyQueueList.add(notifyQueueRepository.save(notifyQueueObjData));
				} else {
					throw new NoRecordsFoundException("Record Not Found!");
				}
			}
		}
		return notifyQueueList;
	}

	@Override
	public NotifyQueue addNotifyQueue(NotifyQueue notifyQueue, Map<String, String> headers) {
		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		if (extensionSchema == null) {
			throw new NullPointerException("Extension Schema should not be null, empty or blank.");
		}
		String userId = headers.get(CommonConstants.USER_ID);
		Long generatedQueueId = notifyQueueRepository.SGKLong();
		notifyQueue.setNotifyQueueId(generatedQueueId);
		notifyQueue.setCreateUserId(userId.toUpperCase());
		notifyQueue.setUpdateUserId(userId.toUpperCase());
		notifyQueue.setUpdateExtensionSchema(extensionSchema);
		notifyQueue.setUversion("!");
		return notifyQueueRepository.save(notifyQueue);
	}

	@Override
	public NotifyQueueRetry updateNotifyQueueRetry(@Valid @NotNull NotifyQueueRetry notifyQueueRetry,
			Map<String, String> headers) {

		UserId.headerUserID(headers);

		if (notifyQueueRepository.existsByEvtLogId(notifyQueueRetry.getNorfolkSouthernEventLog().getEventLogId())) {

			NorfolkSouthernEventLog nsEventLog = nsEventLogRepo
					.findByEventLogId(notifyQueueRetry.getNorfolkSouthernEventLog().getEventLogId());

			if ((nsEventLog.getEvtCd().equals("RMFC") || nsEventLog.getEvtCd().equals("NTFY")
					|| nsEventLog.getEvtCd().equals("RCOV"))
					&& nsEventLog.getEquipmentInit().equals("MSKU")
					&& nsEventLog.getEquipmentId()
							.equals(notifyQueueRetry.getNorfolkSouthernEventLog().getEquipmentId())
					&& nsEventLog.getEquipmentType()
							.equals(notifyQueueRetry.getNorfolkSouthernEventLog().getEquipmentType())
					&& nsEventLog.getEquipmentNumber()
							.equals(notifyQueueRetry.getNorfolkSouthernEventLog().getEquipmentNumber())) {

				List<NotifyQueue> existingNotifyQueue = notifyQueueRepository
						.findByEvtLogId(notifyQueueRetry.getNorfolkSouthernEventLog().getEventLogId());

				List<NotifyQueue> updatedNotifyQueues = new ArrayList<>();

				try {

					existingNotifyQueue.forEach(notifyQueue -> {
						notifyQueue.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
						notifyQueue.setUversion(null);
						notifyQueue.setNotifyStat("VOID");
						notifyQueue.setEventCode("VOID");

						updatedNotifyQueues.add(notifyQueueRepository.save(notifyQueue));

					});

					notifyQueueRetry.setNotifyQueueList(updatedNotifyQueues);

				} catch (Exception e) {
					throw new RecordNotAddedException("Update Notify Queue Failed");

				}

			} else
				throw new NoRecordsFoundException("Record Not Found!");

		} else
			throw new NoRecordsFoundException("Record with Event Log Id: "
					+ notifyQueueRetry.getNorfolkSouthernEventLog().getEventLogId() + " not Found!");

		return notifyQueueRetry;

	}

	@SuppressWarnings("deprecation")
	@Override
	public NotifyQueueRetry getNotifyQueueRetry(Long notifyQueueId) {
		logger.info(":::getNotifyQueueRetry Method Starts:::");
		NotifyQueueRetry notifyQueueRetry = new NotifyQueueRetry();
		NotifyQueue ntfyQueue = notifyQueueRepository.findByNtfyQueueId(notifyQueueId);
		if (ntfyQueue == null) {
			throw new NoRecordsFoundException("No Records Found!");
		}
		notifyQueueRetry.setNotifyQueue(ntfyQueue);
		if (ntfyQueue.getEvtLogId() != null && nsEventLogRepo.existsById(ntfyQueue.getEvtLogId())) {
			notifyQueueRetry.setNorfolkSouthernEventLog(nsEventLogRepo.findById(ntfyQueue.getEvtLogId()).get());
		}
		if (notifyQueueRetry.getNorfolkSouthernEventLog() != null
				&& notifyQueueRetry.getNorfolkSouthernEventLog().getServiceId() != null
				&& shipmentRepository.existsBySvcId(notifyQueueRetry.getNorfolkSouthernEventLog().getServiceId())) {
			Shipment shipmentRetrived = shipmentRepository
					.findBySvcId(notifyQueueRetry.getNorfolkSouthernEventLog().getServiceId());
			if (StringUtils.isNotBlank(shipmentRetrived.getShipStat())) {
				notifyQueueRetry.setShipment(shipmentRetrived);
				notifyQueueRetry.setCustomerNickname(customerNicknameRepository.findByCustomerNicknameAndTerminalId(
						"" + shipmentRetrived.getNtfyOvrdAreaCd() + shipmentRetrived.getNtfyOvrdExch()
								+ shipmentRetrived.getNtfyOvrdBase(),
						notifyQueueRetry.getNorfolkSouthernEventLog().getTerminalId()));

			}
		}
		if (notifyQueueRetry.getShipment() != null && notifyQueueRetry.getShipment().getCustomerToNotify() != null
				&& customerIndexRepository.existsById(notifyQueueRetry.getShipment().getCustomerToNotify())) {
			notifyQueueRetry.setCustomerIndex(
					customerIndexRepository.findByCustomerId(notifyQueueRetry.getShipment().getCustomerToNotify()));
		}
		if (notifyQueueRetry.getCustomerIndex() != null
				&& notifyQueueRetry.getNorfolkSouthernEventLog().getLclDateTime() != null
				&& notifyQueueRetry.getNorfolkSouthernEventLog().getTerminalId() != null) {
			String evtCd = StringUtils.equalsIgnoreCase("NTFY",
					notifyQueueRetry.getNorfolkSouthernEventLog().getEvtCd()) ? "RMFC"
							: notifyQueueRetry.getNorfolkSouthernEventLog().getEvtCd();
			int dayOfWeek = notifyQueueRetry.getNorfolkSouthernEventLog().getLclDateTime().getDay();
			int hours = notifyQueueRetry.getNorfolkSouthernEventLog().getLclDateTime().getHours();
			int shift = (hours > 7 && hours <= 15) ? 1 : (hours > 15 && hours <= 23) ? 2 : 3;
			List<CustomerNotifyProfile> customerNotifyProfiles = customerNotifyProfileRepository
					.findByCustomer_CustomerIdAndEventCodeAndNotifyTerminalIdOrderByNotifyTerminalIdDesc(
							notifyQueueRetry.getCustomerIndex().getCustomerId(), evtCd,
							notifyQueueRetry.getNorfolkSouthernEventLog().getTerminalId());
			List<CustomerNotifyProfile> customerNotifyProfileMatched = customerNotifyProfiles.stream()
					.filter(cust -> cust.getDayOfWeek().contains(DayOfWeek.of(dayOfWeek))
							&& cust.getShift().contains(Shift.getShiftByValue(shift)))
					.collect(Collectors.toList());
			notifyQueueRetry.setCustomerNotifyProfileList(customerNotifyProfileMatched);
		}
		logger.info(":::getNotifyQueueRetry Method Ends:::");
		return notifyQueueRetry;
	}
}
