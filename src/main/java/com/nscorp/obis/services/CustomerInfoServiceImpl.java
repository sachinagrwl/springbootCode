
package com.nscorp.obis.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CityState;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.CustomerInfoDTO;

import com.nscorp.obis.dto.mapper.CustomerInfoMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CityStateRepository;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerNicknameRepository;
import com.nscorp.obis.repository.DrayageCustomerRepository;
import com.nscorp.obis.response.data.PaginatedResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CustomerInfoServiceImpl implements CustomerInfoService {

	@Autowired
	CustomerInfoRepository customerInfoRepo;

	@Autowired
	SpecificationGenerator specificationGenerator;

	@Autowired
	CityStateRepository cityStateRepo;

	@Autowired
	CustomerNicknameRepository custNickNameRepo;

	@Autowired
	DrayageCustomerRepository drayageRepo;

	@Override
	public PaginatedResponse<CustomerInfoDTO> fetchCustomers(Long customerId, String customerName,
			String customerNumber, Integer pageSize, Integer pageNumber, String[] sort, String[] filter,String fetchExpired) {
		log.info("CustomerInfoServiceImpl : fetchCustomers : Method Starts");
		Specification<CustomerInfo> specification = specificationGenerator.customerInfoSpecification(customerId,
				customerName, customerNumber, filter, fetchExpired);
		Page<CustomerInfo> customers;
		if (customerNumber != null) {
			if (!customerInfoRepo.existsByCustomerNumber(customerNumber)) {
				throw new NoRecordsFoundException("No Records Found For Given Customer Number");
			}
			String[] sortCustomerNumber = { "updateDateTime", "desc" };
			customers = customerInfoRepo.findAll(specification,
					PageRequest.of(0, 1, Sort.by(SortFilter.sortOrder(sortCustomerNumber))));
		} else {
			if(sort.length==2 && sort[0].contains("customerName")) {
				String[] defaultSort= {sort[0],"customerNumber,asc"};
				customers = customerInfoRepo.findAll(specification,
						PageRequest.of(pageNumber-1, pageSize, Sort.by(SortFilter.sortOrder(defaultSort))));
			}
			else {
			customers = customerInfoRepo.findAll(specification,
					PageRequest.of(pageNumber-1, pageSize, Sort.by(SortFilter.sortOrder(sort))));
			}
		}
		if (customers.isEmpty()) {
			throw new NoRecordsFoundException("No Records Found For Requested Combination");
		}
		List<CustomerInfoDTO> customerInfoDTOs = new ArrayList<>();
		customers.stream().forEach(cust -> {
			CustomerInfoDTO dto = CustomerInfoMapper.INSTANCE.customerInfoToCustomerInfoDTO(cust);
			if (dto.getBillToCustomerId() != null) {
				Optional<CustomerInfo> custOptional = customerInfoRepo.findById(dto.getBillToCustomerId());
				if (custOptional.isPresent())
					dto.setBillToCustomerName(custOptional.get().getCustomerName());
			}
			if (cust.getDrayageScac() != null) {
				dto.setScac(cust.getDrayageScac().getDrayId());
			}
			customerInfoDTOs.add(dto);
		});
		PaginatedResponse<CustomerInfoDTO> paginatedResponse = PaginatedResponse.of(customerInfoDTOs, customers);
		log.info("CustomerInfoServiceImpl : fetchCustomers : Method Ends");
		return paginatedResponse;
	}

	@Override
	public CustomerInfo addCustomer(CustomerInfo customerInfo, Map<String, String> headers) {
		Long id = (long) (Math.random() * Math.pow(10, 15));
		customerInfo.setCustomerId(id);

		String Number = String.valueOf(id);
		String custNumber = "T" + Number.substring(0, 9);
		customerInfo.setCustomerNumber(custNumber);

		if (customerInfo.getAddress1() == null && customerInfo.getAddress2() == null) {
			throw new InvalidDataException("Customer Address should not be null");
		}

		if (customerInfo.getState() != null) {
			List<CityState> cityList = cityStateRepo.findAllByStateAbbreviation(customerInfo.getState());
			if (cityList.size() == 0)
				throw new NoRecordsFoundException("No State Record Found For Given State Id");
		}
		if (customerInfo.getExpiredDate() != null) {
			throw new InvalidDataException("Expiry date cant be edited");
		}

		if (customerInfo.getBillToCustomerId() != null
				&& (!customerInfoRepo.existsById(customerInfo.getBillToCustomerId()))) {
			throw new NoRecordsFoundException(
					"No Record Exist with given Bill to CustomerId :" + customerInfo.getBillToCustomerId());
		}

		if (customerInfo.getBillToCustomerNumber() != null
				&& (!customerInfoRepo.existsByCustomerNumber(customerInfo.getBillToCustomerNumber()))) {
			throw new NoRecordsFoundException(
					"No Record Exist with given Bill to CustomerNumber :" + customerInfo.getBillToCustomerNumber());
		}
		
		if(customerInfo.getCustomerAreaCode()!=null && (customerInfo.getCustomerAreaCode()<99 || customerInfo.getCustomerAreaCode()>999)) {
			throw new InvalidDataException("customer area code should be between 99 and 1000");
		}
		if(customerInfo.getFaxAreaCode()!=null && (customerInfo.getFaxAreaCode()<99 || customerInfo.getFaxAreaCode()>999)) {
			throw new InvalidDataException("fax area code should be between 99 and 1000");
		}

		UserId.headerUserID(headers);
		customerInfo.setCreateUserId(headers.get("userid"));
		customerInfo.setUpdateUserId(headers.get("userid"));
		customerInfo.setUversion("!");
		if (headers.get("extensionschema") != null)
			customerInfo.setUpdateExtensionSchema(headers.get("extensionschema"));

		customerInfo = customerInfoRepo.save(customerInfo);
		if (customerInfo == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}

		return customerInfo;
	}

	@Override
	public CustomerInfoDTO updateCustomer(CustomerInfoDTO customerInfoDTO, Map<String, String> headers) {
		log.info("CustomerInfoServiceImpl : updateCustomer : Method Starts");
		UserId.headerUserID(headers);
		CustomerInfo customerInfo;
		Optional<CustomerInfo> custOptional = customerInfoRepo.findById(customerInfoDTO.getCustomerId());
		if (!custOptional.isPresent())
			throw new NoRecordsFoundException(
					"No Record Found For Given Customer Id / Customer Id can't be null or blank or not provided");
		customerInfo = custOptional.get();
		if (customerInfo.getExpiredDate() != null && customerInfoDTO.getExpiredDate() != null) {
			throw new InvalidDataException(
					"Record has been marked for deletion, no updates allowed unless record is restored.");
		}

		if (customerInfo.getExpiredDate() != null && customerInfoDTO.getExpiredDate() == null) {
			customerInfo.setExpiredDate(null);
			customerInfo.setActivityStatus("A");
			customerInfo.setUpdateUserId(headers.get("userid"));
			if (headers.get("extensionschema") != null)
				customerInfo.setUpdateExtensionSchema(headers.get("extensionschema"));
			customerInfo = customerInfoRepo.save(customerInfo);
			customerInfoDTO = CustomerInfoMapper.INSTANCE.customerInfoToCustomerInfoDTO(customerInfo);
			return customerInfoDTO;
		}

		if (customerInfo.getExpiredDate() == null && customerInfoDTO.getExpiredDate() != null) {
			Date currentDate = new Date(System.currentTimeMillis());
			if (!customerInfoDTO.getExpiredDate().toString().equals(currentDate.toString()))
				throw new InvalidDataException("Expired Date Not Matched With the Current Date !");
			customerInfo.setExpiredDate(customerInfoDTO.getExpiredDate());
			customerInfo.setActivityStatus("I");
			customerInfo.setUpdateUserId(headers.get("userid"));
			if (headers.get("extensionschema") != null)
				customerInfo.setUpdateExtensionSchema(headers.get("extensionschema"));
			customerInfo = customerInfoRepo.save(customerInfo);
			custNickNameRepo.deleteAllByCustomerId(customerInfo.getCustomerId());
			customerInfoDTO = CustomerInfoMapper.INSTANCE.customerInfoToCustomerInfoDTO(customerInfo);
			return customerInfoDTO;
		}

		if (customerInfoDTO.getState() != null && customerInfoDTO.getState() != customerInfo.getState()) {
			List<CityState> cityList = cityStateRepo.findAllByStateAbbreviation(customerInfoDTO.getState());
			if (cityList.size() == 0)
				throw new NoRecordsFoundException("No State Record Found For Given State Id");
		}
		if (customerInfo.getCustomerNumber().toLowerCase().startsWith("s")
				|| customerInfo.getCustomerNumber().toLowerCase().startsWith("t")) {
			customerInfo.setCustomerNumber(customerInfoDTO.getCustomerNumber());
			customerInfo.setCustomerName(customerInfoDTO.getCustomerName());
			customerInfo.setGoodSpellingName(customerInfoDTO.getGoodSpellingName());
			customerInfo.setCredit(customerInfoDTO.getCredit());
			customerInfo.setAddress1(customerInfoDTO.getAddress1());
			customerInfo.setAddress2(customerInfoDTO.getAddress2());
			customerInfo.setCity(customerInfoDTO.getCity());
			customerInfo.setCountry(customerInfoDTO.getCountry());
			customerInfo.setZipcode(customerInfoDTO.getZipcode());
			customerInfo.setPrimeContact(customerInfoDTO.getPrimeContact());
			customerInfo.setCustomerAreaCode(customerInfoDTO.getCustomerAreaCode());
			customerInfo.setCustomerExchangeNumber(customerInfoDTO.getCustomerExchangeNumber());
			customerInfo.setCustomerExtension(customerInfoDTO.getCustomerExtension());
			customerInfo.setCustomerBase(customerInfoDTO.getCustomerBase());
			customerInfo.setFaxAreaCode(customerInfoDTO.getFaxAreaCode());
			customerInfo.setFaxExchange(customerInfoDTO.getFaxExchange());
			customerInfo.setFaxNumber(customerInfoDTO.getFaxNumber());
			customerInfo.setTerritory(customerInfoDTO.getTerritory());
			customerInfo.setRegion(customerInfoDTO.getRegion());
			customerInfo.setDistrict(customerInfoDTO.getDistrict());
			customerInfo.setNsAccoutDescription(customerInfoDTO.getNsAccoutDescription());
			customerInfo.setCifNumber(customerInfoDTO.getCifNumber());
			customerInfo.setSalesmanCode(customerInfoDTO.getSalesmanCode());
			customerInfo.setTeamAudCd(customerInfoDTO.getTeamAudCd());
			customerInfo.setState(customerInfoDTO.getState());
			customerInfo.setActivityStatus(customerInfoDTO.getActivityStatus());
			if (customerInfo.getBillToCustomerId() != customerInfoDTO.getBillToCustomerId()) {
				if (customerInfoDTO.getBillToCustomerId() != null
						&& (!customerInfoRepo.existsById(customerInfoDTO.getBillToCustomerId()))) {
					throw new NoRecordsFoundException(
							"No Record Exist with given Bill to CustomerId :" + customerInfoDTO.getBillToCustomerId());
				}
				customerInfo.setBillToCustomerId(customerInfoDTO.getBillToCustomerId());
			}
			if (customerInfo.getBillToCustomerNumber() != customerInfoDTO.getBillToCustomerNumber()) {
				if (customerInfoDTO.getBillToCustomerNumber() != null
						&& (!customerInfoRepo.existsByCustomerNumber(customerInfoDTO.getBillToCustomerNumber()))) {
					throw new NoRecordsFoundException("No Record Exist with given Bill to CustomerNumber :"
							+ customerInfoDTO.getBillToCustomerNumber());
				}
				customerInfo.setBillToCustomerNumber(customerInfoDTO.getBillToCustomerNumber());
			}
			if((!Objects.equals(customerInfoDTO.getFaxAreaCode(), customerInfo.getFaxAreaCode()))) {
				if(customerInfo.getFaxAreaCode()!=null && (customerInfo.getFaxAreaCode()<99 || customerInfo.getFaxAreaCode()>999)) {
					throw new InvalidDataException("fax area code should be between 99 and 1000");
				}
			}
			if((!Objects.equals(customerInfoDTO.getCustomerAreaCode(), customerInfo.getCustomerAreaCode()))) {
				if(customerInfo.getCustomerAreaCode()!=null && (customerInfo.getCustomerAreaCode()<99 || customerInfo.getCustomerAreaCode()>999)) {
					throw new InvalidDataException("customer area code should be between 99 and 1000");
				}
			}
		} else {

			if ((!StringUtils.equals(customerInfoDTO.getCustomerNumber(), customerInfo.getCustomerNumber()))
					|| (!StringUtils.equals(customerInfoDTO.getCustomerName(), customerInfo.getCustomerName()))
					|| (!StringUtils.equals(customerInfoDTO.getGoodSpellingName(), customerInfo.getGoodSpellingName()))
					|| (!StringUtils.equals(customerInfoDTO.getCredit(), customerInfo.getCredit()))
					|| (!StringUtils.equals(customerInfoDTO.getAddress1(), customerInfo.getAddress1()))
					|| (!StringUtils.equals(customerInfoDTO.getAddress2(), customerInfo.getAddress2()))
					|| (!StringUtils.equals(customerInfoDTO.getCity(), customerInfo.getCity()))
					|| (customerInfoDTO.getCountry() != customerInfo.getCountry())
					|| (!StringUtils.equals(customerInfoDTO.getZipcode(), customerInfo.getZipcode()))
					|| (!StringUtils.equals(customerInfoDTO.getTeamAudCd(), customerInfo.getTeamAudCd()))
					|| (!StringUtils.equals(customerInfoDTO.getSalesmanCode(), customerInfo.getSalesmanCode()))
					|| (!StringUtils.equals(customerInfoDTO.getCifNumber(), customerInfo.getCifNumber()))
					|| (!StringUtils.equals(customerInfoDTO.getNsAccoutDescription(),
							customerInfo.getNsAccoutDescription()))
					|| (!StringUtils.equals(customerInfoDTO.getDistrict(), customerInfo.getDistrict()))
					|| (!StringUtils.equals(customerInfoDTO.getState(), customerInfo.getState()))
					|| (!Objects.equals(customerInfoDTO.getRegion(), customerInfo.getRegion()))
					|| (!Objects.equals(customerInfoDTO.getTerritory(), customerInfo.getTerritory()))
					|| (!Objects.equals(customerInfoDTO.getFaxNumber(), customerInfo.getFaxNumber()))
					|| (!Objects.equals(customerInfoDTO.getFaxExchange(), customerInfo.getFaxExchange()))
					|| (!Objects.equals(customerInfoDTO.getFaxAreaCode(), customerInfo.getFaxAreaCode()))
					|| (!Objects.equals(customerInfoDTO.getBillToCustomerId(), customerInfo.getBillToCustomerId()))
					|| (!StringUtils.equals(customerInfoDTO.getBillToCustomerNumber(),
							customerInfo.getBillToCustomerNumber()))
					|| (!StringUtils.equals(customerInfoDTO.getCustomerBase(), customerInfo.getCustomerBase()))
					|| (!Objects.equals(customerInfoDTO.getCustomerExtension(), customerInfo.getCustomerExtension()))
					|| (!Objects.equals(customerInfoDTO.getCustomerExchangeNumber(),
							customerInfo.getCustomerExchangeNumber()))
					|| (!Objects.equals(customerInfoDTO.getCustomerAreaCode(), customerInfo.getCustomerAreaCode()))
					|| (!StringUtils.equals(customerInfoDTO.getPrimeContact(), customerInfo.getPrimeContact())))
				throw new InvalidDataException(
						"Non temporary Customers are restricted for modification of all fields except the Intermodal Ind, Equip Owner Ind, Shipment Priority, and Intermodal Desc fields");
		}
		customerInfo.setIntermodalIdentification(customerInfoDTO.getIntermodalIdentification());
		customerInfo.setEquipmentOwner(customerInfoDTO.getEquipmentOwner());
		customerInfo.setShipmentPriority(customerInfoDTO.getShipmentPriority());
		customerInfo.setAccountDescription(customerInfoDTO.getAccountDescription());
		customerInfo.setUpdateUserId(headers.get("userid"));
		if (headers.get("extensionschema") != null)
			customerInfo.setUpdateExtensionSchema(headers.get("extensionschema"));
		customerInfo = customerInfoRepo.save(customerInfo);
		customerInfoDTO = CustomerInfoMapper.INSTANCE.customerInfoToCustomerInfoDTO(customerInfo);
		log.info("CustomerInfoServiceImpl : updateCustomer : Method Ends");
		return customerInfoDTO;
	}

}
