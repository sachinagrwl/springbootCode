package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import java.util.Optional;

import javax.validation.Valid;

import com.nscorp.obis.domain.CityState;
import com.nscorp.obis.repository.CityStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.CustomerLocalInfo;
import com.nscorp.obis.domain.CustomerLocalInfoPrimaryKeys;
import com.nscorp.obis.dto.CustomerLocalInfoDTO;
import com.nscorp.obis.dto.mapper.CustomerLocalInfoMapper;

import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerLocalInfoRepository;
import com.nscorp.obis.repository.TerminalRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerLocalInfoServiceImpl implements CustomerLocalInfoService {

	@Autowired
	CustomerLocalInfoRepository customerLocalInfoRepository;

	@Autowired
	CustomerInfoRepository customerInfoRepository;

	@Autowired
	TerminalRepository terminalRepository;

	@Autowired
	CityStateRepository cityStateRepo;

	@Override
	public CustomerLocalInfo fetchCustomerLocalInfo(@Valid Long customerId, @Valid Long terminalId)
			throws SQLException {
		if (terminalId != null) {
			if (!customerLocalInfoRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id : " + terminalId);
			}
		}

		if (customerId != null) {
			if (!customerLocalInfoRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer Found with this customer id : " + customerId);
			}
		}

		CustomerLocalInfo customerLocalInfo = customerLocalInfoRepository.findByCustomerIdAndTerminalId(customerId,
				terminalId);
		if (customerLocalInfo == null) {
			throw new NoRecordsFoundException("No Record found for this combination");
		}
		return customerLocalInfo;
	}

	@Override
	public CustomerLocalInfoDTO deleteCustomerLocalInfo(CustomerLocalInfoDTO customerLocalInfoDTO) {
		if (customerLocalInfoDTO.getTerminalId() != null) {
			if (!customerLocalInfoRepository.existsByTerminalId(customerLocalInfoDTO.getTerminalId())) {
				throw new NoRecordsFoundException(
						"No Terminal Found with this terminal id : " + customerLocalInfoDTO.getTerminalId());
			}
		}

		if (customerLocalInfoDTO.getCustomerId() != null) {
			if (!customerLocalInfoRepository.existsByCustomerId(customerLocalInfoDTO.getCustomerId())) {
				throw new NoRecordsFoundException(
						"No Customer Found with this customer id : " + customerLocalInfoDTO.getCustomerId());
			}
		}
		CustomerLocalInfo cust = customerLocalInfoRepository.findByCustomerIdAndTerminalId(
				customerLocalInfoDTO.getCustomerId(), customerLocalInfoDTO.getTerminalId());
		if (cust == null) {
			throw new NoRecordsFoundException("No Record found for this combination");
		}
		customerLocalInfoRepository.delete(cust);
		CustomerLocalInfoDTO custLocalInfoDTO = CustomerLocalInfoMapper.INSTANCE
				.customerLocalInfoToCustomerLocalInfoDTO(cust);
		return custLocalInfoDTO;
	}

	@Override
	public CustomerLocalInfo updateCustomerLocalInfo(@Valid CustomerLocalInfoDTO customerLocalInfoDTO,
			Map<String, String> headers) {
		Long customerId = customerLocalInfoDTO.getCustomerId();
		Long terminalId = customerLocalInfoDTO.getTerminalId();
		if (customerId == null) {
			throw new NoRecordsFoundException("Customer Id is required field");
		}
		if (terminalId == null) {
			throw new NoRecordsFoundException("Terminal Id is required field");
		}
		if (customerId != null) {
			if (!customerLocalInfoRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer Found with this customer id : " + customerId);
			}
		}
		if (terminalId != null) {
			if (!customerLocalInfoRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id : " + terminalId);
			}
		}
		CustomerLocalInfo customerLocalInfo = customerLocalInfoRepository.findByCustomerIdAndTerminalId(customerId,
				terminalId);
		if (customerLocalInfo == null) {
			throw new NoRecordsFoundException(
					"No record found for this customerId : " + customerId + " and terminalId : " + terminalId);
		}
		if(customerLocalInfoDTO.getCustomerState()!=null){
			List<CityState> cityList = cityStateRepo.findAllByStateAbbreviation(customerLocalInfoDTO.getCustomerState());
			if (cityList.size() == 0)
				throw new NoRecordsFoundException("No State Record Found For Given State Id");
		}

		UserId.headerUserID(headers);
		customerLocalInfo.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		customerLocalInfo.setCustomerName(customerLocalInfoDTO.getCustomerName());
		customerLocalInfo.setCustAddr1(customerLocalInfoDTO.getCustAddr1());
		customerLocalInfo.setCustAddr2(customerLocalInfoDTO.getCustAddr2());
		customerLocalInfo.setCustomerCity(customerLocalInfoDTO.getCustomerCity());
		customerLocalInfo.setCustZipCd(customerLocalInfoDTO.getCustZipCd());
		customerLocalInfo.setCustomerState(customerLocalInfoDTO.getCustomerState());
		customerLocalInfo.setCustCountry(customerLocalInfoDTO.getCustCountry());
		customerLocalInfo.setLocalContact(customerLocalInfoDTO.getLocalContact());
		customerLocalInfo.setFaxAreaCd(customerLocalInfoDTO.getFaxAreaCd());
		customerLocalInfo.setFaxExch(customerLocalInfoDTO.getFaxExch());
		customerLocalInfo.setFaxExt(customerLocalInfoDTO.getFaxExt());
		customerLocalInfo.setCustAreaCd(customerLocalInfoDTO.getCustAreaCd());
		customerLocalInfo.setCustExt(customerLocalInfoDTO.getCustExt());
		customerLocalInfo.setCustExch(customerLocalInfoDTO.getCustExch());
		customerLocalInfo.setAddtlInfo(customerLocalInfoDTO.getAddtlInfo());
		customerLocalInfo.setPhoneBase(customerLocalInfoDTO.getPhoneBase());
		customerLocalInfoRepository.save(customerLocalInfo);
		return customerLocalInfo;

	}

	@Override
	public CustomerLocalInfoDTO addCustomerLocalInfo(CustomerLocalInfoDTO customerLocalInfoDTO,
			Map<String, String> headers) {
		log.info("CustomerLocalInfoServiceImpl : addCustomerLocalInfo : Method Starts");
		if (headers.get(CommonConstants.USER_ID) == null) {
			throw new InvalidDataException("Headers are missing with key " + CommonConstants.USER_ID);
		}
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		UserId.headerUserID(headers);
		Optional<CustomerInfo> customerInfo = customerInfoRepository.findById(customerLocalInfoDTO.getCustomerId());
		if (!customerInfo.isPresent())
			throw new NoRecordsFoundException(
					"Customer Record Doesn't exist with given CustomerId :" + customerLocalInfoDTO.getCustomerId());
		if (!terminalRepository.existsById(customerLocalInfoDTO.getTerminalId()))
			throw new NoRecordsFoundException(
					"Terminal Record Doesn't exist with given TerminalId :" + customerLocalInfoDTO.getTerminalId());
		if (customerLocalInfoDTO.getCustomerName() == null) {
			customerLocalInfoDTO.setCustomerName(customerInfo.get().getCustomerName());
		}
		if (!customerInfo.get().getCustomerName().trim().equals(customerLocalInfoDTO.getCustomerName().trim()))
			throw new InvalidDataException("Customer Name is Invalid");
		CustomerLocalInfo cust = customerLocalInfoRepository.findByCustomerIdAndTerminalId(
				customerLocalInfoDTO.getCustomerId(), customerLocalInfoDTO.getTerminalId());
		if (cust != null) {
			throw new RecordAlreadyExistsException("Record Already exists with given terminalId and customerId");
		}
		if (customerLocalInfoDTO.getUversion() == null)
			customerLocalInfoDTO.setUversion("!");
		customerLocalInfoDTO.setCreateUserId(headers.get(CommonConstants.USER_ID));
		customerLocalInfoDTO.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		if (extensionSchema == null || extensionSchema.isEmpty() || extensionSchema.trim().isEmpty()) {
			customerLocalInfoDTO.setUpdateExtensionSchema("IMS02625");
		}
		else {
			customerLocalInfoDTO.setUpdateExtensionSchema(extensionSchema);
		}
		if(customerLocalInfoDTO.getCustomerState()!=null){
			List<CityState> cityList = cityStateRepo.findAllByStateAbbreviation(customerLocalInfoDTO.getCustomerState());
			if (cityList.size() == 0)
				throw new NoRecordsFoundException("No State Record Found For Given State Id");
		}
		CustomerLocalInfo custLocalinfo = CustomerLocalInfoMapper.INSTANCE
				.customerLocalInfoDTOToCustomerLocalInfo(customerLocalInfoDTO);
		custLocalinfo = customerLocalInfoRepository.save(custLocalinfo);
		log.info("CustomerLocalInfoServiceImpl : addCustomerLocalInfo : Method Ends");
		return CustomerLocalInfoMapper.INSTANCE.customerLocalInfoToCustomerLocalInfoDTO(custLocalinfo);
	}

}
