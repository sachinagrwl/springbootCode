package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.*;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.exception.NoRecordsFoundException;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class VoiceNotifyServiceImpl implements VoiceNotifyService {

	@Autowired(required = true)
	VoiceNotifyRepository voiceNotifyRepo;

	@Autowired
	EquipmentLocationRepository equipmentLocationRepo;

	@Autowired
	EquipmentChassisRepository equipmentChassisRepo;

	@Autowired
	TerminalRepository terminalRepo;

	@Autowired
	VoiceNotify2Repository voiceNotify2Repo;

	@Autowired
	StationRepository stationRepo;

	@Autowired
	GenericCodeUpdateRepository genericCodeUpdateRepository;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	NotepadEntryRepository notePadRepo;

	@Autowired
	NotifyQueueRepository notifyQueueRepository;

	@Autowired
	NorfolkSouthernEventLogRepository nsEventLogRepo;

	@Autowired
	TempEVTRepository tempEVTRepository;

	public VoiceNotify getVoiceNotify(Long notifyQueueId) {

		VoiceNotify voiceNotify = voiceNotifyRepo.findByNotifyQueueId(notifyQueueId);

		if (voiceNotify != null) {
			EquipmentLocation equipmentLocation = equipmentLocationRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(
					voiceNotify.getEquipInit(), voiceNotify.getEquipNbr(), voiceNotify.getEquipTp(),
					voiceNotify.getEquipId());
			EquipmentChassis equipmentChassis = equipmentChassisRepo.findByChasInitAndChasNbrAndChasEqTpAndChasId(
					equipmentLocation.getChasInit(), equipmentLocation.getChasNbr(), equipmentLocation.getChasTp(),
					equipmentLocation.getChasId());

			if (voiceNotify.getEquipTp().equals("C")) {
				if (equipmentLocation != null) {
					voiceNotify.setChasInit(equipmentLocation.getChasInit());
					voiceNotify.setChasNbr(equipmentLocation.getChasNbr());
					if (equipmentChassis != null) {
						voiceNotify.setDmgInd(equipmentChassis.getDmgInd());
					}
				}

			}

//			LocalDateTime ldt = voiceNotify.getUpdateDtTm();
//
//			ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
//
//			ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
//			LocalDateTime swissLocal = utcZoned.toLocalDateTime();

			voiceNotify.setUpdateDtTm(voiceNotify.getUpdateDtTm());

			switch (voiceNotify.getEventCd()) {

			case "RMFC":
				voiceNotify.setEvtDesc("GROUNDED-REMOVED FROM FLATCAR");
				break;

			case "ICHR":
				voiceNotify.setEvtDesc("SHIPMENT INTERCHANGE RECEIVED");
				break;

			case "ICHD":
				voiceNotify.setEvtDesc("SHIPMENT INTERCHANGE DELIVERED");
				break;

			case "LDFC":
				voiceNotify.setEvtDesc("LOADED ON FLATCAR");
				break;

			default:
				voiceNotify.setEvtDesc("WAYBILL VOIDED");
				GenericCodeUpdate genericCodeUpdate = genericCodeUpdateRepository
						.findByGenericTableAndGenericTableCode("EVENT", voiceNotify.getEventCd());
				voiceNotify.setGenericCodeUpdate(genericCodeUpdate);
				break;

			}
			List<Station> stationName = stationRepo.findByChar8Spell(voiceNotify.getOpSpell());
			voiceNotify.setStationName(stationName.get(0));
			return voiceNotify;
		} else
			throw new NoRecordsFoundException("No records found");
	}

	@Override
	public List<VoiceNotify> getVoiceNtfyList(String notifyStat, Long termId, String notifyMethod) {

		Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());

		List<VoiceNotify> voiceNotify = voiceNotifyRepo.findByNotifyStatAndTermIdAndNotifyMethod(notifyStat, termId,
				notifyMethod);

		voiceNotify.forEach(voiceNotifyList -> {
			if (voiceNotifyList.getNotifyCustId() == null) {
				voiceNotifyList.setCustomerName("PHONE CALL");
			} else {
				Customer customer = customerRepo.findByCustomerId(voiceNotifyList.getNotifyCustId());
				voiceNotifyList.setCustomerName(customer.getCustomerName());
			}

			if (voiceNotifyList.getSvcId() != null) {
				List<NotepadEntry> notePadEntry = notePadRepo.findBySvcIdAndCreateDateTimeLessThanOrderByCreateDateTime(
						voiceNotifyList.getSvcId(), currentDateTime);
				voiceNotifyList.setNote(notePadEntry);
			} else {
				voiceNotifyList.setNote(null);
			}
		});

		if (voiceNotify.isEmpty()) {
			throw new NoRecordsFoundException("No Records Found!");
		}

		return voiceNotify;
	}

	/*
	 * In updateVoiceNotify method we are on only updating Notify Queue data and
	 * adding data in TEMP_EVT table. We are not doing any modifications in
	 * Voice_Notify_V1 Table
	 */
	@Override
	public VoiceNotify updateVoiceNotify(VoiceNotify voiceNotify, Map<String, String> headers) {
		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		if (extensionSchema == null) {
			throw new NullPointerException("Extension Schema should not be null, empty or blank.");
		}
		if (voiceNotifyRepo.existsByNotifyQueueId(voiceNotify.getNotifyQueueId())) {
			VoiceNotify existingVoiceNotify = voiceNotifyRepo.findByNotifyQueueId(voiceNotify.getNotifyQueueId());
			if (voiceNotify.getNotifyStat().equals("SENT")) {
				if (StringUtils.isNotEmpty(voiceNotify.getPersonField())) {
					try {
						NotifyQueue notifyQueue = notifyQueueRepository
								.findByNtfyQueueId(voiceNotify.getNotifyQueueId());
						notifyQueue.setNotifyStat("CONF");
						notifyQueue.setPersonNotified(voiceNotify.getPersonField());
						notifyQueue.setNotifyReasonCode("SEND CONFIRMED");
						notifyQueueRepository.save(notifyQueue);
					} catch (Exception ex) {
						throw new RecordNotAddedException(CommonConstants.UPDATE_FAILED_MESSAGE);
					}
				} else {
					try {
						NotifyQueue notifyQueue = notifyQueueRepository
								.findByNtfyQueueId(voiceNotify.getNotifyQueueId());
						notifyQueue.setNotifyStat("CONF");
						notifyQueue.setNotifyReasonCode("SEND CONFIRMED");
						notifyQueueRepository.save(notifyQueue);
					} catch (Exception ex) {
						throw new RecordNotAddedException(CommonConstants.UPDATE_FAILED_MESSAGE);
					}
				}
				if (StringUtils.equalsAnyIgnoreCase("RMFC", voiceNotify.getEventCd())
						|| StringUtils.equalsAnyIgnoreCase("RCOV", voiceNotify.getEventCd())
						|| StringUtils.equalsAnyIgnoreCase("NTFY", voiceNotify.getEventCd())) {
					if (voiceNotify.getNotifyCustId() != null && voiceNotify.getNotifyCustId() >= 0) {
						List<NorfolkSouthernEventLog> listEventLog = nsEventLogRepo
								.findByServiceIdAndEvtCd(voiceNotify.getSvcId(), "NOPA");
						TempEVT tempEVT = new TempEVT();
						if (!listEventLog.isEmpty()) {
							tempEVT.setEvtCd("NOPA");
						} else {
							tempEVT.setEvtCd("RNOT");
						}
						tempEVT.setReasonCode("VOIC");
						tempEVT.setEquipInit(voiceNotify.getEquipInit());
						tempEVT.setEquipNbr(voiceNotify.getEquipNbr());
						tempEVT.setEquipTp(voiceNotify.getEquipTp());
						tempEVT.setEquipId(voiceNotify.getEquipId());
						tempEVT.setSvcId(voiceNotify.getSvcId());
						tempEVT.setLeInd(voiceNotify.getEmptyCd());
						if (voiceNotify.getNotifyCustId() != null) {
							tempEVT.setCustId(voiceNotify.getNotifyCustId());
						} else {
							tempEVT.setCustId(voiceNotify.getShipCust());
						}
						tempEVT.setTermId(voiceNotify.getTermId());
						tempEVT.setQueStat("R");
						tempEVT.setEvtdtTm(Date.valueOf(LocalDate.now()));
						tempEVT.setCreateUserId(headers.get(CommonConstants.USER_ID));
						tempEVT.setUpdateUserId(headers.get(CommonConstants.USER_ID));
						tempEVT.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
						tempEVT.setUversion("!");
						Long evtlogId = tempEVTRepository.SGK();
						tempEVT.setEvtlogId(evtlogId);
						try {
							tempEVTRepository.save(tempEVT);
						} catch (Exception e) {
							throw new RecordNotAddedException("Temp Event record not added!");
						}
						TempEVT tempEVT2 = new TempEVT();
						tempEVT2.setEvtCd("CONF");
						tempEVT2.setEquipInit(voiceNotify.getEquipInit());
						tempEVT2.setEquipNbr(voiceNotify.getEquipNbr());
						tempEVT2.setEquipTp(voiceNotify.getEquipTp());
						tempEVT2.setEquipId(voiceNotify.getEquipId());
						tempEVT2.setSvcId(voiceNotify.getSvcId());
						tempEVT2.setLeInd(voiceNotify.getEmptyCd());
						if (voiceNotify.getNotifyCustId() != null) {
							tempEVT2.setCustId(voiceNotify.getNotifyCustId());
						} else {
							tempEVT2.setCustId(voiceNotify.getShipCust());
						}
						tempEVT2.setTermId(voiceNotify.getTermId());
						tempEVT2.setQueStat("R");
						tempEVT2.setEvtdtTm(Date.valueOf(LocalDate.now()));
						tempEVT2.setCreateUserId(headers.get(CommonConstants.USER_ID));
						tempEVT2.setUpdateUserId(headers.get(CommonConstants.USER_ID));
						tempEVT2.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
						tempEVT2.setUversion("!");
						Long evtlogId2 = tempEVTRepository.SGK();
						tempEVT2.setEvtlogId(evtlogId2);
						try {
							tempEVTRepository.save(tempEVT2);
						} catch (Exception e) {
							throw new RecordNotAddedException("Temp Event record not added!");
						}
					}
				} else {
					if (voiceNotify.getNotifyCustId() != null && voiceNotify.getNotifyCustId() >= 0) {
						TempEVT tempEVT3 = new TempEVT();
						tempEVT3.setEquipInit(voiceNotify.getEquipInit());
						tempEVT3.setEquipNbr(voiceNotify.getEquipNbr());
						tempEVT3.setEquipTp(voiceNotify.getEquipTp());
						tempEVT3.setEquipId(voiceNotify.getEquipId());
						tempEVT3.setSvcId(voiceNotify.getSvcId());
						tempEVT3.setLeInd(voiceNotify.getEmptyCd());
						tempEVT3.setCustId(voiceNotify.getNotifyCustId());
						tempEVT3.setTermId(voiceNotify.getTermId());
						tempEVT3.setQueStat("R");
						tempEVT3.setEvtdtTm(Date.valueOf(LocalDate.now()));
						tempEVT3.setEvtCd("NOTV");
						tempEVT3.setCreateUserId(headers.get(CommonConstants.USER_ID));
						tempEVT3.setUpdateUserId(headers.get(CommonConstants.USER_ID));
						tempEVT3.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
						tempEVT3.setUversion("!");
						Long evtlogId3 = tempEVTRepository.SGK();
						tempEVT3.setEvtlogId(evtlogId3);
						try {
							tempEVTRepository.save(tempEVT3);
						} catch (Exception e) {
							throw new RecordNotAddedException("Temp Event record not added!");
						}
					}
				}
			}
			if (StringUtils.equalsAnyIgnoreCase("FAIL", voiceNotify.getNotifyStat())) {
				try {
					NotifyQueue notifyQueue = notifyQueueRepository.findByNtfyQueueId(voiceNotify.getNotifyQueueId());
					notifyQueue.setNotifyStat(voiceNotify.getNotifyStat());
					notifyQueueRepository.save(notifyQueue);
				} catch (Exception ex) {
					throw new RecordNotAddedException(CommonConstants.UPDATE_FAILED_MESSAGE);
				}
			}
			return existingVoiceNotify;
		} else
			throw new NoRecordsFoundException(CommonConstants.NO_RECORD_FOUND_MESSAGE);
	}

}
