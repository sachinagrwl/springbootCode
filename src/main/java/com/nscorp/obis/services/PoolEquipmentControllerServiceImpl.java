package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.PoolEquipmentConflictController;
import com.nscorp.obis.domain.PoolEquipmentController;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.PoolEquipmentConflictControllerRepository;
import com.nscorp.obis.repository.PoolEquipmentControllerRepository;
import com.nscorp.obis.repository.PoolRepository;

@Service
@Transactional
public class PoolEquipmentControllerServiceImpl implements PoolEquipmentControllerService {

	@Autowired
	PoolEquipmentControllerRepository controllerRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	PoolRepository poolRepo;

	@Autowired
	PoolEquipmentConflictControllerRepository conflictRepository;

	@Override
	public List<PoolEquipmentController> getAllPool() {

		List<PoolEquipmentController> poolControllerList = controllerRepo.findAll();

		if (poolControllerList.isEmpty()) {
			throw new NoRecordsFoundException("No Records Found!");
		}

		return poolControllerList;
	}

	@Override
	public PoolEquipmentController insertPoolCtrl(PoolEquipmentController poolCtrl, Map<String, String> headers) {

		if (poolCtrl.getPoolId() == null) {
			throw new NoRecordsFoundException("Pool Id should not be null!");
		}

		if (poolCtrl.getCustomer() == null) {
			throw new NoRecordsFoundException("Customer should not be null!");
		}

		if (poolCtrl.getCustomer().getCustomerId() == null) {
			throw new NoRecordsFoundException("Customer Id should not be null!");
		}

		if (!poolRepo.existsByPoolId(poolCtrl.getPoolId())) {
			throw new NoRecordsFoundException(
					"PoolId: " + poolCtrl.getPoolId() + " is invalid as it doesn't exists in Pool");
		}

		if (!customerRepo.existsByCustomerId(poolCtrl.getCustomer().getCustomerId())) {
			throw new NoRecordsFoundException("Customer Id: " + poolCtrl.getCustomer().getCustomerId()
					+ " is invalid as it doesn't exists in Customer");
		}

		UserId.headerUserID(headers);

		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);

		if (poolCtrl.getCustomer() != null) {
			Customer customerId = customerRepo.findById(poolCtrl.getCustomer().getCustomerId()).get();
			poolCtrl.setCustomer(customerId);
		}

		if (poolCtrl.getCustomerPrimary6() == null) {
			Customer customer = customerRepo.findByCustomerId(poolCtrl.getCustomer().getCustomerId());
			String custNr = customer.getCustomerNumber().substring(0, 6);
			poolCtrl.setCustomerPrimary6(custNr);
		}

		if (conflictRepository.existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(poolCtrl.getEquipmentType(),
				poolCtrl.getCustomerPrimary6(), poolCtrl.getPoolId())) {
			List<PoolEquipmentConflictController> conflictCtlrList = conflictRepository
					.findByControllerTypeAndCustomerPrimary6AndPoolControllerId(poolCtrl.getEquipmentType(),
							poolCtrl.getCustomerPrimary6(), poolCtrl.getPoolId());
			
			String newLine = System.getProperty("line.separator") ;
			String exceptionMessage = "The Controller Conflict Identified on Pool Name - Equipment Type - Customer Primary 6 - Terminal : ";
			StringBuilder exceptionMessageBuilder = new StringBuilder();
			exceptionMessageBuilder.append(exceptionMessage + newLine);
			
			for(int i = 0; i < conflictCtlrList.size(); i++) {
				if(i > 0 && i < conflictCtlrList.size()) {
					exceptionMessageBuilder.append(" , ");
				}
				exceptionMessageBuilder.append(conflictCtlrList.get(i).getPool().getPoolName() + " - " + conflictCtlrList.get(i).getControllerType() + " - " + conflictCtlrList.get(i).getCustomerPrimary6() + " - " + conflictCtlrList.get(i).getTerminal().getTerminalName() + newLine);
			}
			
			throw new RecordNotAddedException(exceptionMessageBuilder.toString());
		}

		if (controllerRepo.existsByPoolIdAndEquipmentTypeAndCustomerPrimary6(poolCtrl.getPoolId(),
				poolCtrl.getEquipmentType(), poolCtrl.getCustomerPrimary6())) {
			throw new RecordAlreadyExistsException("Record Already Exists under Pool Id: " + poolCtrl.getPoolId()
					+ " ,Equipment Type: " + poolCtrl.getEquipmentType() + " and Customer Primary 6: "
					+ poolCtrl.getCustomerPrimary6());
		}

		if (extensionSchema != null) {
			extensionSchema = extensionSchema.toUpperCase();
		} else {
			throw new NullPointerException(CommonConstants.EXTENSION_SCHEMA_EXCEPTION_MESSAGE);
		}
		
		Long generatedPoolControllerId = controllerRepo.SGKLong();
		poolCtrl.setPoolControllerId(generatedPoolControllerId);

		poolCtrl.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		poolCtrl.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		poolCtrl.setUpdateExtensionSchema(extensionSchema);
		poolCtrl.setUversion("!");

		PoolEquipmentController poolEqCtrl = controllerRepo.save(poolCtrl);
		return poolEqCtrl;
	}

	@Override
	public PoolEquipmentController updatePoolCtrl(PoolEquipmentController poolCtrl, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (controllerRepo.existsByPoolControllerIdAndUversion(poolCtrl.getPoolControllerId(),
				poolCtrl.getUversion())) {

			PoolEquipmentController existingPoolCtrl = controllerRepo.findById(poolCtrl.getPoolControllerId()).get();

			if (conflictRepository.existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(poolCtrl.getEquipmentType(),
					existingPoolCtrl.getCustomerPrimary6(), existingPoolCtrl.getPoolId())) {
				
				List<PoolEquipmentConflictController> conflictCtlrList = conflictRepository
						.findByControllerTypeAndCustomerPrimary6AndPoolControllerId(poolCtrl.getEquipmentType(),
								existingPoolCtrl.getCustomerPrimary6(), existingPoolCtrl.getPoolId());
				
				String exceptionMessage = "The Controller Conflict Identified on Pool Name - Equipment Type - Customer Primary 6 - Terminal : ";
				StringBuilder exceptionMessageBuilder = new StringBuilder();
				exceptionMessageBuilder.append(exceptionMessage);
				
				for(int i = 0; i < conflictCtlrList.size(); i++) {
					if(i > 0 && i < (conflictCtlrList.size() - 1)) {
						exceptionMessageBuilder.append(" , ");
					}
					exceptionMessageBuilder.append(conflictCtlrList.get(i).getPool().getPoolName() + " - " + conflictCtlrList.get(i).getControllerType() + " - " + conflictCtlrList.get(i).getCustomerPrimary6() + " - " + conflictCtlrList.get(i).getTerminal().getTerminalName());
				}
				
				throw new RecordNotAddedException(exceptionMessageBuilder.toString());
				
			}

			if (controllerRepo.existsByPoolIdAndEquipmentTypeAndCustomerPrimary6(existingPoolCtrl.getPoolId(),
					poolCtrl.getEquipmentType(), existingPoolCtrl.getCustomerPrimary6())) {
				throw new RecordAlreadyExistsException("Record Already Exists under Pool Id: " + existingPoolCtrl.getPoolId()
						+ " ,Equipment Type: " + poolCtrl.getEquipmentType() + " and Customer Primary 6: "
						+ existingPoolCtrl.getCustomerPrimary6());
			}

			String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
			if (extensionSchema != null) {
				extensionSchema = extensionSchema.toUpperCase();
			} else {
				throw new NullPointerException(CommonConstants.EXTENSION_SCHEMA_EXCEPTION_MESSAGE);
			}

			existingPoolCtrl.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			existingPoolCtrl.setUpdateExtensionSchema(extensionSchema);
			existingPoolCtrl.setEquipmentType(poolCtrl.getEquipmentType());

			/* Audit fields */
			if (StringUtils.isNotEmpty(existingPoolCtrl.getUversion())) {
				existingPoolCtrl.setUversion(
						Character.toString((char) ((((int) existingPoolCtrl.getUversion().charAt(0) - 32) % 94) + 33)));
			}

			return controllerRepo.save(existingPoolCtrl);
		} else {
			String rep = "No record Found Under this Pool Controller Id:" + poolCtrl.getPoolControllerId()
					+ " and Uversion:" + poolCtrl.getUversion();
			throw new NoRecordsFoundException(rep);
		}
	}

	@Override
	public PoolEquipmentController deletePoolCtrl(PoolEquipmentController poolCtrl) {
		if (controllerRepo.existsByPoolControllerIdAndUversion(poolCtrl.getPoolControllerId(),
				poolCtrl.getUversion())) {
			PoolEquipmentController existingPoolCtrl = controllerRepo.findById(poolCtrl.getPoolControllerId()).get();
			controllerRepo.deleteById(poolCtrl.getPoolControllerId());
			return existingPoolCtrl;
		} else {
			String rep = "No record Found Under this Pool Controller Id:" + poolCtrl.getPoolControllerId()
					+ " and Uversion:" + poolCtrl.getUversion();
			throw new RecordNotDeletedException(rep);
		}
	}

}
