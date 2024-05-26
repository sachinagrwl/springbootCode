package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerPool;

import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerPoolRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.dto.CustomerPoolDTO;
import com.nscorp.obis.dto.PoolListDTO;
import com.nscorp.obis.dto.mapper.CustomerPoolMapper;
import com.nscorp.obis.exception.InvalidDataException;

import com.nscorp.obis.repository.CustomerInfoRepository;

import com.nscorp.obis.repository.PoolRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerPoolServiceImpl implements CustomerPoolService {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerPoolRepository customerPoolRepository;

	@Autowired
	SpecificationGenerator specificationGenerator;

	@Autowired
	CustomerInfoRepository customerInfoRepo;

	@Autowired
	PoolRepository poolRepo;

	@Override
	public List<CustomerPool> fetchCustomerPool(@Valid Long customerId) throws SQLException {
		List<CustomerPool> customerPools = customerPoolRepository.findByCustomerId(customerId);
		if (customerPools.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for this Customer.");
		}
		return customerPools;
	}

	@Transactional
	@Override
	public List<CustomerPoolDTO> updateCustomerPools(PoolListDTO poolListDTO, Map<String, String> headers)
			throws SQLException {
		log.info("CustomerPoolServiceImpl : updateCustomerPools : Method Starts");
		UserId.headerUserID(headers);
		Long custId = poolListDTO.getCustomerId();
		Set<Long> pools = new HashSet<Long>();
		if (!customerInfoRepo.existsById(custId)) {
			throw new NoRecordsFoundException("No Customer Record found for this Customer Id :" + custId);
		}
		poolListDTO.getPoolIds().stream().forEach((poolId) -> {
			if (poolId == null) {
				throw new InvalidDataException("Pool Id Can't be null");
			}
			if (!poolRepo.existsById(poolId)) {
				throw new NoRecordsFoundException("No Pool Record found for this Pool Id :" + poolId);
			}
			pools.add(poolId);
		});
		Set<Long> existedPools = new HashSet<Long>();
		List<CustomerPool> customerPools = customerPoolRepository.findByCustomerId(custId);
		customerPools.stream().forEach((pool) -> {
			existedPools.add(pool.getPoolId());
		});
		Set<Long> addPools = new HashSet<Long>(pools);
		addPools.removeAll(existedPools);
		Set<Long> removePools = new HashSet<Long>(existedPools);
		removePools.removeAll(pools);
		List<CustomerPool> poolsToRemove = new ArrayList<>();
		removePools.stream().forEach((poolId) -> {
			CustomerPool customerPool = customerPoolRepository.findByCustomerIdAndPoolId(custId, poolId);
			if (customerPool != null)
				poolsToRemove.add(customerPool);
		});
		if (poolsToRemove.size() > 0) {
			customerPoolRepository.deleteAll(poolsToRemove);
		}
		List<CustomerPool> poolsToAdd = new ArrayList<>();
		addPools.stream().forEach((poolId) -> {
			CustomerPool customerPool = new CustomerPool();
			customerPool.setCustomerId(custId);
			customerPool.setPoolId(poolId);
			customerPool.setUversion("!");
			customerPool.setCreateUserId(headers.get("userid"));
			customerPool.setUpdateUserId(headers.get("userid"));
			if (headers.get("extensionschema") != null) {
				customerPool.setUpdateExtensionSchema(headers.get("extensionschema"));
			} else {
				customerPool.setUpdateExtensionSchema("IMS02691");
			}
			poolsToAdd.add(customerPool);
		});
		if (poolsToAdd.size() > 0) {
			customerPoolRepository.saveAll(poolsToAdd);
		}
		customerPools = customerPoolRepository.findByCustomerId(custId);
		List<CustomerPoolDTO> customerPoolDTOs = new ArrayList<>();
		customerPools.stream().forEach((customerPool) -> {
			customerPoolDTOs.add(CustomerPoolMapper.INSTANCE.CustomerPoolToCustomerPoolDTO(customerPool));
		});
		log.info("CustomerPoolServiceImpl : updateCustomerPools : Method Ends");
		return customerPoolDTOs;
	}

}
