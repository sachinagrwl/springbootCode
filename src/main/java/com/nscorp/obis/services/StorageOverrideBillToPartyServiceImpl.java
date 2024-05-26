package com.nscorp.obis.services;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.StorageOverrideBillToParty;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.StorageOverrideBillToPartyRepository;

@Transactional
@Service
public class StorageOverrideBillToPartyServiceImpl implements StorageOverrideBillToPartyService {

	@Autowired
	StorageOverrideBillToPartyRepository overrideBillToPartyRepository;

	@Autowired
	CorporateCustomerRepository corporateCustomerRepository;

	@Override
	public StorageOverrideBillToParty updateOverrideBillToParty(StorageOverrideBillToParty storageOverrideBillToParty,
			Map<String, String> headers) {
		UserId.headerUserID(headers);
		StorageOverrideBillToParty resultStorageOverrideBillToParty;
		if (StringUtils.isBlank(headers.get(CommonConstants.EXTENSION_SCHEMA))) {
			throw new NullPointerException(CommonConstants.EXTENSION_SCHEMA_EXCEPTION_MESSAGE);
		}
		if (!corporateCustomerRepository.existsById(storageOverrideBillToParty.getCorporateCustomerId())) {
			throw new NoRecordsFoundException(
					"Provided Corporate CustId: " + storageOverrideBillToParty.getCorporateCustomerId()
							+ "is not valid as it doesn't exists in CORPORATE CUSTOMER");
		}

		if (!overrideBillToPartyRepository.existsById(storageOverrideBillToParty.getCorporateCustomerId())) {
			// insert record
			storageOverrideBillToParty.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			storageOverrideBillToParty.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			storageOverrideBillToParty.setUversion("!");
			storageOverrideBillToParty
					.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
			resultStorageOverrideBillToParty = overrideBillToPartyRepository.save(storageOverrideBillToParty);
		} else {
			// update record
			if (!overrideBillToPartyRepository.existsByCorporateCustomerIdAndUversion(
					storageOverrideBillToParty.getCorporateCustomerId(), storageOverrideBillToParty.getUversion())) {
				throw new NoRecordsFoundException("No record Found Under this CorporateCustomerId:"
						+ storageOverrideBillToParty.getCorporateCustomerId() + " and Uversion:"
						+ storageOverrideBillToParty.getUversion());
			}
			StorageOverrideBillToParty existingRecord = overrideBillToPartyRepository
					.findById(storageOverrideBillToParty.getCorporateCustomerId()).get();
			existingRecord.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			existingRecord.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
			if (StringUtils.isNotEmpty(existingRecord.getUversion())) {
				existingRecord.setUversion(
						Character.toString((char) ((((int) existingRecord.getUversion().charAt(0) - 32) % 94) + 33)));
			}
			existingRecord.setOverrideInd(storageOverrideBillToParty.getOverrideInd());
			resultStorageOverrideBillToParty = overrideBillToPartyRepository.save(existingRecord);
		}
		return resultStorageOverrideBillToParty;
	}

}
