package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.GuaranteeCustCrossRef;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.GuaranteeCustCrossRefRepository;

@Service
@Transactional
public class GuaranteeCustCrossRefServiceImpl implements GuaranteeCustCrossRefService {

	@Autowired
	GuaranteeCustCrossRefRepository crossRefRepository;

	@Autowired
	CorporateCustomerRepository corporateCustomerRepository;

	@Override
	public List<GuaranteeCustCrossRef> getAllGuaranteeCustCrossRef(String customerName, String customerNumber,
			String terminalName) {
		List<GuaranteeCustCrossRef> GuaranteeCustCrossRefList = crossRefRepository
				.findGuaranteeCustCrossRef(customerName, customerNumber, terminalName);
		if (GuaranteeCustCrossRefList.isEmpty()) {
			throw new NoRecordsFoundException("No Records found!");
		}
		return GuaranteeCustCrossRefList;
	}

	@Override
	public GuaranteeCustCrossRef addGuaranteeCustCrossRef(GuaranteeCustCrossRef guaranteeCustCrossRef,
			Map<String, String> headers) {
		UserId.headerUserID(headers);
		guaranteeCustCrossRefValidations(guaranteeCustCrossRef, headers);
		guaranteeCustCrossRef.setUversion("!");
		guaranteeCustCrossRef.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		guaranteeCustCrossRef.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		guaranteeCustCrossRef.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
		guaranteeCustCrossRef.setGuaranteeCustXrefId(crossRefRepository.SGKLong());
		return crossRefRepository.save(guaranteeCustCrossRef);
	}

	@Override
	public GuaranteeCustCrossRef updateGuaranteeCustCrossRef(GuaranteeCustCrossRef guaranteeCustCrossRef,
			Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (crossRefRepository.existsByGuaranteeCustXrefIdAndCorpCustIdAndUversion(
				guaranteeCustCrossRef.getGuaranteeCustXrefId(), guaranteeCustCrossRef.getCorpCustId(),
				guaranteeCustCrossRef.getUversion())) {
			guaranteeCustCrossRefValidations(guaranteeCustCrossRef, headers);
			GuaranteeCustCrossRef existingGuaranteeCustCrossRef = crossRefRepository
					.findByGuaranteeCustXrefIdAndCorpCustId(guaranteeCustCrossRef.getGuaranteeCustXrefId(),
							guaranteeCustCrossRef.getCorpCustId());
			existingGuaranteeCustCrossRef.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			existingGuaranteeCustCrossRef
					.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
			if (StringUtils.isNotEmpty(existingGuaranteeCustCrossRef.getUversion())) {
				existingGuaranteeCustCrossRef.setUversion(Character.toString(
						(char) ((((int) existingGuaranteeCustCrossRef.getUversion().charAt(0) - 32) % 94) + 33)));
			}
			existingGuaranteeCustCrossRef.setTerminalName(guaranteeCustCrossRef.getTerminalName());
			existingGuaranteeCustCrossRef.setTerminalId(guaranteeCustCrossRef.getTerminalId());
			return crossRefRepository.save(existingGuaranteeCustCrossRef);
		} else
			throw new NoRecordsFoundException("No record Found to update Under this GuaranteeCustXrefId: "
					+ guaranteeCustCrossRef.getGuaranteeCustXrefId() + " ,CorpCustId: "
					+ guaranteeCustCrossRef.getCorpCustId() + " and U_Version: " + guaranteeCustCrossRef.getUversion());
	}

	@Override
	public GuaranteeCustCrossRef deleteGuaranteeCustCrossRef(GuaranteeCustCrossRef guaranteeCustCrossRef,
			Map<String, String> headers) {
		if (crossRefRepository.existsByGuaranteeCustXrefIdAndCorpCustIdAndUversion(
				guaranteeCustCrossRef.getGuaranteeCustXrefId(), guaranteeCustCrossRef.getCorpCustId(),
				guaranteeCustCrossRef.getUversion())) {
			GuaranteeCustCrossRef existingGuaranteeCustCrossRef = crossRefRepository
					.findByGuaranteeCustXrefIdAndCorpCustId(guaranteeCustCrossRef.getGuaranteeCustXrefId(),
							guaranteeCustCrossRef.getCorpCustId());
			crossRefRepository.delete(existingGuaranteeCustCrossRef);
			return existingGuaranteeCustCrossRef;
		} else
			throw new NoRecordsFoundException("No record Found to delete Under this GuaranteeCustXrefId: "
					+ guaranteeCustCrossRef.getGuaranteeCustXrefId() + " ,CorpCustId: "
					+ guaranteeCustCrossRef.getCorpCustId() + " and U_Version: " + guaranteeCustCrossRef.getUversion());
	}

	private void guaranteeCustCrossRefValidations(GuaranteeCustCrossRef guaranteeCustCrossRef,
			Map<String, String> headers) {
		if (StringUtils.isBlank(headers.get(CommonConstants.EXTENSION_SCHEMA))) {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}
		if (StringUtils.isBlank(guaranteeCustCrossRef.getGuaranteeCustLongName())
				&& StringUtils.isBlank(guaranteeCustCrossRef.getGuaranteeCustomerNumber())) {
			throw new RecordNotAddedException("Customer Name and Customer Number both should not be null/Blank!");
		}
		if (!corporateCustomerRepository.existsByCorporateCustomerId(guaranteeCustCrossRef.getCorpCustId())) {
			throw new RecordNotAddedException("Corporate Customer Id: " + guaranteeCustCrossRef.getCorpCustId()
					+ " is not valid as it doesn't exists in CORP CUST");
		}
		if (crossRefRepository.existsByGuaranteeCustLongNameAndGuaranteeCustomerNumberAndTerminalName(
				guaranteeCustCrossRef.getGuaranteeCustLongName(), guaranteeCustCrossRef.getGuaranteeCustomerNumber(),
				guaranteeCustCrossRef.getTerminalName())) {
			throw new RecordAlreadyExistsException(
					"Record with Combination of Corporate Customer: " + guaranteeCustCrossRef.getGuaranteeCustLongName()
							+ " ,Customer: " + guaranteeCustCrossRef.getGuaranteeCustomerNumber()
							+ " and Terminal: " + guaranteeCustCrossRef.getTerminalName() + " is already exists!");
		}
	}

}
