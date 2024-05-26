package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.*;
import com.nscorp.obis.dto.DestinationTerminalNotifyProfileDTO;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.dto.mapper.NotifyProfileMethodMapper;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.DestinationTerminalNotifyProfileRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.NotifyProfileMethodRepository;
import com.nscorp.obis.repository.TerminalRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DestinationTerminalNotifyProfileServiceImpl implements DestinationTerminalNotifyProfileService {
	
	@Autowired
	DestinationTerminalNotifyProfileRepository destRepository;

	@Autowired
	TerminalRepository terminalRepository;
	
	@Autowired
	NotifyProfileMethodRepository notifyRepository;
	
	@Autowired
	GenericCodeUpdateRepository genericCodeRepository;

	@Override
	public List<DestinationTerminalNotifyProfile> fetchDestinationTerminalProfilesByTermId(Long terminalId)
			throws SQLException {
		if (terminalId != null) {
			if (!terminalRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id :" + terminalId);
			}
		}
		List<DestinationTerminalNotifyProfile> terminalNotifyProfiles = destRepository.findByTerminalId(terminalId);
		if (terminalNotifyProfiles.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for this terminal :" + terminalId);
		}
		return terminalNotifyProfiles;
	}

	/* Service for Adding values */
	@Override
	public DestinationTerminalNotifyProfile insertDestinationTerminalNotifyProfile(
			DestinationTerminalNotifyProfile destinationTerminalNotifyProfile, Map<String, String> headers,
			@NotNull(message = "Terminal Id should be proviced.") @Min(value = 1, message = "Terminal Id value must be greater than 0") @Digits(integer = 15, fraction = 0, message = "Terminal Id should not have more than 15 digits.") Long terminalId) {
		if (terminalId != null) {
			if (!terminalRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id :" + terminalId);
			}
			destinationTerminalNotifyProfile.setTerminalId(terminalId);
		}
		
		Long id = (long) (Math.random() * Math.pow(10, 15));
		if (destinationTerminalNotifyProfile.getNotifyProfileId() == null)
			destinationTerminalNotifyProfile.setNotifyProfileId(id);
		if (destinationTerminalNotifyProfile.getEventCode() != null) {
			if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EVENT",
					destinationTerminalNotifyProfile.getEventCode())) {
				throw new InvalidDataException("Invalid Event Code");
			}
			destinationTerminalNotifyProfile.setEventCode(destinationTerminalNotifyProfile.getEventCode().trim());
		} else {
			throw new InvalidDataException("Event code should not be null");
		}
		if (destRepository.existsByNotifyProfileId(destinationTerminalNotifyProfile.getNotifyProfileId())) {
			throw new RecordAlreadyExistsException(
					"This terminal notify profile already exists with notify Profile Id :"
							+ destinationTerminalNotifyProfile.getNotifyProfileId());
		}
		UserId.headerUserID(headers);

		destinationTerminalNotifyProfile.setCreateUserId(headers.get(CommonConstants.USER_ID));
		destinationTerminalNotifyProfile.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		destinationTerminalNotifyProfile.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		destinationTerminalNotifyProfile.setUversion("!");
		DestinationTerminalNotifyProfile destinationTerminalProfile = destRepository
				.save(destinationTerminalNotifyProfile);
		if (destinationTerminalProfile == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}
		return destinationTerminalProfile;
	}

	/* Service for Updating values */
	@Override
	public DestinationTerminalNotifyProfile updateDestinationTerminalProfile(
			@Valid DestinationTerminalNotifyProfileDTO destinationTerminalNotifyProfileDTO, Map<String, String> headers)
			throws SQLException {
		Long profileId = destinationTerminalNotifyProfileDTO.getNotifyProfileId();
		if (profileId == null) {
			throw new InvalidDataException("Notify Profile Id should not be null");
		}
		if (!destRepository.existsByNotifyProfileId(profileId)) {
			throw new NoRecordsFoundException("No record found for this profile Id : " + profileId);
		}
		DestinationTerminalNotifyProfile destinationTerminalNotifyProfile = destRepository
				.findByNotifyProfileId(profileId);
		UserId.headerUserID(headers);
		destinationTerminalNotifyProfile.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		if (destinationTerminalNotifyProfileDTO.getShift() != null) {
			destinationTerminalNotifyProfile.setShift(destinationTerminalNotifyProfileDTO.getShift());
		}

		if (destinationTerminalNotifyProfileDTO.getDayOfWeek() != null) {
			destinationTerminalNotifyProfile.setDayOfWeek(destinationTerminalNotifyProfileDTO.getDayOfWeek());
		}

		if (destinationTerminalNotifyProfileDTO.getEventCode() != null) {
			if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EVENT",
					destinationTerminalNotifyProfileDTO.getEventCode())) {
				throw new InvalidDataException("Invalid Event Code");
			}
			destinationTerminalNotifyProfile.setEventCode(destinationTerminalNotifyProfileDTO.getEventCode());
		}

		List<NotifyProfileMethodDTO> notifyProfileMethodsDto = destinationTerminalNotifyProfileDTO
				.getNotifyProfileMethods();
		if(notifyProfileMethodsDto == null) {
			destinationTerminalNotifyProfile.setNotifyProfileMethods(null);
			destRepository.save(destinationTerminalNotifyProfile);
			return destinationTerminalNotifyProfile;
		}
		for (NotifyProfileMethodDTO notifyProfileMethodDTO : notifyProfileMethodsDto) {

			if (notifyProfileMethodDTO.getNotificationName() == null
					|| notifyProfileMethodDTO.getNotificationName().trim().equals("")) {
				throw new InvalidDataException("Notification Name should not be null");
			}
			if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.EMAIL) {
				throw new InvalidDataException("EMAIL Notification Method is not valid");
			}

			if (notifyProfileMethodDTO.getNotificationName() != null) {
				if (notifyProfileMethodDTO.getNotificationMethod() == null) {
					throw new InvalidDataException("Must Specify Notification Method.");
				}
				
			}
			if (notifyProfileMethodDTO.getNotificationMethod() != null) {
				if (notifyProfileMethodDTO.getNotificationOrder() == null) {
					throw new InvalidDataException("Must Specify Notification Order");
				}
				if (notifyProfileMethodDTO.getNotificationType() == null) {
					throw new InvalidDataException("Must Specify Notification Type.");
				}
			}

			if (notifyProfileMethodDTO.getNotificationType() != null) {
				if (notifyProfileMethodDTO.getNotificationMethod() == null) {
					throw new InvalidDataException("Must Specify Notification Method.");
				}
			}

			if(notifyProfileMethodDTO.getNotificationOrder() == NotificationOrder.SECONDARY) {
				String Rmfc = "RMFC", Rcov = "RCOV";
				if ((!destinationTerminalNotifyProfileDTO.getEventCode().equals(Rmfc))
						&& (!destinationTerminalNotifyProfileDTO.getEventCode().equals(Rcov))) {
					throw new InvalidDataException(
							"Secondary Notification order is only valid for event code RMFC or RCOV");
				}
			}

			if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.EDI) {
				if (notifyProfileMethodDTO.getEdiBox() == null ||  notifyProfileMethodDTO.getEdiBox().trim().equals("")) {
					throw new InvalidDataException("EDI Box is required field for Notification Method EDI");
				}
				if (notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_SUMMARY
						|| notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_SUMMARY) {
					throw new InvalidDataException("Notification Type not valid for Method EDI.");
				}
				if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EDI_RSN",
						destinationTerminalNotifyProfileDTO.getEventCode())) {
					throw new InvalidDataException("Notification Method EDI not valid for event code "
							+ destinationTerminalNotifyProfileDTO.getEventCode());
				}
			} else if (notifyProfileMethodDTO.getEdiBox() != null) {
				throw new InvalidDataException("Notify Method must be EDI before entering EDI box fields");
			}

			if ((notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_DETAIL)
					|| (notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_SUMMARY)) {
				String Rmfc = "RMFC";
				if (destinationTerminalNotifyProfileDTO.getEventCode().equals(Rmfc)) {
					throw new InvalidDataException(
							"Change notificationtype , Event code should not be RMFC for notificationType Deferred Details or Deferred Summary.");
				}
			}

			if ((notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_DETAIL)
					|| (notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_SUMMARY)) {
				String Rmfc = "RMFC", Irfc = "IRFC";
				if ((!destinationTerminalNotifyProfileDTO.getEventCode().equals(Rmfc))
						&& (!destinationTerminalNotifyProfileDTO.getEventCode().equals(Irfc))) {
					throw new InvalidDataException(
							"Change notificationtype , Event code should be RMFC or IRFC for notificationType Delay Details or Delay Summary.");
				}
			}

			if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.VOICE
					|| notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.FAX) {
				if (notifyProfileMethodDTO.getNotifyAreaCode() == null
						&& notifyProfileMethodDTO.getNotifyPrefix() == null
						&& notifyProfileMethodDTO.getNotifySuffix() == null) {
					throw new InvalidDataException("Must Enter Telephone No");
				}
				if (notifyProfileMethodDTO.getNotifyAreaCode() == null
						|| notifyProfileMethodDTO.getNotifyPrefix() == null
						|| notifyProfileMethodDTO.getNotifySuffix() == null) {
					throw new InvalidDataException("Phone Number Incomplete");
				}
			}

			if (notifyProfileMethodDTO.getNotifyMethodId() == null) {
				Long id = (long) (Math.random() * Math.pow(10, 15));
				notifyProfileMethodDTO.setNotifyMethodId(id);
				NotifyProfileMethod method = NotifyProfileMethodMapper.INSTANCE
						.notifyProfileMethodDTOToNotifyProfileMethod(notifyProfileMethodDTO);
				notifyRepository.save(method);
			}
		}
		List<NotifyProfileMethod> notifyProfileMethods = new ArrayList<>();
		notifyProfileMethods = notifyProfileMethodsDto.stream()
				.map(NotifyProfileMethodMapper.INSTANCE::notifyProfileMethodDTOToNotifyProfileMethod)
				.collect(Collectors.toList());
		destinationTerminalNotifyProfile.setNotifyProfileMethods(notifyProfileMethods);
		
		/* Changing UVersion value during each update */
		if(StringUtils.isNotEmpty(destinationTerminalNotifyProfile.getUversion())) {
			destinationTerminalNotifyProfile.setUversion(
					Character.toString((char) ((((int)destinationTerminalNotifyProfile.getUversion().charAt(0) - 32) % 94) + 33)));
		} else{
			destinationTerminalNotifyProfile.setUversion("!");
		}
		
		destRepository.save(destinationTerminalNotifyProfile);
		return destinationTerminalNotifyProfile;
	}

	@Transactional
	@Override
	public void deleteDestinationTerminalProfile(
			@Valid DestinationTerminalNotifyProfile destinationTerminalNotifyProfiles) {
		Long profileId = destinationTerminalNotifyProfiles.getNotifyProfileId();
		if (profileId == null) {
			throw new InvalidDataException("Notify Profile Id should not be null");
		}
		if (destRepository.existsByNotifyProfileId(destinationTerminalNotifyProfiles.getNotifyProfileId())) {
			destRepository.deleteByNotifyProfileId(destinationTerminalNotifyProfiles.getNotifyProfileId());
		} else {
			String errMsg = destinationTerminalNotifyProfiles.getNotifyProfileId() + " Record Not Found!";
			throw new RecordNotDeletedException(errMsg);
		}

		return;
	}

}
