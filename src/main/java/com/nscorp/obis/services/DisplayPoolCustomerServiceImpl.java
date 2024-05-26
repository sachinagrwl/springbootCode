package com.nscorp.obis.services;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.CustomerPool;
import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerPoolRepository;
import com.nscorp.obis.repository.PoolRepository;

@Service
@Transactional
public class DisplayPoolCustomerServiceImpl implements DisplayPoolCustomerService {

	@Autowired
	PoolRepository poolRepository;

	@Autowired
	CustomerPoolRepository customerPoolRepository;

	@Autowired
	CustomerInfoRepository customerInfoRepository;

	@Override
	public Pool addPoolCustomer(Pool pool, Map<String, String> headers) {
		UserId.headerUserID(headers);
		poolCustomerValidations(pool, headers);
		pool.getCustomers().forEach(customer -> {
			if (customer.getCustomerId() == null) {
				throw new NullPointerException("Customer Id should not be null!");
			}
			if (!customerInfoRepository.existsById(customer.getCustomerId())) {
				throw new NoRecordsFoundException(
						"Customer Id: " + customer.getCustomerId() + " is not valid as it doesn't exists in CUSTOMER");
			}
			CustomerInfo existingCustomerInfo = customerInfoRepository.findById(customer.getCustomerId()).get();
			if (StringUtils.equalsIgnoreCase(CommonConstants.TRAILER_RSRV_TYPE, pool.getPoolReservationType())
					&& (StringUtils.equalsIgnoreCase("TO1", existingCustomerInfo.getTeamAudCd())
							|| StringUtils.equalsIgnoreCase("TO2", existingCustomerInfo.getTeamAudCd()))) {
				throw new RecordNotAddedException(
						"Record not added as Customer : " + existingCustomerInfo.getCustomerName()
								+ " having Team Audit code as (TO1 or TO2) are invalid for RR Trailer Pool");
			}
			if (!customerPoolRepository.existsByCustomerIdAndPoolId(customer.getCustomerId(), pool.getPoolId())) {
				CustomerPool customerPool = new CustomerPool();
				customerPool.setUversion("!");
				customerPool.setCreateUserId(headers.get(CommonConstants.USER_ID));
				customerPool.setUpdateUserId(headers.get(CommonConstants.USER_ID));
				customerPool.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
				customerPool.setPoolId(pool.getPoolId());
				customerPool.setCustomerId(customer.getCustomerId());
				customerPoolRepository.saveAndFlush(customerPool);
			} else {
				throw new RecordAlreadyExistsException("Record with Pool Id: " + pool.getPoolId()
						+ " and Customer Name: " + existingCustomerInfo.getCustomerName() + " is already exists!");
			}
		});
		return poolRepository.findById(pool.getPoolId()).get();
	}

	private void poolCustomerValidations(Pool pool, Map<String, String> headers) {
		if (StringUtils.isBlank(headers.get(CommonConstants.EXTENSION_SCHEMA))) {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}
		if (pool.getPoolId() == null || !poolRepository.existsByPoolId(pool.getPoolId())) {
			throw new NoRecordsFoundException(
					"Pool Id: " + pool.getPoolId() + " is not valid as it doesn't exists in Pool");
		}

	}

	@Override
	public Pool deletePool(Pool poolObj) {
		Pool poolCust = poolRepository.findById(poolObj.getPoolId()).get();
		poolObj.getCustomers().forEach(customer -> {
			if (customerPoolRepository.existsByCustomerIdAndPoolIdAndUversion(customer.getCustomerId(),
					poolObj.getPoolId(), customer.getUversion())) {
				CustomerPool existingPool = customerPoolRepository.findByCustomerIdAndPoolId(customer.getCustomerId(),
						poolObj.getPoolId());
				customerPoolRepository.delete(existingPool);

			} else {
				String rep = "No record Found Under this Customer Id:" + customer.getCustomerId() + " and Pool Id:"
						+ poolObj.getPoolId() + " and Uversion:" + customer.getUversion();
				throw new RecordNotDeletedException(rep);
			}

		});
		return poolCust;
	}

}
