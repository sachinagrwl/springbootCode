package com.nscorp.obis.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.exception.RecordNotAddedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.domain.CustomerScac;
import com.nscorp.obis.domain.DrayageCompany;
import com.nscorp.obis.domain.DrayageCustomer;
import com.nscorp.obis.domain.DrayageCustomerInfo;
import com.nscorp.obis.dto.CustomerScacDTO;
import com.nscorp.obis.dto.DrayageCustomerInfoDTO;
import com.nscorp.obis.dto.mapper.CustomerScacMapper;
import com.nscorp.obis.dto.mapper.DrayageCustomerInfoMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.CustomerScacRepository;
import com.nscorp.obis.repository.DrayageCompanyRepository;
import com.nscorp.obis.repository.DrayageCustomerInfoRepository;
import com.nscorp.obis.repository.DrayageCustomerRepository;
import com.nscorp.obis.repository.DrayageSCACRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TruckerMaintenanceServiceImpl implements TruckerMaintenanceService {
    @Autowired
    DrayageCustomerRepository drayageCustomerRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerInfoRepository custInfoRepo;
    @Autowired
    DrayageSCACRepository drayageScacRepo;
    @Autowired
    DrayageCompanyRepository drayageCompanyRepository;
    @Autowired
    DrayageCustomerInfoRepository drayageCustomerInfoRepo;
    
    @Autowired
    CustomerScacRepository customerScacRepo;
    
    @Autowired
    CustomerScacMapper mapper;
    
    @Autowired
    SpecificationGenerator specificationGenerator;

    @Override
    public List<DrayageCustomer> fetchDrayageCustomers(Long customerId, String drayageId) {
        if (customerId != null && !customerRepository.existsByCustomerId(customerId)) {
            throw new InvalidDataException("Customer Id is invalid.");
        }
        List<DrayageCustomer> drayageCustomers = Collections.emptyList();
        if (customerId != null && (drayageId != null && !drayageId.isEmpty())) {
            drayageCustomers = drayageCustomerRepository.findByCustomerCustomerIdAndDrayageId(customerId, drayageId);
        } else if (customerId != null) {
            drayageCustomers = drayageCustomerRepository.findByCustomerCustomerId(customerId);
        } else if (drayageId != null && !drayageId.isEmpty()) {
            drayageCustomers = drayageCustomerRepository.findByDrayageId(drayageId);
        } else {

            throw new InvalidDataException("Pass any query parameter.");
            // drayageCustomers = drayageCustomerRepository.findAll();
        }
        if (drayageCustomers.isEmpty()) {
            throw new NoRecordsFoundException("No Records found for this query.");
        }
        return drayageCustomers;

    }

    @Override
    public List<DrayageCustomerInfoDTO> deleteDrayageCustomerInfo(List<DrayageCustomerInfoDTO> drayageCustomerInfoDTOS) {
    	List<DrayageCustomerInfo> drayageCustomerInfos=new ArrayList<>();
    	List<DrayageCustomerInfoDTO> drayCustomerInfoDTOs=new ArrayList<>();
    	drayageCustomerInfoDTOS.stream().forEach((drayageCustomerInfoDTO)->{
    		Long custId = drayageCustomerInfoDTO.getCustomerId();
            String drayId = drayageCustomerInfoDTO.getDrayageId();
            if (!custInfoRepo.existsById(custId))
                throw new NoRecordsFoundException("Customer record not found with given customer id :" + custId);
            if (!drayageScacRepo.existsById(drayId))
                throw new NoRecordsFoundException("Drayage record not found with given Drayage Id :" + drayId);
            List<DrayageCustomerInfo> drayageCustomerInfo = drayageCustomerInfoRepo.findByCustomerIdAndDrayageId(custId,
                    drayId);
            if (drayageCustomerInfo == null || drayageCustomerInfo.size() == 0)
                throw new NoRecordsFoundException("No Records Found For Given Customer Id "+custId+" And Drayage Id "+drayId);
           drayageCustomerInfo.stream().forEach((temp)->{
        	   drayageCustomerInfos.add(temp);
        	   drayCustomerInfoDTOs.add(DrayageCustomerInfoMapper.INSTANCE
                .drayageCustomerInfoToDrayageCustomerInfoDTO(temp));
           });
    	});
        
        drayageCustomerInfoRepo.deleteAll(drayageCustomerInfos);
        return drayCustomerInfoDTOs;
    }
	@Override
	public DrayageCustomerInfo addDrayageCustomer(DrayageCustomerInfo drayageCustomerInfo, Map<String, String> headers, String override) {
		
		if(!custInfoRepo.existsById(drayageCustomerInfo.getCustomerId()))
			throw new NoRecordsFoundException("No record found for this customer id ");
		else{
			if(drayageCustomerInfoRepo.existsByCustomerId(drayageCustomerInfo.getCustomerId())) {
				DrayageCustomerInfo existing = drayageCustomerInfoRepo.findByCustomerId(drayageCustomerInfo.getCustomerId());
				if(override.equalsIgnoreCase("Y")) {
					removeDrayageCustomerLink(existing);
					existing = drayageCustomerInfoRepo.findByCustomerId(drayageCustomerInfo.getCustomerId());
				}
				if(existing!=null){
					throw new RecordAlreadyExistsException("A customer can only be linked to one Drayage Company SCAC.\r\n"
							+ "Are you sure you want to remove Link between Customer Number - "+existing.getCustomerNumber()
							+ " and Drayage Company SCAC - "+existing.getDrayageId() +"?");
				}
			}
		}
		if(drayageCustomerInfo.getCustomerName() == null )
			throw new NoRecordsFoundException("Customer Name can't be null");
		if(drayageCustomerInfo.getCustomerNumber() == null )
			throw new NoRecordsFoundException("Customer Number can't be null");

		UserId.headerUserID(headers);
		drayageCustomerInfo.setCreateUserId(headers.get("userid"));
		drayageCustomerInfo.setUpdateUserId(headers.get("userid"));
		drayageCustomerInfo.setUversion("!");
		if (headers.get("extensionschema") != null)
		drayageCustomerInfo.setUpdateExtensionSchema(headers.get("extensionschema"));

		drayageCustomerInfo = drayageCustomerInfoRepo.save(drayageCustomerInfo);
		if (drayageCustomerInfo == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}

		return drayageCustomerInfo;
	}
	@Override
	public DrayageCustomerInfo addDrayageCustomerAndRemoveLink(DrayageCustomerInfo drayageCustomerInfo,String drayageId, Map<String, String> headers) {
		if (!drayageScacRepo.existsById(drayageId)) {
            throw new NoRecordsFoundException("Drayage record not found with given Drayage Id :" + drayageId);
		}
        if(!custInfoRepo.existsById(drayageCustomerInfo.getCustomerId())) {
			throw new NoRecordsFoundException("No record found for this customer id :"+drayageCustomerInfo.getCustomerId());
		}
		if(drayageCustomerInfo.getDrayageId()!=null) {
		   removeDrayageCustomerLink(drayageCustomerInfo);
		}
		if(drayageCustomerInfoRepo.existsByCustomerId(drayageCustomerInfo.getCustomerId())) {
				throw new RecordAlreadyExistsException("Association already exist for this CustomerId");
		}
		if(drayageCustomerInfo.getCustomerName() == null )
			throw new NoRecordsFoundException("Customer Name can't be null");
		if(drayageCustomerInfo.getCustomerNumber() == null )
			throw new NoRecordsFoundException("Customer Number can't be null");
        drayageCustomerInfo.setDrayageId(drayageId);
		UserId.headerUserID(headers);
		drayageCustomerInfo.setCreateUserId(headers.get("userid"));
		drayageCustomerInfo.setUpdateUserId(headers.get("userid"));
		drayageCustomerInfo.setUversion("!");
		if (headers.get("extensionschema") != null)
		drayageCustomerInfo.setUpdateExtensionSchema(headers.get("extensionschema"));

		drayageCustomerInfo = drayageCustomerInfoRepo.save(drayageCustomerInfo);
		if (drayageCustomerInfo == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}

		return drayageCustomerInfo;
	}
	public void removeDrayageCustomerLink(DrayageCustomerInfo drayageCustomer) {
		Long custId = drayageCustomer.getCustomerId();
		String drayId = drayageCustomer.getDrayageId();
		List<DrayageCustomerInfo> drayageCustomerInfo = drayageCustomerInfoRepo.findByCustomerIdAndDrayageId(custId,
                drayId);
        if (drayageCustomerInfo == null || drayageCustomerInfo.isEmpty()) {
            throw new NoRecordsFoundException("No Records Found For Given Customer Id "+custId+" And Drayage Id "+drayId);
        }
        drayageCustomerInfoRepo.deleteAll(drayageCustomerInfo);
        return;
	}

    @Override
    public DrayageCompany addDrayageCompany(DrayageCompany drayageCompany, Map<String, String> headers) {

        String drayId = drayageCompany.getDrayageId();
        if (!drayageScacRepo.existsById(drayId))
            throw new NoRecordsFoundException("Drayage record not found with given Drayage Id");

        if (drayageCompany.getTiaSuspendDate() != null && !drayageCompany.getTiaInd().equals("S")) {
            throw new InvalidDataException("Suspend date only allowed with TIA code of S");
        }

        if (drayageCompanyRepository.existsByDrayageId(drayId)) {
            throw new RecordAlreadyExistsException("Association already exist");
        }
        setDrayageCompanyCommon(drayageCompany, headers);
        DrayageCompany addedDrayageCompany = drayageCompanyRepository.save(drayageCompany);

        if (addedDrayageCompany == null) {
            throw new RecordNotAddedException("Record Not added to Database");
        }
        return addedDrayageCompany;
    }

    private static void setDrayageCompanyCommon(DrayageCompany drayageCompany, Map<String, String> headers) {
        UserId.headerUserID(headers);
        String userId = headers.get(CommonConstants.USER_ID);
        String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
        drayageCompany.setCreateUserId(userId.toUpperCase());
        drayageCompany.setUpdateUserId(userId.toUpperCase());
        drayageCompany.setUpdateExtensionSchema(extensionSchema);
        drayageCompany.setUversion("!");
    }

    @Override
    public List<DrayageCompany> fetchDrayageCompany(String drayageId) {

        List<DrayageCompany> drayageCompany = Collections.emptyList();
        if (drayageId != null && !drayageId.isEmpty()) {
            drayageCompany = drayageCompanyRepository.findByDrayageId(drayageId);
        }
        if (drayageCompany.isEmpty()) {
            throw new NoRecordsFoundException("No Records found for this query.");
        }
        return drayageCompany;
    }

	@Override
	public DrayageCompany updateDrayageCompany(DrayageCompany drayageCompany, Map<String, String> headers) {
		String drayId = drayageCompany.getDrayageId();
        if (!drayageScacRepo.existsById(drayId))
            throw new NoRecordsFoundException("Drayage record not found with given Drayage Id");

        if (drayageCompany.getTiaSuspendDate() != null && !drayageCompany.getTiaInd().equals("S")) {
            throw new InvalidDataException("Suspend date only allowed with TIA code of S");
        }
		if (!drayageCompanyRepository.existsByDrayageId(drayId)) {
			throw new NoRecordsFoundException("Drayage Company record not found with given Drayage Id");
		}
		DrayageCompany drayageCompanyObj = drayageCompanyRepository.getByDrayageId(drayId);
		drayageCompanyObj.setTiaInd(drayageCompany.getTiaInd());
		drayageCompanyObj.setBondedCarrier(drayageCompany.getBondedCarrier());
		drayageCompanyObj.setBondedAuthId(drayageCompany.getBondedAuthId());

		if (!drayageCompany.getTiaInd().equals("S")) {
			drayageCompanyObj.setTiaSuspendDate(null);
		} else {
			drayageCompanyObj.setTiaSuspendDate(drayageCompany.getTiaSuspendDate());
		}
		drayageCompanyObj.setEqAuthInd(drayageCompany.getEqAuthInd());
		setDrayageCompanyCommon(drayageCompanyObj, headers);
        DrayageCompany updatedDrayageCompany = drayageCompanyRepository.save(drayageCompanyObj);

        return updatedDrayageCompany;
	}

	@Override
	public List<CustomerScacDTO> fetchDrayageCustomersByPrimarySix(String customerPrimarySix) {
		log.info("fetchDrayageCustomersByPrimarySix : Method Starts");
		List<CustomerScac> customers = customerScacRepo
				.findAll(specificationGenerator.customerScacSpecification(customerPrimarySix));
		if (customers.isEmpty()) {
			throw new NoRecordsFoundException("No records found for given primary six");
		}
		log.info("fetchDrayageCustomersByPrimarySix : Method Ends");
		return customers.stream().map(mapper::customerScacTCustomerScacDTO).collect(Collectors.toList());
	}

}
