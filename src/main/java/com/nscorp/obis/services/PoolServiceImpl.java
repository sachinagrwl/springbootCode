package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nscorp.obis.domain.*;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;

@Transactional
@Service
@Slf4j
public class PoolServiceImpl implements PoolService {
	
    @Autowired
    PoolRepository repository;

	@Autowired
	PoolTypeRepository poolTypeRepository;

	@Autowired
	TruckerGroupRepository truckerGroupRepository;
    
    @Autowired
    PoolTerminalRepository poolTermRepo;
    
    @Autowired
	SpecificationGenerator specificationGenerator;

	@Autowired
	PoolEquipmentControllerRepository poolEqControllerRepo;

	@Autowired
	PoolEquipmentRangeRepository poolEqRangeRepo;

	private void poolValidations(Pool pool){

		if (pool.getPoolReservationType() != null) {
			if (!poolTypeRepository.existsById(pool.getPoolReservationType())) {
				throw new RecordNotAddedException("Reservation Type is not valid");
			}
		}

		if (pool.getTruckerGroup() != null) {

			if (!truckerGroupRepository.existsByTruckerGroupCode(pool.getTruckerGroup().getTruckerGroupCode())) {
				throw new RecordNotAddedException("Trucker Group Code is not valid");
			} else {
				TruckerGroup truckerGroup = truckerGroupRepository.findByTruckerGroupCode(pool.getTruckerGroup().getTruckerGroupCode());
				pool.setTruckerGroup(truckerGroup);
			}
		}

	}
    
	@Override
	public List<Pool> getPools(Long poolId, String poolName) {
		log.info("getPools : Method Starts");
		Specification<Pool> specs = specificationGenerator.poolSpecification(poolId, poolName);
		 
		if(poolId!=null && !repository.existsByPoolId(poolId)) {
			throw new InvalidDataException("Pool Id is invalid.");
		}
		List<Pool> pools = repository.findAll(specs);
		if (pools.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for this query.");
		}
		log.info("getPools : Method Ends");
		return pools;
	}

	@Override
	public Pool updatePoolTerminal(Pool pool, Map<String, String> headers) {
		log.info("updatePoolTerminal : Method Starts");
		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		String userId = headers.get(CommonConstants.USER_ID);
		
		System.out.println("terminal :" + pool.getTerminals());
		
		//Check Extension Schema is not null
		if(extensionSchema != null) {
			extensionSchema = extensionSchema.toUpperCase();
		} else {
			throw new NullPointerException(CommonConstants.EXTENSION_SCHEMA_ERROR_MESSAGE);
		}
		
		
		if(!repository.existsByPoolId(pool.getPoolId())) {
			throw new NoRecordsFoundException("Record Not Found!");
		}
		
		
		pool.getTerminals().forEach(terminal -> {
			
			if(!poolTermRepo.existsByPoolIdAndTerminalId(pool.getPoolId(), terminal.getTerminalId())) {
				PoolTerminal poolTerm = new PoolTerminal();
				poolTerm.setPoolId(pool.getPoolId());
				poolTerm.setTerminalId(terminal.getTerminalId());
				//Unversioned AuditFields needs to be inserted//
				poolTerm.setUpdateUserId(userId);
				poolTerm.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
				poolTermRepo.saveAndFlush(poolTerm);
			}
		});
		
		List<Long> existingPoolTerminal = poolTermRepo.findByPoolId(pool.getPoolId()).stream().map(terminal -> terminal.getTerminalId()).collect(Collectors.toList());
		
		List<Long> updatedPoolTerminal = pool.getTerminals().stream().map(terminal -> terminal.getTerminalId()).collect(Collectors.toList());
		
		existingPoolTerminal.removeAll(updatedPoolTerminal);
		existingPoolTerminal.forEach(exisingTerminal -> {
			poolTermRepo.deleteByPoolIdAndTerminalId(pool.getPoolId(), exisingTerminal);
		});
		log.info("updatePoolTerminal : Method Ends");
			return repository.findById(pool.getPoolId()).get();
			
		}

	@Override
	public Pool addPool(Pool pool, Map<String, String> headers) {
		log.info("addPool : Method Starts");
		UserId.headerUserID(headers);
		String userId = headers.get(CommonConstants.USER_ID);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);

		if(repository.existsByPoolName(pool.getPoolName())){
			if(repository.existsByDescription(pool.getDescription())){
				throw new RecordNotAddedException("Pool Name & Pool Description should be unique from other records!");
			} else{
				throw new RecordNotAddedException("Pool Name should be unique from other records!");
			}
		} else if(repository.existsByDescription(pool.getDescription())){
			throw new RecordNotAddedException("Pool Description should be unique from other records!");
		}

		poolValidations(pool);
		Long generatedPoolId = repository.SGKLong();
		pool.setPoolId(generatedPoolId);
		pool.setCreateUserId(userId.toUpperCase());
		pool.setUpdateUserId(userId.toUpperCase());
		pool.setUpdateExtensionSchema(extensionSchema);
		pool.setUversion("!");
		Pool poolObj = repository.save(pool);
		log.info("addPool : Method Ends");
		return poolObj;
	}

	@Override
	public Pool updatePool(Pool pool, Map<String, String> headers) {
		log.info("updatePool : Method Starts");
		UserId.headerUserID(headers);
		if(repository.existsByPoolId(pool.getPoolId())){
			Pool existingPool = repository.findById(pool.getPoolId()).get();

			if(!(existingPool.getPoolName().equals(pool.getPoolName()))){
				if(repository.existsByPoolName(pool.getPoolName())) {
					if(!(existingPool.getDescription().equals(pool.getDescription()))) {
						if (repository.existsByDescription(pool.getDescription())) {
							throw new RecordNotAddedException("Pool Name & Pool Description should be unique from other records!");
						} else {
							throw new RecordNotAddedException("Pool Name should be unique from other records!");
						}
					} else {
						throw new RecordNotAddedException("Pool Name should be unique from other records!");
					}
				}
			}
			if(!(existingPool.getDescription().equals(pool.getDescription()))) {
				if (repository.existsByDescription(pool.getDescription())) {
					throw new RecordNotAddedException("Pool Description should be unique from other records!");
				}
			}
			poolValidations(pool);
			existingPool.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			if(headers.get(CommonConstants.EXTENSION_SCHEMA) != null){
				existingPool.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			}
			existingPool.setPoolName(pool.getPoolName());
			existingPool.setDescription(pool.getDescription());
			existingPool.setPoolReservationType(pool.getPoolReservationType());
			existingPool.setAgreementRequired(pool.getAgreementRequired());
			existingPool.setCheckTrucker(pool.getCheckTrucker());
			existingPool.setTruckerGroup(pool.getTruckerGroup());
			if(StringUtils.isNotEmpty(existingPool.getUversion())) {
				existingPool.setUversion(
						Character.toString((char) ((((int)existingPool.getUversion().charAt(0) - 32) % 94) + 33)));
			}
			repository.save(existingPool);
			log.info("updatePool : Method Ends");
			return existingPool;
		} else{
			throw new NoRecordsFoundException("No record found for this 'poolId': "+pool.getPoolId());
		}
	}

	@Override
	public Pool deletePool(Pool pool) {
		log.info("deletePool : Method Starts");
		if(poolTermRepo.existsByPoolId(pool.getPoolId())){
			throw new RecordNotDeletedException("Pool with Pool Id: "+pool.getPoolId()+" has some active terminals");
		}

		if(repository.existsByPoolId(pool.getPoolId())){
			Pool poolObj = repository.findById(pool.getPoolId()).get();
			repository.deleteById(pool.getPoolId());
			List<PoolEquipmentController> poolEqControllerList = poolEqControllerRepo.findByPoolId(pool.getPoolId());
			if(poolEqControllerList != null){
				for(PoolEquipmentController eqCont : poolEqControllerList){
					poolEqControllerRepo.deleteById(eqCont.getPoolControllerId());
				}
			}

			List<PoolEquipmentRange> poolEqRangeList = poolEqRangeRepo.findByPoolId(pool.getPoolId());
			if(poolEqRangeList != null){
				for(PoolEquipmentRange eqRange : poolEqRangeList){
					poolEqRangeRepo.deleteById(eqRange.getPoolRangeId());
				}
			}
			log.info("deletePool : Method Ends");
			return poolObj;
		} else {
			throw new NoRecordsFoundException("No record Found to delete Under this Pool Id: " + pool.getPoolId());
		}
	}
}
	
