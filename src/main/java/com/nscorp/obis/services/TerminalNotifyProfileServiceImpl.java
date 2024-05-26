package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.*;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.dto.TerminalNotifyProfileDTO;
import com.nscorp.obis.dto.mapper.NotifyProfileMethodMapper;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.NotifyProfileMethodRepository;
import com.nscorp.obis.repository.TerminalNotifyProfileRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TerminalNotifyProfileServiceImpl implements TerminalNotifyProfileService {
	
	@Autowired
	TerminalNotifyProfileRepository repository;

	@Autowired
	TerminalRepository terminalRepository;
	
	@Autowired
	NotifyProfileMethodRepository notifyRepository;
	
	@Autowired
	GenericCodeUpdateRepository genericCodeRepository;

	@Override
	public List<TerminalNotifyProfile> fetchTerminalProfilesByTermId(@Valid Long terminalId) throws SQLException {
		if (terminalId != null) {
			if (!terminalRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id :" + terminalId);
			}
		}
		List<TerminalNotifyProfile> terminalNotifyProfiles = repository.findByTerminalId(terminalId);
		if (terminalNotifyProfiles.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for this terminal.");
		}
		return terminalNotifyProfiles;
	}

	/* This Method Is To Insert Values Based On a TerminalId */
	@Override
	public TerminalNotifyProfile insertTerminalNotifyProfile(TerminalNotifyProfile terminalNotifyProfile,
			Map<String, String> headers, Long terminalId) {

		if (terminalId != null) {
			if (!terminalRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id :" + terminalId);
			}
			terminalNotifyProfile.setTerminalId(terminalId);
		}
		
		Long id = (long) (Math.random() * Math.pow(10, 15));
		if (terminalNotifyProfile.getNotifyProfileId() == null)
			terminalNotifyProfile.setNotifyProfileId(id);
		if (terminalNotifyProfile.getEventCode() != null) {
			if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EVENT",
					terminalNotifyProfile.getEventCode())) {
				throw new InvalidDataException("Invalid Event Code");
			}
			terminalNotifyProfile.setEventCode(terminalNotifyProfile.getEventCode().trim());
		} else {
			throw new InvalidDataException("Event code should not be null");
		}
		if (repository.existsByNotifyProfileId(terminalNotifyProfile.getNotifyProfileId())) {
			throw new RecordAlreadyExistsException(
					"This terminal notify profile already exists with notify Profile Id :"
							+ terminalNotifyProfile.getNotifyProfileId());
		}
		UserId.headerUserID(headers);

		terminalNotifyProfile.setCreateUserId(headers.get(CommonConstants.USER_ID));
		terminalNotifyProfile.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		terminalNotifyProfile.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		terminalNotifyProfile.setUversion("!");
		TerminalNotifyProfile terminalProfile = repository.save(terminalNotifyProfile);
		if (terminalProfile == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}
		return terminalProfile;
	}

	/* This Method Is To Update Values */
	@Override
	public TerminalNotifyProfile updateTerminalProfileByProfileId(
			@Valid TerminalNotifyProfileDTO terminalNotifyProfileDTO, Map<String, String> headers) {

		Long profileId = terminalNotifyProfileDTO.getNotifyProfileId();
		if (profileId == null) {
			throw new InvalidDataException("Notify Profile Id should not be null");
		}
		if (!repository.existsByNotifyProfileId(profileId)) {
			throw new NoRecordsFoundException("No record found for this profile Id : " + profileId);
		}
		TerminalNotifyProfile terminalProfile = repository.findByNotifyProfileId(profileId);
		UserId.headerUserID(headers);
		terminalProfile.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		if (terminalNotifyProfileDTO.getShift() != null) {
			terminalProfile.setShift(terminalNotifyProfileDTO.getShift());
		}
		if (terminalNotifyProfileDTO.getDayOfWeek() != null) {
			terminalProfile.setDayOfWeek(terminalNotifyProfileDTO.getDayOfWeek());
		}
		if (terminalNotifyProfileDTO.getEventCode() != null) {
			if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EVENT",
					terminalNotifyProfileDTO.getEventCode())) {
				throw new InvalidDataException("Invalid Event Code");
			}
			terminalProfile.setEventCode(terminalNotifyProfileDTO.getEventCode().trim());
		} 

		List<NotifyProfileMethodDTO> notifyProfileMethodsDto = terminalNotifyProfileDTO.getNotifyProfileMethods();
		if(notifyProfileMethodsDto == null) {
			terminalProfile.setNotifyProfileMethods(null);
			repository.save(terminalProfile);
			return terminalProfile;
		}
		for (NotifyProfileMethodDTO notifyProfileMethodDTO : notifyProfileMethodsDto) {

			if (notifyProfileMethodDTO.getNotificationName() == null
					|| notifyProfileMethodDTO.getNotificationName().trim().equals("")) {
				throw new InvalidDataException("Notification Name should not be null");
			}
			if (notifyProfileMethodDTO.getNotificationMethod() == null) {
				throw new InvalidDataException("Must Specify Notification Method.");
			}
			if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.EMAIL) {
				throw new InvalidDataException("EMAIL Notification Method is not valid");
			}
			
			if (notifyProfileMethodDTO.getNotificationOrder() == null) {
				throw new InvalidDataException("Must Specify Notification Order");
			}
			if (notifyProfileMethodDTO.getNotificationType() == null) {
				throw new InvalidDataException("Must Specify Notification Type.");
			}

			if (notifyProfileMethodDTO.getNotificationOrder() == NotificationOrder.SECONDARY) {
				String Rmfc = "RMFC", Rcov = "RCOV";
				if ((!terminalNotifyProfileDTO.getEventCode().equals(Rmfc))
						&& (!terminalNotifyProfileDTO.getEventCode().equals(Rcov))) {
					throw new InvalidDataException(
							"Secondary Notification order is only valid for event code RMFC or RCOV");
				}
			}

			if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.EDI) {
				if (notifyProfileMethodDTO.getEdiBox() == null
						|| notifyProfileMethodDTO.getEdiBox().trim().equals("")) {
					throw new InvalidDataException("EDI Box is required field for Notification Method EDI");
				}
				if (notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_SUMMARY
						|| notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_SUMMARY) {
					throw new InvalidDataException("Notification Type not valid for Method EDI.");
				}
				if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EDI_RSN",
						terminalNotifyProfileDTO.getEventCode())) {
					throw new InvalidDataException("Notification Method EDI not valid for event code "
							+ terminalNotifyProfileDTO.getEventCode());
				}
			} else if (notifyProfileMethodDTO.getEdiBox() != null) {
				throw new InvalidDataException("Notify Method must be EDI before entering EDI box fields");
			}

			if ((notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_DETAIL)
					|| (notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_SUMMARY)) {
				String Rmfc = "RMFC";
				if (terminalNotifyProfileDTO.getEventCode().equals(Rmfc)) {
					throw new InvalidDataException(
							"Change notificationtype , Event code should not be RMFC for notificationType Deferred Details or Deferred Summary.");
				}
			}

			if ((notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_DETAIL)
					|| (notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_SUMMARY)) {
				String Rmfc = "RMFC", Irfc = "IRFC";
				if ((!terminalNotifyProfileDTO.getEventCode().equals(Rmfc))
						&& (!terminalNotifyProfileDTO.getEventCode().equals(Irfc))) {
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
//				if (notifyProfileMethodDTO.getNotifyAreaCode() == null
//						|| notifyProfileMethodDTO.getNotifyPrefix() == null
//						|| notifyProfileMethodDTO.getNotifySuffix() == null) {
//					throw new InvalidDataException("Phone Number Incomplete");
//				}
			}
			if((notifyProfileMethodDTO.getNotifyAreaCode() != null
					|| notifyProfileMethodDTO.getNotifyPrefix() != null
					|| notifyProfileMethodDTO.getNotifySuffix() != null)&& (notifyProfileMethodDTO.getNotifyAreaCode() == null
					|| notifyProfileMethodDTO.getNotifyPrefix() == null
					|| notifyProfileMethodDTO.getNotifySuffix() == null)) {
			throw new InvalidDataException("Phone Number Incomplete");
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

		/* Changing UVersion value during each update */
		if(StringUtils.isNotEmpty(terminalProfile.getUversion())) {
			terminalProfile.setUversion(
					Character.toString((char) ((((int)terminalProfile.getUversion().charAt(0) - 32) % 94) + 33)));
		} else{
			terminalProfile.setUversion("!");
		}

		terminalProfile.setNotifyProfileMethods(notifyProfileMethods);
		
		/* Changing UVersion value during each update */
		if(StringUtils.isNotEmpty(terminalProfile.getUversion())) {
			terminalProfile.setUversion(
					Character.toString((char) ((((int)terminalProfile.getUversion().charAt(0) - 32) % 94) + 33)));
		} else{
			terminalProfile.setUversion("!");
		}
		
		repository.save(terminalProfile);
		return terminalProfile;

	}

	@Transactional
	@Override
	public void deleteTerminalProfile(@Valid @NotNull TerminalNotifyProfile terminalNotifyProfile) {
		Long profileId = terminalNotifyProfile.getNotifyProfileId();
		if (profileId == null) {
			throw new InvalidDataException("Notify Profile Id should not be null");
		}
		if (repository.existsByNotifyProfileId(profileId)) {
			repository.deleteByNotifyProfileId(profileId);
		} else {
			String errMsg = profileId + " Record Not Found!";
			throw new RecordNotDeletedException(errMsg);
		}
		return;
	}
}
