package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.ActivityNotifyProfile;
import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.DaysOfWeekConverter;
import com.nscorp.obis.domain.NotificationMethod;
import com.nscorp.obis.domain.NotificationOrder;
import com.nscorp.obis.domain.NotificationType;
import com.nscorp.obis.domain.NotifyProfileMethod;
import com.nscorp.obis.domain.Shift;
import com.nscorp.obis.domain.ShiftConverter;
import com.nscorp.obis.domain.SpecialActivityNotifyProfile;
import com.nscorp.obis.dto.ActivityNotifyProfileDTO;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.dto.SpecialActivityNotifyProfileDTO;
import com.nscorp.obis.dto.mapper.ActivityNotifyProfileMapper;
import com.nscorp.obis.dto.mapper.NotifyProfileMethodMapper;
import com.nscorp.obis.dto.mapper.SpecialActivityNotifyProfileMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.ActivityNotifyProfileRepository;
import com.nscorp.obis.repository.BaseActivityNotifyProfileRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.NotifyProfileMethodRepository;
import com.nscorp.obis.repository.SpecialActivityNotifyProfileRepository;
import com.nscorp.obis.repository.TerminalRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpecialActivityNotifyProfileServiceImpl implements SpecialActivityNotifyProfileService {

	@Autowired
	SpecialActivityNotifyProfileRepository repository;

	@Autowired
	ActivityNotifyProfileRepository activityProfileRepo;

	@Autowired
	TerminalRepository terminalRepository;

	@Autowired
	GenericCodeUpdateRepository genericCodeRepository;

	@Autowired
	ActivityNotifyProfileMapper mapper;

	@Autowired
	BaseActivityNotifyProfileRepository baseActivityNotifyProfileRepo;

	@Autowired
	NotifyProfileMethodRepository notifyRepository;

	@Override
	public List<SpecialActivityNotifyProfile> fetchActivityDetails() throws SQLException {

		List<SpecialActivityNotifyProfile> data = new ArrayList<>();
		data = repository.findAll();
		if (data.isEmpty()) {
			throw new NoRecordsFoundException("No Records found.");
		}
		return data;

	}

	@Override
	public List<SpecialActivityNotifyProfile> fetchActivityProfiles(@Valid Integer activityId) throws SQLException {

		List<SpecialActivityNotifyProfile> data = repository.findByActivityId(activityId);
		if (data.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for this activityId.");
		}
		return data;
	}

	@Override
	public ActivityNotifyProfileDTO addActivityNotifyProfile(ActivityNotifyProfileDTO activityNotifyProfileDTO,
			Map<String, String> headers) {
		log.info("addActivityNotifyProfile : Method Starts");
		UserId.headerUserID(headers);
		if (activityNotifyProfileDTO.getNotifyProfileId() == null) {
			activityNotifyProfileDTO.setNotifyProfileId((long) (Math.random() * Math.pow(10, 15)));
		}
		if (activityProfileRepo.existsByNotifyProfileId(activityNotifyProfileDTO.getNotifyProfileId())) {
			throw new RecordAlreadyExistsException(
					"the activity notify profile already exists with given notify profile id :"
							+ activityNotifyProfileDTO.getNotifyProfileId());
		}
		if (activityNotifyProfileDTO.getNotifyTerminalId() != null) {
			if (!terminalRepository.existsByTerminalId(activityNotifyProfileDTO.getNotifyTerminalId())) {
				throw new NoRecordsFoundException(
						"No Terminal Found with this terminal id :" + activityNotifyProfileDTO.getNotifyTerminalId());
			}
		}
		if (activityNotifyProfileDTO.getEventCode() != null) {
			if (activityNotifyProfileDTO.getEventCode().equals("NTFY")) {
				throw new InvalidDataException("NTFY event code is Invalid, use RMFC");
			}
			if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EVENT",
					activityNotifyProfileDTO.getEventCode())) {
				throw new InvalidDataException("Invalid Event Code");
			}
			activityNotifyProfileDTO.setEventCode(activityNotifyProfileDTO.getEventCode());
		}
		DaysOfWeekConverter dayConverter = new DaysOfWeekConverter();
		ShiftConverter shiftConverter = new ShiftConverter();

		if(activityNotifyProfileDTO.getNotifyTerminalId() != null)
		{	
			if (baseActivityNotifyProfileRepo.existsByDayOfWeekAndEventCodeAndShiftAndNotifyTerminalId(
					dayConverter.convertToDatabaseColumn(activityNotifyProfileDTO.getDayOfWeek()),
					activityNotifyProfileDTO.getEventCode(),
					shiftConverter.convertToDatabaseColumn(activityNotifyProfileDTO.getShift()),
					activityNotifyProfileDTO.getNotifyTerminalId())) {
				throw new RecordAlreadyExistsException(
						"Activity Profile already exists with given combination of Event, Days,Shift and terminal");
			}
		}
		if (!repository.existsById(activityNotifyProfileDTO.getActivityId())) {
			SpecialActivityNotifyProfile entity = new SpecialActivityNotifyProfile();
			entity.setActivityId(activityNotifyProfileDTO.getActivityId());
			entity.setCreateUserId(headers.get(CommonConstants.USER_ID));
			entity.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			entity.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			if (entity.getUpdateExtensionSchema() == null) {
				entity.setUpdateExtensionSchema("IMS02668");
			}
			entity = repository.save(entity);
			if (entity == null) {
				throw new RecordNotAddedException("Activity record is not created !");
			}
		}
		activityNotifyProfileDTO.setCreateUserId(headers.get(CommonConstants.USER_ID));
		activityNotifyProfileDTO.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		activityNotifyProfileDTO.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if (activityNotifyProfileDTO.getUpdateExtensionSchema() == null) {
			activityNotifyProfileDTO.setUpdateExtensionSchema("IMS02668");
		}
		activityNotifyProfileDTO.setUversion("!");
		ActivityNotifyProfile activityNotifyProfile = mapper
				.activityNotifyProfileDTOtoActivityNotifyProfile(activityNotifyProfileDTO);
		activityNotifyProfile = activityProfileRepo.save(activityNotifyProfile);
		if (activityNotifyProfile == null) {
			throw new RecordNotAddedException("Activity Notify Profile not created !");
		}
		activityNotifyProfileDTO = mapper.activityNotifyProfiletoActivityNotifyProfileDTO(activityNotifyProfile);
		log.info("addActivityNotifyProfile : Method Ends");
		return activityNotifyProfileDTO;
	}

	@Override
	public SpecialActivityNotifyProfile updateActivityNotifyProfile(@Valid SpecialActivityNotifyProfileDTO specialActivityNotifyProfileDTO,
			Map<String, String> headers) throws SQLException {

				Integer activityId = specialActivityNotifyProfileDTO.getActivityId();
				SpecialActivityNotifyProfile specialActivityNotifyProfileData;

				Optional<SpecialActivityNotifyProfile> custOptional = repository.findById(activityId);
				if (!custOptional.isPresent())
					throw new NoRecordsFoundException("No Record Found For Given Activity Id");
				
				specialActivityNotifyProfileData = custOptional.get();
				UserId.headerUserID(headers);

				specialActivityNotifyProfileData.setUversion("!");
				specialActivityNotifyProfileData.setUpdateUserId(headers.get("userid"));
				specialActivityNotifyProfileData.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
				if (specialActivityNotifyProfileData.getUpdateExtensionSchema() == null) {
					specialActivityNotifyProfileData.setUpdateExtensionSchema("IMS02668");
				}
				if(!StringUtils.equals(specialActivityNotifyProfileData.getActivityDesc(), specialActivityNotifyProfileDTO.getActivityDesc()) )
				{
					specialActivityNotifyProfileData.setActivityDesc(specialActivityNotifyProfileDTO.getActivityDesc());
					specialActivityNotifyProfileData = repository.save(specialActivityNotifyProfileData);
				}
				List<ActivityNotifyProfileDTO> activityNotifyProfileDTOList = specialActivityNotifyProfileDTO.getActivityNotifyProfiles();

				for(ActivityNotifyProfileDTO activityNotifyProfileDTO : activityNotifyProfileDTOList)
				{
					Long notifyProfileId = activityNotifyProfileDTO.getNotifyProfileId();
					Optional<ActivityNotifyProfile> activityData = activityProfileRepo.findById(notifyProfileId);
					if (!activityData.isPresent())
						throw new NoRecordsFoundException("No Record Found For Given Notify Profile Id");

					ActivityNotifyProfile activityNotifyProfile = activityData.get();
					DaysOfWeekConverter dayConverter = new DaysOfWeekConverter();
					ShiftConverter shiftConverter = new ShiftConverter();
					List<Shift> shifts = activityData.get().getShift();
					List<Shift> shiftsDTO = activityNotifyProfileDTO.getShift();
					if (shifts != null && (!shifts.isEmpty())) {
						Collections.sort(shifts);
					}
					if (shiftsDTO != null && (!shiftsDTO.isEmpty())) {
						Collections.sort(shiftsDTO);
					}

					List<DayOfWeek> days = activityData.get().getDayOfWeek();
					List<DayOfWeek> daysDTO = activityNotifyProfileDTO.getDayOfWeek();
					if (days != null && (!days.isEmpty())) {
						Collections.sort(days);
					}
					if (daysDTO != null && (!daysDTO.isEmpty())) {
						Collections.sort(daysDTO);
					}

					if ((!StringUtils.equals(activityNotifyProfileDTO.getEventCode(),activityData.get().getEventCode()))
							|| (!Objects.equals(activityNotifyProfileDTO.getNotifyTerminalId(),activityData.get().getNotifyTerminalId()) 
									|| (!Objects.equals(days, daysDTO) )
										|| (!Objects.equals(shifts, shiftsDTO))) )

					{
						if (baseActivityNotifyProfileRepo.existsByDayOfWeekAndEventCodeAndShiftAndNotifyTerminalId(
								dayConverter.convertToDatabaseColumn(activityNotifyProfileDTO.getDayOfWeek()),
								activityNotifyProfileDTO.getEventCode(),
								shiftConverter.convertToDatabaseColumn(activityNotifyProfileDTO.getShift()),
								activityNotifyProfileDTO.getNotifyTerminalId())) {
							throw new RecordAlreadyExistsException("Activity Profile already exists.");
						}
					}
					activityNotifyProfile.setUversion("!");
					activityNotifyProfile.setUpdateUserId(headers.get("userid"));
					activityNotifyProfile.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
					if (activityNotifyProfile.getUpdateExtensionSchema() == null) {
						activityNotifyProfile.setUpdateExtensionSchema("IMS02668");
					}

					if(activityNotifyProfileDTO.getEventCode() != null)
					{
						if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EVENT",activityNotifyProfileDTO.getEventCode())) {
							throw new InvalidDataException("Invalid Event Code");
						}
						if (activityNotifyProfileDTO.getEventCode().equals("NTFY")) {
							throw new InvalidDataException("NTFY Event not valid, use RMFC");
						}
						activityNotifyProfile.setEventCode(activityNotifyProfileDTO.getEventCode());
					}

					activityNotifyProfile.setNotifyTerminalId(activityNotifyProfileDTO.getNotifyTerminalId());

					// if(activityNotifyProfileDTO.getDayOfWeek() != null)
					activityNotifyProfile.setDayOfWeek(activityNotifyProfileDTO.getDayOfWeek());

					// if(activityNotifyProfileDTO.getShift() != null)
					activityNotifyProfile.setShift(activityNotifyProfileDTO.getShift());


					List<NotifyProfileMethodDTO> notifyProfileMethodsDto = activityNotifyProfileDTO.getNotifyProfileMethods();
					if (notifyProfileMethodsDto == null) {
						activityNotifyProfile.setNotifyProfileMethods(null);
						activityProfileRepo.save(activityNotifyProfile);
						return specialActivityNotifyProfileData;
					}

					for (NotifyProfileMethodDTO notifyProfileMethodDTO : notifyProfileMethodsDto) 
					{
						if (notifyProfileMethodDTO.getNotificationName() == null || notifyProfileMethodDTO.getNotificationName().trim().equals("")) 
							throw new InvalidDataException("Notification Name should not be null");

						if (notifyProfileMethodDTO.getNotificationMethod() == null) 
							throw new InvalidDataException("Must Specify Notification Method.");

						if (notifyProfileMethodDTO.getNotificationOrder() == null) 
							throw new InvalidDataException("Must Specify Notification Order");
						
						if (notifyProfileMethodDTO.getNotificationType() == null) 
							throw new InvalidDataException("Must Specify Notification Type.");
						
						if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.EDI) {

							if (notifyProfileMethodDTO.getEdiBox() == null || notifyProfileMethodDTO.getEdiBox().trim().equals("")) 
								throw new InvalidDataException("EDI Box is required field for Notification Method EDI");
							
							if (notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_SUMMARY || notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_SUMMARY) 
								throw new InvalidDataException("Notification Type not valid for Method EDI.");
							
							if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EDI_RSN", activityNotifyProfileDTO.getEventCode())) 
								throw new InvalidDataException("Notification Method EDI not valid for event code " + activityNotifyProfileDTO.getEventCode());
							
						} else if (notifyProfileMethodDTO.getEdiBox() != null) {
							throw new InvalidDataException("Notify Method must be EDI before entering EDI box fields");
						}

						if ((notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_DETAIL)
								|| (notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_SUMMARY)) {
							String Rmfc = "RMFC";
							if (activityNotifyProfileDTO.getEventCode().equals(Rmfc)) {
								throw new InvalidDataException("Change notificationtype , Event code should not be RMFC for notificationType Deferred Details or Deferred Summary.");
							}
						}

						if (notifyProfileMethodDTO.getNotificationOrder() == NotificationOrder.SECONDARY) {
							String Rmfc = "RMFC", Rcov = "RCOV";
							if ((!activityNotifyProfileDTO.getEventCode().equals(Rmfc)) && (!activityNotifyProfileDTO.getEventCode().equals(Rcov))) {
								throw new InvalidDataException("Secondary Notification order is only valid for event code RMFC or RCOV");
							}
						}

						if ((notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_DETAIL)
								|| (notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_SUMMARY)) {
							String Rmfc = "RMFC", Irfc = "IRFC";
							if ((!activityNotifyProfileDTO.getEventCode().equals(Rmfc)) && (!activityNotifyProfileDTO.getEventCode().equals(Irfc))) {
								throw new InvalidDataException("Change notificationtype , Event code should be RMFC or IRFC for notificationType Delay Details or Delay Summary.");
							}
						}

						if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.VOICE || notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.FAX) {
							if (notifyProfileMethodDTO.getNotifyAreaCode() == null && notifyProfileMethodDTO.getNotifyPrefix() == null && notifyProfileMethodDTO.getNotifySuffix() == null) {
								throw new InvalidDataException("Must Enter Telephone No");
							}
						}

						if ((notifyProfileMethodDTO.getNotifyAreaCode() != null || notifyProfileMethodDTO.getNotifyPrefix() != null
								|| notifyProfileMethodDTO.getNotifySuffix() != null) && (notifyProfileMethodDTO.getNotifyAreaCode() == null
									|| notifyProfileMethodDTO.getNotifyPrefix() == null || notifyProfileMethodDTO.getNotifySuffix() == null)) {
							throw new InvalidDataException("Phone Number Incomplete");
						}

						if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.FAX) {
							if (notifyProfileMethodDTO.getMicrowaveIndicator() == null) {
								throw new InvalidDataException("Must Enter MicrowaveIndicator Y or N");
							}
						}

						if (!activityNotifyProfileDTO.getEventCode().equals("RMFC")&& !activityNotifyProfileDTO.getEventCode().equals("RCOV")) {
							if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.EMAIL && notifyProfileMethodDTO.getAutoRenotify() == "N") {
								throw new InvalidDataException("The user can only enter the Auto renotify field if the event code is set as RMFC or RCOV and the notify method type is not set as EMAIL.");
							} 
							else if (notifyProfileMethodDTO.getNotificationMethod() != NotificationMethod.EMAIL && notifyProfileMethodDTO.getAutoRenotify() != null) {
								throw new InvalidDataException("auto renotify fields populate only for the event code is RMFC or RCOV");
							}
						}

						if(StringUtils.isNotEmpty(activityNotifyProfile.getUversion())) {
							activityNotifyProfile.setUversion(Character.toString((char) ((((int)activityNotifyProfile.getUversion().charAt(0) - 32) % 94) + 33)));
						} else{
							activityNotifyProfile.setUversion("!");
						}


						if (notifyProfileMethodDTO.getNotifyMethodId() == null) {
							Long id = (long) (Math.random() * Math.pow(10, 15));
							notifyProfileMethodDTO.setNotifyMethodId(id);
							NotifyProfileMethod method = NotifyProfileMethodMapper.INSTANCE.notifyProfileMethodDTOToNotifyProfileMethod(notifyProfileMethodDTO);
							/* Changing UVersion value during each update */
							if(StringUtils.isNotEmpty(method.getUversion())) {
								method.setUversion( Character.toString((char) ((((int)method.getUversion().charAt(0) - 32) % 94) + 33)) );
							} else{
								method.setUversion("!");
							}
							method.setCreateUserId(headers.get(CommonConstants.USER_ID));
							method.setUpdateUserId(headers.get(CommonConstants.USER_ID));
							method.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
							if (method.getUpdateExtensionSchema() == null) {
								method.setUpdateExtensionSchema("IMS02668");
							}
							notifyRepository.save(method);
						}
			
					}

					List<NotifyProfileMethod> notifyProfileMethods = new ArrayList<>();
					notifyProfileMethods = notifyProfileMethodsDto.stream()
						.map(NotifyProfileMethodMapper.INSTANCE::notifyProfileMethodDTOToNotifyProfileMethod)
						.collect(Collectors.toList());

					activityNotifyProfile.setNotifyProfileMethods(notifyProfileMethods);
					activityProfileRepo.save(activityNotifyProfile);

				}
				SpecialActivityNotifyProfile specialActivityNotifyProfileValue = SpecialActivityNotifyProfileMapper.INSTANCE.
						specialActivityNotifyProfileDtoToSpecialActivityNotifyProfile(specialActivityNotifyProfileDTO);

				return specialActivityNotifyProfileValue;
	}

	@Transactional
	public void deleteActivityNotifyProfile(@Valid @NotNull ActivityNotifyProfile activityNotifyProfile) {

		Long profileId = activityNotifyProfile.getNotifyProfileId();
		if (profileId == null) {
			throw new InvalidDataException("Notify Profile Id should not be null");
		}
		if (activityProfileRepo.existsByNotifyProfileId(profileId)) {
			activityProfileRepo.deleteByNotifyProfileId(profileId);
		} else {
			String errMsg = profileId + " Record Not Found!";
			throw new RecordNotDeletedException(errMsg);
		}
	}


}
