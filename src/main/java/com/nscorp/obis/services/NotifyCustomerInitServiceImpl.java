package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.domain.NotifyCustomerInit;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerIndexRepository;
import com.nscorp.obis.repository.NotifyCustomerInitRepository;

@Service
@Transactional
public class NotifyCustomerInitServiceImpl implements NotifyCustomerInitService {

	@Autowired
	private NotifyCustomerInitRepository notifyCustomerInitRepo;
	@Autowired
	private CustomerIndexRepository customerIndexRepository;

	@Override
	public List<NotifyCustomerInit> getCustomerInitials(Long custId) {
		List<NotifyCustomerInit> custInitials = custId != null
				? notifyCustomerInitRepo.findByCustIdOrderByEqInitAsc(custId)
				: notifyCustomerInitRepo.findAllByOrderByEqInitAsc();

		if (custInitials.isEmpty()) {
			throw new NoRecordsFoundException("No Initials Found!");
		}
		return custInitials;
	}

	@Override
	public NotifyCustomerInit addNotifyCustomerInit(NotifyCustomerInit notifyCustomerInit,
			Map<String, String> headers) {
		UserId.headerUserID(headers);

		if(StringUtils.isBlank(notifyCustomerInit.getEqInit())) {
			throw new NullPointerException("'eqInit' Should not be Blank or Null.");
		}

		if (StringUtils.isBlank(headers.get(CommonConstants.EXTENSION_SCHEMA))) {
			throw new NullPointerException(CommonConstants.EXTENSION_SCHEMA_EXCEPTION_MESSAGE);
		}

		if (notifyCustomerInitRepo.existsByEqInit(notifyCustomerInit.getEqInit())) {
			NotifyCustomerInit notifyCust = notifyCustomerInitRepo.findByEqInit(notifyCustomerInit.getEqInit());
			Optional<CustomerIndex> customerIndex = customerIndexRepository.findById(notifyCust.getCustId());
			String CustomerExceptionName = customerIndex.isPresent() ? customerIndex.get().getCustomerName()
					: notifyCust.getCustId().toString();

			throw new RecordNotAddedException("EqInIt : " + notifyCustomerInit.getEqInit()
					+ " already exists for Customer : " + CustomerExceptionName);
		}
		notifyCustomerInit.setUversion("!");
		notifyCustomerInit.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		notifyCustomerInit.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		notifyCustomerInit.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
		return notifyCustomerInitRepo.saveAndFlush(notifyCustomerInit);
	}

	@Override
	public NotifyCustomerInit deleteNotifyCustomerInit(NotifyCustomerInit notifyCustomerInit) {

		if(StringUtils.isBlank(notifyCustomerInit.getEqInit())) {
			throw new NullPointerException("'eqInit' Should not be Blank or Null.");
		}

		if (notifyCustomerInitRepo.existsByEqInitAndCustIdAndUversion(notifyCustomerInit.getEqInit(),
				notifyCustomerInit.getCustId(), notifyCustomerInit.getUversion())) {
			NotifyCustomerInit notifyCustomer = notifyCustomerInitRepo
					.findByEqInitAndCustId(notifyCustomerInit.getEqInit(), notifyCustomerInit.getCustId());
			notifyCustomerInitRepo.deleteByEqInitAndCustId(notifyCustomerInit.getEqInit(),
					notifyCustomerInit.getCustId());
			return notifyCustomer;
		} else {
			throw new NoRecordsFoundException(
					"No Record Found under EqInIt : " + notifyCustomerInit.getEqInit() + " , CustomerId : "
							+ notifyCustomerInit.getCustId() + " and UVersion : " + notifyCustomerInit.getUversion());
		}
	}

	@Override
	public List<NotifyCustomerInit> updateNotifyCustomerInit(NotifyCustomerInit notifyCustomerInit,
			Map<String, String> headers) {
		notifyCustomerInit.getEqInitList().removeIf(StringUtils::isEmpty);
		List<String> badRequest = notifyCustomerInit.getEqInitList().stream().filter(eqInit -> eqInit.length() > 4)
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(badRequest)) {
			throw new NullPointerException("EqInIt Length should not be greater than 4 for values : " + badRequest);
		}
		notifyCustomerInit.getEqInitList().forEach(eqInit -> {
			if (!notifyCustomerInitRepo.existsByEqInitAndCustId(eqInit,notifyCustomerInit.getCustId())) {
				notifyCustomerInit.setEqInit(eqInit);
				addNotifyCustomerInit(notifyCustomerInit, headers);
			}
		});
		List<String> assocaitedEqInIts = notifyCustomerInitRepo.findByCustId(notifyCustomerInit.getCustId()).stream()
				.map(notifyCust -> notifyCust.getEqInit()).collect(Collectors.toList());
		assocaitedEqInIts.removeAll(notifyCustomerInit.getEqInitList());
		assocaitedEqInIts.forEach(exisingInIt -> {
			notifyCustomerInitRepo.deleteByEqInitAndCustId(exisingInIt, notifyCustomerInit.getCustId());
		});
		List<NotifyCustomerInit> updatedEqInIts = notifyCustomerInitRepo.findByCustId(notifyCustomerInit.getCustId());
		return updatedEqInIts;

	}
}
