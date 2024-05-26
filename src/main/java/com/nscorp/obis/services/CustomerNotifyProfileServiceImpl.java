package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.*;
import com.nscorp.obis.dto.CustomerNotifyProfileDTO;
import com.nscorp.obis.dto.DeliveryDetailDTO;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.dto.mapper.NotifyProfileMethodMapper;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.*;
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
public class CustomerNotifyProfileServiceImpl implements CustomerNotifyProfileService {

	@Autowired
	CustomerNotifyProfileRepository repository;
	@Autowired
	NotifyProfileMethodRepository notifyRepository;

	@Autowired
	TerminalRepository terminalRepository;

	@Autowired
	DeliveryDetailRepository deliveryDetailRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	GenericCodeUpdateRepository genericCodeRepository;

	@Override
	public List<CustomerNotifyProfile> fetchCustomerNotifyProfiles(@Valid Long customerId) throws SQLException {
		List<CustomerNotifyProfile> customerNotify = new ArrayList<>();
		customerNotify = repository.findByCustomer_CustomerId(customerId);
		if (customerNotify.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for this customer.");
		}
		for (CustomerNotifyProfile customerNotifyProfile : customerNotify) {
			Customer customer = customerNotifyProfile.getCustomer();
			if (customer != null) {
				DeliveryDetail deliveryDetail = customer.getDeliveryDetail();
				String t = "T";
				if (deliveryDetail != null) {
					if (deliveryDetail.getSendDETG() != null) {
						if (deliveryDetail.getSendDETG().equals(t)) {
							deliveryDetail.setSendDETG("true");
						}
					}
					if (deliveryDetail.getSendDETG() != null) {
						if (deliveryDetail.getSendSETG().equals(t)) {
							deliveryDetail.setSendSETG("true");
						}
					}
				}
			}
		}

		return customerNotify;
	}

	/* This method is used to add values */
	@Override
	public CustomerNotifyProfile insertCustomerNotifyProfile(CustomerNotifyProfile customerNotifyProfile,
			Map<String, String> headers, Long notifyTerminalId, Long customerId) {
		if (notifyTerminalId != null) {

			if (!terminalRepository.existsByTerminalId(notifyTerminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id :" + notifyTerminalId);
			}
			customerNotifyProfile.setNotifyTerminalId(notifyTerminalId);
		}

		if (customerId != null) {
			Customer customer = customerRepository.findByCustomerId(customerId);
			if (!customerRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer found with this customer Id : " + customerId);
			}
			customerNotifyProfile.setCustomer(customer);

		}

		Long id = (long) (Math.random() * Math.pow(10, 15));
		if (customerNotifyProfile.getNotifyProfileId() == null)
			customerNotifyProfile.setNotifyProfileId(id);
		if (customerNotifyProfile.getEventCode() != null) {
			if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EVENT",
					customerNotifyProfile.getEventCode())) {
				throw new InvalidDataException("Invalid Event Code");
			} else if (customerNotifyProfile.getEventCode().equals("NTFY")) {
				throw new InvalidDataException("NTFY event code is Invalid, use RMFC");
			}
		} else {
			throw new InvalidDataException("Event code should not be null");
		}

		if (repository.existsByNotifyProfileId(customerNotifyProfile.getNotifyProfileId())) {
			throw new RecordAlreadyExistsException(
					"This customer notify profile already exists with notify profile id: "
							+ customerNotifyProfile.getNotifyProfileId());
		}

		UserId.headerUserID(headers);
		customerNotifyProfile.setCreateUserId(headers.get("userid"));
		customerNotifyProfile.setUpdateUserId(headers.get("userid"));

		customerNotifyProfile.setUpdateExtensionSchema(headers.get("extensionschema"));
		customerNotifyProfile.setUversion("!");
		CustomerNotifyProfile customerProfile = repository.save(customerNotifyProfile);
		if (customerProfile == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}
		return customerProfile;
	}

	/* This Method Is Used to Update Values */
	@Override
	public CustomerNotifyProfile updateCustomerNotifyProfile(@Valid CustomerNotifyProfileDTO customerNotifyProfileDTO,
			Map<String, String> headers) {
		Long notifyProfileId = customerNotifyProfileDTO.getNotifyProfileId();
		if (notifyProfileId == null) {
			throw new InvalidDataException("Notify Profile Id should not be null");
		}
		if (!repository.existsByNotifyProfileId(notifyProfileId)) {
			throw new NoRecordsFoundException("No record found for this profile Id : " + notifyProfileId);
		}
		CustomerNotifyProfile customerNotifyProfile = repository.findByNotifyProfileId(notifyProfileId);
		UserId.headerUserID(headers);
		customerNotifyProfile.setUpdateUserId(headers.get("userid"));

		if (customerNotifyProfileDTO.getNotifyTerminalId() != null) {
			Long notifyTerminalId = customerNotifyProfileDTO.getNotifyTerminalId();
			if (!terminalRepository.existsByTerminalId(notifyTerminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id :" + notifyTerminalId);
			}
			customerNotifyProfile.setNotifyTerminalId(notifyTerminalId);
		} else {
			customerNotifyProfile.setNotifyTerminalId(customerNotifyProfileDTO.getNotifyTerminalId());
		}

		if (customerNotifyProfileDTO.getShift() != null) {
			customerNotifyProfile.setShift(customerNotifyProfileDTO.getShift());
		}
		if (customerNotifyProfileDTO.getDayOfWeek() != null) {
			customerNotifyProfile.setDayOfWeek(customerNotifyProfileDTO.getDayOfWeek());
		}
		if (customerNotifyProfileDTO.getEventCode() != null) {
			if (!genericCodeRepository.existsByGenericTableAndGenericTableCode("EVENT",
					customerNotifyProfileDTO.getEventCode())) {
				throw new InvalidDataException("Invalid Event Code");
			}
			if (customerNotifyProfileDTO.getEventCode().equals("NTFY")) {
				throw new InvalidDataException("NTFY Event not valid, use RMFC");
			}
			customerNotifyProfile.setEventCode(customerNotifyProfileDTO.getEventCode());
		} else {
			throw new InvalidDataException("Event code should not be null");
		}

		if (customerNotifyProfileDTO.getCustomer() != null) {
			Long customerId = customerNotifyProfileDTO.getCustomer().getCustomerId();
			if (!customerRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer Found with this customer id :" + customerId);
			} else {
				DeliveryDetailDTO deliveryDetailDTO = customerNotifyProfileDTO.getCustomer().getDeliveryDetail();
				Customer customer = customerNotifyProfile.getCustomer();
				if (customer == null) {
					customer = customerRepository.findByCustomerId(customerId);
				}

				DeliveryDetail deliveryDetail = customer.getDeliveryDetail();
				if (deliveryDetailDTO != null) {
					if (deliveryDetail == null){
						deliveryDetail = new DeliveryDetail();
						deliveryDetail.setCustomerId(customerId);
						UserId.headerUserID(headers);
						deliveryDetail.setCreateUserId(headers.get("userid"));
					}
					deliveryDetail.setUpdateUserId(headers.get("userid"));
					if (headers.get(CommonConstants.EXTENSION_SCHEMA) != null) {
						deliveryDetail.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
					} else {
						deliveryDetail.setUpdateExtensionSchema("IMS02663");
					}
					/* Changing UVersion value during each update */
					if(StringUtils.isNotEmpty(deliveryDetailDTO.getUversion())) {
						deliveryDetailDTO.setUversion(
								Character.toString((char) ((((int)deliveryDetailDTO.getUversion().charAt(0) - 32) % 94) + 33)));
					} else{
						deliveryDetailDTO.setUversion("!");
					}
					deliveryDetail.setUversion(deliveryDetailDTO.getUversion());
					deliveryDetail.setConfirmationTimeInterval(deliveryDetailDTO.getConfirmationTimeInterval());
					if (deliveryDetailDTO.getSendSETG() != null) {
						if (deliveryDetailDTO.getSendSETG() == false) {
							deliveryDetail.setSendSETG("F");
						}
						if (deliveryDetailDTO.getSendSETG() == true) {
							deliveryDetail.setSendSETG("T");
						}
					} else {
						deliveryDetail.setSendSETG(null);
					}
					if (deliveryDetailDTO.getSendDETG() != null) {
						if (deliveryDetailDTO.getSendDETG() == false) {
							deliveryDetail.setSendDETG("F");
						}
						if (deliveryDetailDTO.getSendDETG() == true) {
							deliveryDetail.setSendDETG("T");
						}
					} else {
						deliveryDetail.setSendDETG(null);
					}
					//deliveryDetailRepository.save(deliveryDetail);
				} 
				// deliveryDetailRepository.save(deliveryDetail);
				customer.setDeliveryDetail(deliveryDetail);
				customerNotifyProfile.setCustomer(customer);
			}
		}

		List<NotifyProfileMethodDTO> notifyProfileMethodsDto = customerNotifyProfileDTO.getNotifyProfileMethods();
		if (notifyProfileMethodsDto == null) {
			customerNotifyProfile.setNotifyProfileMethods(null);
			repository.save(customerNotifyProfile);
			return customerNotifyProfile;
		}
		for (NotifyProfileMethodDTO notifyProfileMethodDTO : notifyProfileMethodsDto) {

			if (notifyProfileMethodDTO.getNotificationName() == null
					|| notifyProfileMethodDTO.getNotificationName().trim().equals("")) {
				throw new InvalidDataException("Notification Name should not be null");
			}
			if (notifyProfileMethodDTO.getNotificationMethod() == null) {
				throw new InvalidDataException("Must Specify Notification Method.");
			}

			if (notifyProfileMethodDTO.getNotificationOrder() == null) {
				throw new InvalidDataException("Must Specify Notification Order");
			}
			if (notifyProfileMethodDTO.getNotificationType() == null) {
				throw new InvalidDataException("Must Specify Notification Type.");
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
						customerNotifyProfileDTO.getEventCode())) {
					throw new InvalidDataException("Notification Method EDI not valid for event code "
							+ customerNotifyProfileDTO.getEventCode());
				}
			} else if (notifyProfileMethodDTO.getEdiBox() != null) {
				throw new InvalidDataException("Notify Method must be EDI before entering EDI box fields");
			}
			if ((notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_DETAIL)
					|| (notifyProfileMethodDTO.getNotificationType() == NotificationType.DEFERRED_SUMMARY)) {
				String Rmfc = "RMFC";
				if (customerNotifyProfileDTO.getEventCode().equals(Rmfc)) {
					throw new InvalidDataException(
							"Change notificationtype , Event code should not be RMFC for notificationType Deferred Details or Deferred Summary.");
				}
			}

			if (notifyProfileMethodDTO.getNotificationOrder() == NotificationOrder.SECONDARY) {
				String Rmfc = "RMFC", Rcov = "RCOV";
				if ((!customerNotifyProfileDTO.getEventCode().equals(Rmfc))
						&& (!customerNotifyProfileDTO.getEventCode().equals(Rcov))) {
					throw new InvalidDataException(
							"Secondary Notification order is only valid for event code RMFC or RCOV");
				}
			}

			if ((notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_DETAIL)
					|| (notifyProfileMethodDTO.getNotificationType() == NotificationType.DELAY_SUMMARY)) {
				String Rmfc = "RMFC", Irfc = "IRFC";
				if ((!customerNotifyProfileDTO.getEventCode().equals(Rmfc))
						&& (!customerNotifyProfileDTO.getEventCode().equals(Irfc))) {
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
			if ((notifyProfileMethodDTO.getNotifyAreaCode() != null || notifyProfileMethodDTO.getNotifyPrefix() != null
					|| notifyProfileMethodDTO.getNotifySuffix() != null)
					&& (notifyProfileMethodDTO.getNotifyAreaCode() == null
							|| notifyProfileMethodDTO.getNotifyPrefix() == null
							|| notifyProfileMethodDTO.getNotifySuffix() == null)) {
				throw new InvalidDataException("Phone Number Incomplete");
			}
			if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.FAX) {
				if (notifyProfileMethodDTO.getMicrowaveIndicator() == null) {
					throw new InvalidDataException("Must Enter MicrowaveIndicator Y or N");
				}

			}

			if (!customerNotifyProfileDTO.getEventCode().equals("RMFC")
					&& !customerNotifyProfileDTO.getEventCode().equals("RCOV")) {
				if (notifyProfileMethodDTO.getNotificationMethod() == NotificationMethod.EMAIL
						&& notifyProfileMethodDTO.getAutoRenotify() == "N") {
					throw new InvalidDataException(
							"The user can only enter the Auto renotify field if the event code is set as RMFC or RCOV and the notify method type is not set as EMAIL.");
				} else if (notifyProfileMethodDTO.getNotificationMethod() != NotificationMethod.EMAIL
						&& notifyProfileMethodDTO.getAutoRenotify() != null) {

					throw new InvalidDataException(
							"auto renotify fields populate only for the event code is RMFC or RCOV");
				}

			}
			
			/* Changing UVersion value during each update */
			if(StringUtils.isNotEmpty(customerNotifyProfile.getUversion())) {
				customerNotifyProfile.setUversion(
						Character.toString((char) ((((int)customerNotifyProfile.getUversion().charAt(0) - 32) % 94) + 33)));
			} else{
				customerNotifyProfile.setUversion("!");
			}

			if (notifyProfileMethodDTO.getNotifyMethodId() == null) {
				Long id = (long) (Math.random() * Math.pow(10, 15));
				notifyProfileMethodDTO.setNotifyMethodId(id);
				NotifyProfileMethod method = NotifyProfileMethodMapper.INSTANCE
						.notifyProfileMethodDTOToNotifyProfileMethod(notifyProfileMethodDTO);
				/* Changing UVersion value during each update */
				if(StringUtils.isNotEmpty(method.getUversion())) {
					method.setUversion(
							Character.toString((char) ((((int)method.getUversion().charAt(0) - 32) % 94) + 33)));
				} else{
					method.setUversion("!");
				}
				notifyRepository.save(method);
			}

		}

		List<NotifyProfileMethod> notifyProfileMethods = new ArrayList<>();
		notifyProfileMethods = notifyProfileMethodsDto.stream()
				.map(NotifyProfileMethodMapper.INSTANCE::notifyProfileMethodDTOToNotifyProfileMethod)
				.collect(Collectors.toList());

		customerNotifyProfile.setNotifyProfileMethods(notifyProfileMethods);
		repository.save(customerNotifyProfile);
		return customerNotifyProfile;
	}

	/* This Method Is Used to Delete Values */
	@Transactional
	public void deleteCustomerNotifyProfiles(@Valid @NotNull CustomerNotifyProfile customerNotifyProfiles) {

		Long profileId = customerNotifyProfiles.getNotifyProfileId();
		if (profileId == null) {
			throw new InvalidDataException("Notify Profile Id should not be null");
		}
		if (repository.existsByNotifyProfileId(profileId)) {
			repository.deleteByNotifyProfileId(profileId);
		} else {
			String errMsg = profileId + " Record Not Found!";
			throw new RecordNotDeletedException(errMsg);
		}
	}

}
