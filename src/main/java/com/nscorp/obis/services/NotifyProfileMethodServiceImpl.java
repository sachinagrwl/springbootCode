package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.NotificationMethod;
import com.nscorp.obis.domain.NotificationType;
import com.nscorp.obis.domain.NotifyProfileMethod;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.NotifyProfileMethodRepository;

@Service
public class NotifyProfileMethodServiceImpl implements NotifyProfileMethodService {

	@Autowired
	NotifyProfileMethodRepository repository;
	
	@Autowired
	SpecificationGenerator specificationGenerator;

	/* This service to fetch data */
	@SuppressWarnings("serial")
	@Override
	public List<NotifyProfileMethod> fetchNotifyProfileMethod(@Valid String notificationName, String notificationMethod,
			String notificationType, String ediBox, Integer notifyAreaCode, Integer notifyPrefix, Integer notifySuffix,
			Integer notifyPhoneExtension, String notificationOrder, Character microwaveIndicator, Integer pageNo,
			Integer pageSize) throws SQLException {

		Specification<NotifyProfileMethod> specs = specificationGenerator.notifyProfileMethodSpecification(notificationName, notificationMethod, notificationType, ediBox, 
				notifyAreaCode, notifyPrefix, notifySuffix, notifyPhoneExtension, notificationOrder, microwaveIndicator);
		List<NotifyProfileMethod> result = new ArrayList<>();

		if (pageSize == null) {
			result = repository.findAll(specs);
		} else {
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("notificationName").ascending());
			Page<NotifyProfileMethod> response = repository.findAll(specs, pageable);
			if (response != null) {
				result = response.getContent();
			}
		}
		if (result.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for this Combination.");
		}
		return result;
	}

	/* This service to to add */
	@Override
	public NotifyProfileMethod insertNotifyProfileMethod(NotifyProfileMethod notifyProfileMethod,
			NotifyProfileMethodDTO notifyProfileMethodDTO, Map<String, String> headers) {
		Long id = (long) (Math.random() * Math.pow(10, 15));
		if (notifyProfileMethod.getNotifyMethodId() == null)
			notifyProfileMethod.setNotifyMethodId(id);

		if (repository.existsByNotifyMethodId(notifyProfileMethod.getNotifyMethodId())) {
			throw new RecordAlreadyExistsException("This notify profile method already exists with nothify method id: "
					+ notifyProfileMethod.getNotifyMethodId());
		}
		if (notifyProfileMethod.getNotifyMethodId() != null && notifyProfileMethod.getNotificationType() == null
				&& notifyProfileMethod.getNotificationMethod() == null
				&& notifyProfileMethod.getNotificationOrder() == null
				&& notifyProfileMethod.getNotificationName() == null && notifyProfileMethod.getAutoRenotify() == null
				&& notifyProfileMethod.getNotifyPhoneExtension() == null
				&& notifyProfileMethod.getNotifyAreaCode() == null && notifyProfileMethod.getNotifyPrefix() == null
				&& notifyProfileMethod.getNotifySuffix() == null && notifyProfileMethod.getEdiBox() == null
				&& notifyProfileMethod.getNotifyEmail() == null && notifyProfileMethod.getNotifyEmailId() == null
				&& notifyProfileMethod.getMicrowaveIndicator() == null) {
			throw new InvalidDataException("W_NTFY_M Required");

		}
		if (notifyProfileMethod.getNotificationName() == null
				|| notifyProfileMethod.getNotificationName().trim().equals("")) {
			if (notifyProfileMethod.getNotificationMethod() != null)
				throw new InvalidDataException(
						"Notification Name should not be null if notification method is provided.");
		}
		if (notifyProfileMethod.getNotificationMethod() == null) {
			if (notifyProfileMethod.getNotificationName() != null)
				throw new InvalidDataException(
						"Notification Method should not be null if notification Name is provided.");
		}
		if (notifyProfileMethod.getNotificationMethod() != null) {
			/*
			 * if (notifyProfileMethod.getNotificationName() == null) { throw new
			 * InvalidDataException("Must Specify Notification Name."); }
			 */
			if (notifyProfileMethod.getNotificationOrder() == null) {
				throw new InvalidDataException("Must Specify Notification Order");
			}
			if (notifyProfileMethod.getNotificationType() == null) {
				throw new InvalidDataException("Must Specify Notification Type.");
			}
		}
		if (notifyProfileMethod.getNotificationMethod() == NotificationMethod.EDI) {
			if (notifyProfileMethod.getEdiBox() == null || notifyProfileMethod.getEdiBox().trim().equals("")) {
				throw new InvalidDataException("EDI Box is required field for Notification Method EDI");
			}
			if (notifyProfileMethod.getNotificationType() == NotificationType.DEFERRED_SUMMARY
					|| notifyProfileMethod.getNotificationType() == NotificationType.DELAY_SUMMARY) {
				throw new InvalidDataException("Notification Type not valid for Method EDI.");
			}
		} else if (notifyProfileMethod.getEdiBox() != null) {
			throw new InvalidDataException("Notify Method must be EDI before entering EDI box fields");
		}

		if (notifyProfileMethod.getNotificationMethod() == NotificationMethod.VOICE
				|| notifyProfileMethod.getNotificationMethod() == NotificationMethod.FAX) {
			if (notifyProfileMethod.getNotifyAreaCode() == null && notifyProfileMethod.getNotifyPrefix() == null
					&& notifyProfileMethod.getNotifySuffix() == null) {
				throw new InvalidDataException("Must Enter Telephone No");
			}
//			if (notifyProfileMethod.getNotifyAreaCode() == null || notifyProfileMethod.getNotifyPrefix() == null
//					|| notifyProfileMethod.getNotifySuffix() == null) {
//				throw new InvalidDataException("Phone Number Incomplete");
//			}
		}
		if ((notifyProfileMethod.getNotifyAreaCode() != null || notifyProfileMethod.getNotifyPrefix() != null
				|| notifyProfileMethod.getNotifySuffix() != null)&& (notifyProfileMethod.getNotifyAreaCode() == null || notifyProfileMethod.getNotifyPrefix() == null
				|| notifyProfileMethod.getNotifySuffix() == null)) {
			throw new InvalidDataException("Phone Number Incomplete");
		}
		if (notifyProfileMethod.getNotificationMethod() == NotificationMethod.FAX) {
			if (notifyProfileMethod.getMicrowaveIndicator() == null) {
				throw new InvalidDataException("Must Enter MicrowaveIndicator Y or N");
			}
		}
		if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.EMAIL) {
			notifyProfileMethodDTO.setAutoRenotify("Y");
			notifyProfileMethod.setAutoRenotify(notifyProfileMethodDTO.getAutoRenotify().charAt(0));
			notifyProfileMethodDTO.setMicrowaveIndicator("N");
			notifyProfileMethod.setMicrowaveIndicator(notifyProfileMethodDTO.getMicrowaveIndicator().charAt(0));
		}
		UserId.headerUserID(headers);
		notifyProfileMethod.setCreateUserId(headers.get(CommonConstants.USER_ID));
		notifyProfileMethod.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		notifyProfileMethod.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		notifyProfileMethod.setUversion("!");
		NotifyProfileMethod notifyProfile = repository.save(notifyProfileMethod);
		if (notifyProfile == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}
		return notifyProfile;

	}

	/* This service to to update */
	@Override
	public NotifyProfileMethod updateNotifyProfileMethod(@Valid NotifyProfileMethodDTO notifyProfileMethodDTO,
			Map<String, String> headers) {
		if (notifyProfileMethodDTO.getNotifyMethodId() == null) {
			throw new InvalidDataException("Notify Profile Method Id is required field.");
		}
		Long notifyMethodId = notifyProfileMethodDTO.getNotifyMethodId();
		if (!repository.existsByNotifyMethodId(notifyMethodId)) {
			throw new NoRecordsFoundException("No record found for this profile Id : " + notifyMethodId);
		}
		NotifyProfileMethod notifyProfileMethod = repository.findByNotifyMethodId(notifyMethodId);
	
		UserId.headerUserID(headers);
		notifyProfileMethod.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		if (notifyProfileMethodDTO.getNotificationName() == null
				|| notifyProfileMethodDTO.getNotificationName().trim().equals("")) {
			throw new InvalidDataException("Notification Name should not be null");
		}

		if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.EDI) {
			if (notifyProfileMethodDTO.getEdiBox() == null || notifyProfileMethodDTO.getEdiBox().trim().equals("")) {
				throw new InvalidDataException("EDI Box is required field for Notification Method EDI");
			}
			if (notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_SUMMARY
					|| notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_SUMMARY) {
				throw new InvalidDataException("Notification Type not valid for Method EDI.");
			}
		} else if (notifyProfileMethodDTO.getEdiBox() != null) {
			throw new InvalidDataException("Notify Method must be EDI before entering EDI box fields");
		}
		if (notifyProfileMethodDTO.getNotificationName() != null) {
			if (notifyProfileMethodDTO.getNotificationMethod() == null) {
				throw new InvalidDataException("Must Specify Notification Method.");
			}
		}
		if (notifyProfileMethodDTO.getNotificationMethod() != null) {
			/*
			 * if (notifyProfileMethodDTO.getNotificationName() == null) { throw new
			 * InvalidDataException("Must Specify Notification Name."); }
			 */
			if (notifyProfileMethodDTO.getNotificationOrder() == null) {
				throw new InvalidDataException("Must Specify Notification Order");
			}
			if (notifyProfileMethodDTO.getNotificationType() == null) {
				throw new InvalidDataException("Must Specify Notification Type.");
			}
		}
		/*
		 * if (notifyProfileMethodDTO.getNotificationType() != null) { if
		 * (notifyProfileMethodDTO.getNotificationMethod() == null) { throw new
		 * InvalidDataException("Must Specify Notification Method."); } }
		 */
		if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.VOICE
				|| notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.FAX) {
			if (notifyProfileMethodDTO.getNotifyAreaCode() == null && notifyProfileMethodDTO.getNotifyPrefix() == null
					&& notifyProfileMethodDTO.getNotifySuffix() == null) {
				throw new InvalidDataException("Must Enter Telephone No");
			}
			if (notifyProfileMethodDTO.getNotifyAreaCode() == null || notifyProfileMethodDTO.getNotifyPrefix() == null
					|| notifyProfileMethodDTO.getNotifySuffix() == null) {
				throw new InvalidDataException("Phone Number Incomplete");
			}
		}
		if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.FAX) {
			if (notifyProfileMethodDTO.getMicrowaveIndicator() == null) {
				throw new InvalidDataException("Must Enter MicrowaveIndicator Y or N");
			}

		}
		/*
		 * if (notifyProfileMethodDTO.getNotificationMethod() ==
		 * NotificationMethod.EMAIL) { notifyProfileMethodDTO.setAutoRenotify("Y");
		 * notifyProfileMethodDTO.setMicrowaveIndicator("N"); }
		 */
		notifyProfileMethod.setNotificationType(notifyProfileMethodDTO.getNotificationType());
		notifyProfileMethod.setNotificationMethod(notifyProfileMethodDTO.getNotificationMethod());
		notifyProfileMethod.setNotificationOrder(notifyProfileMethodDTO.getNotificationOrder());
		notifyProfileMethod.setNotificationName(notifyProfileMethodDTO.getNotificationName());

		if (notifyProfileMethodDTO.getAutoRenotify() != null) {
			notifyProfileMethod.setAutoRenotify(notifyProfileMethodDTO.getAutoRenotify().charAt(0));
		} else {
			notifyProfileMethod.setAutoRenotify(null);
		}
		notifyProfileMethod.setEdiBox(notifyProfileMethodDTO.getEdiBox());
		if (notifyProfileMethodDTO.getMicrowaveIndicator() != null) {
			notifyProfileMethod.setMicrowaveIndicator(notifyProfileMethodDTO.getMicrowaveIndicator().charAt(0));
		} else {
			notifyProfileMethod.setMicrowaveIndicator(null);
		}
		if (notifyProfileMethodDTO.getNotifyAreaCode() != null) {
			notifyProfileMethod.setNotifyAreaCode(Integer.valueOf(notifyProfileMethodDTO.getNotifyAreaCode()));
		} else {
			notifyProfileMethod.setNotifyAreaCode(null);
		}
		notifyProfileMethod.setNotifyEmail(notifyProfileMethodDTO.getNotifyEmail());
		notifyProfileMethod.setNotifyEmailId(notifyProfileMethodDTO.getNotifyEmailId());
		notifyProfileMethod.setNotifyPhoneExtension(notifyProfileMethodDTO.getNotifyPhoneExtension());
		if (notifyProfileMethodDTO.getNotifyPrefix() != null) {
			notifyProfileMethod.setNotifyPrefix(Integer.valueOf(notifyProfileMethodDTO.getNotifyPrefix()));
		} else {
			notifyProfileMethod.setNotifyPrefix(null);
		}
		if (notifyProfileMethodDTO.getNotifySuffix() != null) {
			notifyProfileMethod.setNotifySuffix(Integer.valueOf(notifyProfileMethodDTO.getNotifySuffix()));
		} else {
			notifyProfileMethod.setNotifySuffix(null);
		}

		/* Changing UVersion value during each update */
		if(StringUtils.isNotEmpty(notifyProfileMethod.getUversion())) {
			notifyProfileMethod.setUversion(
					Character.toString((char) ((((int)notifyProfileMethod.getUversion().charAt(0) - 32) % 94) + 33)));
		} else{
			notifyProfileMethod.setUversion("!");
		}
		repository.save(notifyProfileMethod);
		return notifyProfileMethod;
	}

}
