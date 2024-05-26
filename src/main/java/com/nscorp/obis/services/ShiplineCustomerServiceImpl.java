package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.domain.ShiplineCustomer;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.ShiplineCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ShiplineCustomerServiceImpl implements ShiplineCustomerService {
	
	@Autowired
	private ShiplineCustomerRepository steamshipCustomerRepo;
	
	private CorporateCustomer corporateCustomer;
	
	@Autowired
	private CorporateCustomerRepository corporateCustomerRepo;

	@Override
	public List<ShiplineCustomer> getAllSteamshipCustomers() {
		List<ShiplineCustomer> steamshipCustomers = steamshipCustomerRepo.findAll();
		if(steamshipCustomers.isEmpty()) {
			throw new NoRecordsFoundException("No Records Found!");
		}
		return steamshipCustomers;
	}

	@Override
	public ShiplineCustomer addSteamshipCustomer(@Valid ShiplineCustomer steamshipCustomerObj,
			Map<String, String> headers) {
		
		UserId.headerUserID(headers);
		if(steamshipCustomerRepo.existsByShiplineNumber(steamshipCustomerObj.getShiplineNumber())) {
			throw new RecordAlreadyExistsException("Record with Shipline Number: "+steamshipCustomerObj.getShiplineNumber()
			+" Already Exists!");
		}
		
		if(corporateCustomerRepo.existsByCorporateLongNameAndCustomerId(steamshipCustomerObj.getDescription(), steamshipCustomerObj.getCustomerId())) {
			steamshipCustomerObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
			steamshipCustomerObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			steamshipCustomerObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			steamshipCustomerObj.setUversion("!");
			ShiplineCustomer steamshipCustomer=steamshipCustomerRepo.save(steamshipCustomerObj);
			if(steamshipCustomer== null) {
				throw new RecordNotAddedException("Record with Shipline Number: "+steamshipCustomerObj.getShiplineNumber()+" Not Added !");
			}
			return steamshipCustomer;
		}else {
			throw new NoRecordsFoundException("No Record found for Customer Name: "+steamshipCustomerObj.getDescription());
		}	
	}

	@Override
	public void deleteCustomer(@Valid ShiplineCustomer steamshipCustomerObj) {
			if(steamshipCustomerRepo.existsByShiplineNumber(steamshipCustomerObj.getShiplineNumber())) {
				steamshipCustomerRepo.deleteByShiplineNumber(steamshipCustomerObj.getShiplineNumber());
			}else {
				String rep = steamshipCustomerObj.getShiplineNumber()  + " and " + steamshipCustomerObj.getDescription() + " Record Not Found!";
				throw new RecordNotDeletedException(rep);
			}
		}

}
