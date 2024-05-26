package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.PoolCarStorageExempt;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.PoolCarStorageExemptRepository;
import com.nscorp.obis.repository.PoolRepository;
import com.nscorp.obis.repository.PoolTypeRepository;

@Service
@Transactional
public class PoolCarStorageExemptServiceImpl implements PoolCarStorageExemptService {

	@Autowired
	PoolCarStorageExemptRepository carStorageExemptRepository;

	@Autowired
	PoolRepository poolRepository;

	@Autowired
	PoolTypeRepository poolTypeRepository;

	@Override
	public List<PoolCarStorageExempt> getAllPoolCarStorageExempts() {
		List<PoolCarStorageExempt> carExempts = carStorageExemptRepository
				.findAll(Sort.by("pool.description").and(Sort.by("pool.poolReservationType")));
		if (carExempts.isEmpty()) {
			throw new NoRecordsFoundException("No Records are found for Pool Car Storage Exempts!");
		}
		return carExempts;
	}

	@Override
	public PoolCarStorageExempt addPoolCarStorageExempt(PoolCarStorageExempt poolCarStorageExempt,
			Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (poolCarStorageExempt.getPoolId() == null) {
			throw new RecordNotAddedException("Pool Id cannot be null!");
		}
		if (carStorageExemptRepository.existsById(poolCarStorageExempt.getPoolId())) {
			throw new RecordAlreadyExistsException("Record under this Pool Id: " + poolCarStorageExempt.getPoolId()
					+ " is already exists on Car Exempt");
		}
		if (!poolRepository.existsById(poolCarStorageExempt.getPoolId())) {
			throw new RecordNotAddedException("Record under this Pool Id: " + poolCarStorageExempt.getPoolId()
					+ " is not valid as it doesn't exists on Pool");
		}
		poolCarStorageExempt.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		poolCarStorageExempt.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		if (StringUtils.isNotBlank(extensionSchema)) {
			poolCarStorageExempt.setUpdateExtensionSchema(extensionSchema.toUpperCase());
		} else {
			throw new RecordNotAddedException("Extension Schema should not be null, empty or blank");
		}
		poolCarStorageExempt.setUversion("!");

		PoolCarStorageExempt savedCarExempt = carStorageExemptRepository.save(poolCarStorageExempt);
		savedCarExempt.setPool(poolRepository.findById(savedCarExempt.getPoolId()).get());
		return savedCarExempt;
	}

	@Override
	public PoolCarStorageExempt deletePoolCarStorageExempt(PoolCarStorageExempt poolCarStorageExempt) {
		if (carStorageExemptRepository.existsByPoolIdAndUversion(poolCarStorageExempt.getPoolId(),
				poolCarStorageExempt.getUversion())) {
			PoolCarStorageExempt existingPoolCarExempt = carStorageExemptRepository
					.findById(poolCarStorageExempt.getPoolId()).get();
			carStorageExemptRepository.deleteById(poolCarStorageExempt.getPoolId());
			return existingPoolCarExempt;
		} else
			throw new NoRecordsFoundException("No record Found to delete Under this Pool Id: "
					+ poolCarStorageExempt.getPoolId() + " and U_Version: " + poolCarStorageExempt.getUversion());
	}

}
