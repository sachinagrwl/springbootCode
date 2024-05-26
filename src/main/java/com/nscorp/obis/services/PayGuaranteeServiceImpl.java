package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.Map;

import com.nscorp.obis.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.domain.PayGuarantee;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerIndexRepository;
import com.nscorp.obis.repository.PayGuaranteeRepository;

@Service
public class PayGuaranteeServiceImpl implements PayGuaranteeService {

	@Autowired
	PayGuaranteeRepository payGuaranteeRepo;

	@Autowired
	CustomerIndexRepository customerRepository;

	@Override
	public PayGuarantee getPayGuarantee(Long chrgId) {

		if (chrgId != null) {
			if (!payGuaranteeRepo.existsByChrgId(chrgId)) {
				throw new NoRecordsFoundException("No Payment Guarantee found for this Equipment/Shipment Id combination");
			}
		}
		PayGuarantee payInfo = new PayGuarantee();
		payInfo = payGuaranteeRepo.findByChrgId(chrgId);
		CustomerIndex custInfo = customerRepository.findByCustomerId(payInfo.getGuarCustId());
		payInfo.setCustomer(custInfo);

		return payInfo;
	}

	@Override
	public PayGuarantee addPayGuarantee(PayGuarantee payGuarantee, Map<String, String> headers) {
		UserId.headerUserID(headers);
		
		if (payGuarantee.getGuarCustId() == null || customerRepository.findByCustomerId(payGuarantee.getGuarCustId())== null) {
			throw new RecordNotAddedException("You must select the customer by detailing on Name or Number");
		}
		payGuarantee.setPayGuarId(payGuaranteeRepo.SGK());
		payGuarantee.setCreateUserId(headers.get(CommonConstants.USER_ID));
		payGuarantee.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		payGuarantee.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		payGuarantee.setUversion("!");
		PayGuarantee code = payGuaranteeRepo.save(payGuarantee);
		return code;
	}

	@Override
	public PayGuarantee updatePayGuarantee(PayGuarantee payGuarantee, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if(payGuarantee.getPayGuarId()==null)
			throw new InvalidDataException("Provide Primary Key");

		if(!payGuaranteeRepo.existsByChrgId(payGuarantee.getChrgId()))
			throw new NoRecordsFoundException("No Record Found!");

		if (payGuarantee.getGuarCustId() == null || customerRepository.findByCustomerId(payGuarantee.getGuarCustId())== null)
			throw new RecordNotAddedException("You must select the customer by detailing on Name or Number");

		payGuarantee.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		payGuarantee.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
		payGuarantee.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		payGuarantee.setUversion("!");
		PayGuarantee payGuaranteeFinal = payGuaranteeRepo.save(payGuarantee);
		return payGuaranteeFinal;
	}

}
