package com.nscorp.obis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.CorporateCustomerDetailRepository;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.CustomerRepository;

@Service
@Transactional
public class CorporateCustomerDetailServiceImpl implements CorporateCustomerDetailService {

	@Autowired
	CorporateCustomerDetailRepository corporateCustomerDetailRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	CorporateCustomerRepository corpCustRepo;

	Customer customer;
	CorporateCustomerDetail corporateCustomerDetail;

	public List<CorporateCustomerDetail> getCorporateCustomerDetails(@Valid Long corpCustId, @Valid String corpCust6) {
		if (corpCust6 != null) {
			if (CollectionUtils.isEmpty(customerRepo.findByCustomerNumber(corpCust6))) {
				throw new NoRecordsFoundException("No Customer Primary Six exists for this id : " + corpCust6);
			}
		}
		if (corpCustId != null && corpCustId > 0) {
			if (!corporateCustomerDetailRepo.existsByCorpCustId(corpCustId)) {
				throw new NoRecordsFoundException("No Corporate Customer Found with this id : " + corpCustId);
			}
		}
		CorporateCustomerDetail corporateCustomerDetails = new CorporateCustomerDetail();
		List<CorporateCustomerDetail> corpCustDetail = new ArrayList<CorporateCustomerDetail>();
		if (corpCustId > 0 && corpCust6 == null) {
			corpCustDetail = corporateCustomerDetailRepo.findByCorpCustId(corpCustId);
			for (CorporateCustomerDetail corporateCustomerDetail : corpCustDetail) {
				List<Customer> customer = customerRepo.findByCustomerNumber(corporateCustomerDetail.getCorpCust6());
				corporateCustomerDetail.setCustomerNumber(customer);
			}
		} else if (corpCust6 != null) {
			List<Customer> customer = customerRepo.findByCustomerNumber(corpCust6);
			corporateCustomerDetails.setCustomerNumber(customer);
			corpCustDetail.add(corporateCustomerDetails);
		}
		return corpCustDetail;
	}

	@Override
	public CorporateCustomerDetail deleteCorpCustDetail(CorporateCustomerDetail corporateCustomerDetail) {
		
		List<CorporateCustomerDetail> custDtl = corporateCustomerDetailRepo.findByCorpCustId(corporateCustomerDetail.getCorpCustId());
		if(custDtl.size()>1){
		if (corporateCustomerDetail.getCorpCustId() != null) {
			if (!corporateCustomerDetailRepo.existsByCorpCustId(corporateCustomerDetail.getCorpCustId())) {
				throw new NoRecordsFoundException(
						"No Corp Customer Id Found with this id : " + corporateCustomerDetail.getCorpCustId());
			}
		}

		if (corporateCustomerDetail.getCorpCust6() != null) {
			if (!corporateCustomerDetailRepo.existsByCorpCust6(corporateCustomerDetail.getCorpCust6())) {
				throw new NoRecordsFoundException(
						"No Corp Customer 6 Found with this id : " + corporateCustomerDetail.getCorpCust6());
			}
		}
			CorporateCustomerDetail cust = corporateCustomerDetailRepo.findByCorpCustIdAndCorpCust6(
					corporateCustomerDetail.getCorpCustId(), corporateCustomerDetail.getCorpCust6());
		System.out.println(cust);	
		if (cust == null) {
			throw new NoRecordsFoundException("No Record found for this combination");
		}
		CorporateCustomer customer = corpCustRepo.findByCorporateCustomerId(corporateCustomerDetail.getCorpCustId());
		customer.setUpdateUserId(corporateCustomerDetail.getUpdateUserId());
		customer.setUpdateExtensionSchema("IMS02741");
		customer.setUpdateDateTime(corporateCustomerDetail.getUpdateDateTime());
		customer.setUversion("!");
		corpCustRepo.save(customer);
		corporateCustomerDetailRepo.deleteByCorpCustIdAndCorpCust6(corporateCustomerDetail.getCorpCustId(),corporateCustomerDetail.getCorpCust6());
		return corporateCustomerDetail;
		}
		else{
			throw new NoRecordsFoundException("Corporate customer must have atleast one primary 6");
		}
	}

	@Override
	public CorporateCustomerDetail addPrimary6(@Valid CorporateCustomerDetail corporateCustomerDetail,
			Map<String, String> headers) {
		UserId.headerUserID(headers);

		List<Customer> customerList = customerRepo.findByCustomerNumber(corporateCustomerDetail.getCorpCust6());
		if (CollectionUtils.isEmpty(customerList)) {
			throw new NoRecordsFoundException("No Matches found on this primary six, re-enter !");
		}

		if (corporateCustomerDetailRepo.existsByCorpCust6(corporateCustomerDetail.getCorpCust6())) {
			throw new RecordAlreadyExistsException("Primary Six Customer already set for another Corporate Customer");
		}

		corporateCustomerDetail.setCreateUserId(headers.get(CommonConstants.USER_ID));
		corporateCustomerDetail.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		corporateCustomerDetail.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		corporateCustomerDetail.setUversion("!");

		corporateCustomerDetail.setCorpCustId(corporateCustomerDetail.getCorpCustId());
		CorporateCustomerDetail code = corporateCustomerDetailRepo.save(corporateCustomerDetail);
		return code;

//		short procOutput;
//		procOutput = corporateCustomerDetailRepo.inOnlyTest(customerList.get(0).getCustomerId(),corporateCustomerDetail.getCorpCust6());
//      customer.getCustomerId()
//		
//		if (procOutput == 1) {
//
//			return code;
//		} else {
//
//			throw new NoRecordsFoundException("Customer Update not Successfull !");
//		}

	}

}
