package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import com.nscorp.obis.repository.UnCdRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.HazRestrPermit;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.HazRestrPermitRepository;

@Service
@Transactional
public class HazRestrPermitServiceImpl implements HazRestrPermitService {

	@Autowired
	HazRestrPermitRepository hazRestrPermitRepo;
	
	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	UnCdRepository unCdRepository;

	
	
	public List<HazRestrPermit> getHazRestrPermit() {
		
		List<HazRestrPermit> hazRestrPermitList = hazRestrPermitRepo.findAll();
		if(hazRestrPermitList.isEmpty()) {
			throw new NoRecordsFoundException("No Records found");
		}
		for(HazRestrPermit hazRestrPermit: hazRestrPermitList) {
			Customer customerData = customerRepo.findByCustomerId(hazRestrPermit.getCustomerId());
			hazRestrPermit.setCustomer(customerData);
		}
		return hazRestrPermitList;
	}

	@Override
	public HazRestrPermit deleteHazRestrPermit(HazRestrPermit object) {
		if (hazRestrPermitRepo.existsByCustomerIdAndUnCd(object.getCustomerId(),object.getUnCd())) {
			HazRestrPermit del = hazRestrPermitRepo.findByCustomerIdAndUnCd(object.getCustomerId(),object.getUnCd());
			del.setUversion(object.getUversion());
			hazRestrPermitRepo.deleteByCustomerIdAndUnCd(object.getCustomerId(),object.getUnCd());
            return del;
        } else {
            throw new RecordNotDeletedException("Record Not Found!");
        }
	}

	@Override
	public HazRestrPermit addHazRestrPermit(HazRestrPermit hazRestrPermit, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (! unCdRepository.existsByUnCd(hazRestrPermit.getUnCd()))
			throw new NoRecordsFoundException("Invalid UN Code");
		
		if (hazRestrPermitRepo.existsByUnCdAndCustomerId(hazRestrPermit.getUnCd(),hazRestrPermit.getCustomerId()))
			throw new RecordAlreadyExistsException("Customer Number already exists : " +hazRestrPermit.getUnCd());
		
		if (hazRestrPermitRepo.existsByPermitNr(hazRestrPermit.getPermitNr()))
			throw new RecordAlreadyExistsException("Duplicate Permit Number");

		if(hazRestrPermit.getCustomer()!=null?StringUtils.isEmpty(hazRestrPermit.getCustomer().getCustomerName()):true)
			throw new InvalidDataException("Customer Name should not be blank or null");

		if (! customerRepo.existsByCustomerName(hazRestrPermit.getCustomer().getCustomerName()))
			throw new NoRecordsFoundException("Invalid Customer, Use selection list");

		if (! customerRepo.existsByCustomerId(hazRestrPermit.getCustomerId()))
			throw new NoRecordsFoundException("Invalid Customer, Use selection list");

		hazRestrPermit.setCreateUserId(headers.get(CommonConstants.USER_ID));
		hazRestrPermit.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
		hazRestrPermit.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		hazRestrPermit.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		hazRestrPermit.setUversion("!");
		HazRestrPermit code = hazRestrPermitRepo.save(hazRestrPermit);
		code.setCustomer(customerRepo.findByCustomerId(hazRestrPermit.getCustomerId()));
		return code;
	}

	@Override
	public HazRestrPermit updateHazRestrPermit(HazRestrPermit hazRestrictionPermitReq, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (! unCdRepository.existsByUnCd(hazRestrictionPermitReq.getUnCd()))
			throw new NoRecordsFoundException("Invalid UN Code");
		if (! customerRepo.existsByCustomerId(hazRestrictionPermitReq.getCustomerId()))
			throw new NoRecordsFoundException("Invalid Customer, Use selection list");
		if (! customerRepo.existsByCustomerName(hazRestrictionPermitReq.getCustomer().getCustomerName()))
			throw new NoRecordsFoundException("Invalid Customer, Use selection list");
		if(!hazRestrPermitRepo.existsByCustomerIdAndUnCd(hazRestrictionPermitReq.getCustomerId(),hazRestrictionPermitReq.getUnCd())) {
			throw new NoRecordsFoundException("No Records Found");
		}
		if (hazRestrPermitRepo.existsByPermitNr(hazRestrictionPermitReq.getPermitNr()))
			throw new RecordAlreadyExistsException("Duplicate Permit Number");
		
		hazRestrictionPermitReq.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		hazRestrictionPermitReq.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
		hazRestrictionPermitReq.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		hazRestrictionPermitReq.setUversion(hazRestrictionPermitReq.getUversion());
		HazRestrPermit hazRestrPermit = hazRestrPermitRepo.save(hazRestrictionPermitReq);
		hazRestrPermit.setCustomer(customerRepo.findByCustomerId(hazRestrPermit.getCustomerId()));
		return hazRestrPermit;
	}
}
