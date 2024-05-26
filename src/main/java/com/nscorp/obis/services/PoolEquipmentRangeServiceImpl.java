package com.nscorp.obis.services;


import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.*;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.PoolEquipmentConflictRangeRepository;
import com.nscorp.obis.repository.PoolEquipmentRangeRepository;
import com.nscorp.obis.repository.PoolRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class PoolEquipmentRangeServiceImpl implements PoolEquipmentRangeService {

    @Autowired
    PoolEquipmentRangeRepository poolEquipmentRangeRepository;

    @Autowired
    PoolEquipmentConflictRangeRepository conflictRepository;

    @Autowired
    PoolRepository poolRepo;

    public void poolEquipmentRangeValidations(PoolEquipmentRange poolEquipmentRange, Map<String, String> headers){
        UserId.headerUserID(headers);
        String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
        if(extensionSchema != null) {
            extensionSchema = extensionSchema.toUpperCase();
        } else {
            throw new NullPointerException("Extension Schema should not be null, empty or blank");
        }
        if (!poolRepo.existsByPoolId(poolEquipmentRange.getPoolId())) {
            throw new NoRecordsFoundException(
                    "PoolId: " + poolEquipmentRange.getPoolId() + " is invalid as it doesn't exists in Pool");
        }
        BigDecimal eqNrLow = poolEquipmentRange.getEquipmentLowNumber();
        BigDecimal eqNrHigh = poolEquipmentRange.getEquipmentHighNumber();

        if(eqNrLow.compareTo(eqNrHigh) > 0) {
            throw new RecordNotAddedException("Equipment Low Number: " + eqNrLow + " should be less than or equals to Equipment High Number: "
                    + eqNrHigh);
        }

        if(poolEquipmentRangeRepository.existsByPoolIdAndEquipmentInitAndEquipmentType(poolEquipmentRange.getPoolId(), poolEquipmentRange.getEquipmentInit(), poolEquipmentRange.getEquipmentType())) {
            List<PoolEquipmentRange> poolEquipmentRangeList = poolEquipmentRangeRepository.findByPoolIdAndEquipmentInitAndEquipmentType(
                    poolEquipmentRange.getPoolId(), poolEquipmentRange.getEquipmentInit(), poolEquipmentRange.getEquipmentType());
            poolEquipmentRangeList.forEach(rangeOverLap -> {
                if((!rangeOverLap.getPoolRangeId().equals(poolEquipmentRange.getPoolRangeId())) && ((rangeOverLap.getEquipmentLowNumber().compareTo(eqNrLow) >= 0 && rangeOverLap.getEquipmentHighNumber().compareTo(eqNrHigh) <= 0) ||
                        (rangeOverLap.getEquipmentLowNumber().compareTo(eqNrLow) <= 0 && rangeOverLap.getEquipmentHighNumber().compareTo(eqNrHigh) >= 0) ||
                        (rangeOverLap.getEquipmentLowNumber().compareTo(eqNrLow) <= 0 && rangeOverLap.getEquipmentHighNumber().compareTo(eqNrLow) >= 0) ||
                        (rangeOverLap.getEquipmentLowNumber().compareTo(eqNrHigh) <= 0 && rangeOverLap.getEquipmentHighNumber().compareTo(eqNrHigh) >= 0))) {
                    throw new RecordNotAddedException("Equipment Init and Equipment Number Range are overlapping with existing records");
                }
            });
        }

        if(conflictRepository.existsByPoolRangeIdAndRangeTypeAndRangeInitAndRangeLowNumberAndRangeHighNumber(
                poolEquipmentRange.getPoolId(), poolEquipmentRange.getEquipmentType(), poolEquipmentRange.getEquipmentInit(),
                poolEquipmentRange.getEquipmentLowNumber(), poolEquipmentRange.getEquipmentHighNumber())) {
            PoolEquipmentConflictRange conflictRange = conflictRepository.findByPoolRangeIdAndRangeTypeAndRangeInitAndRangeLowNumberAndRangeHighNumber(
                    poolEquipmentRange.getPoolId(), poolEquipmentRange.getEquipmentType(), poolEquipmentRange.getEquipmentInit(),
                    poolEquipmentRange.getEquipmentLowNumber(), poolEquipmentRange.getEquipmentHighNumber());
            throw new RecordNotAddedException("The Range Conflict Identified on Pool Name:"+conflictRange.getPool().getPoolName()+", Equipment Type:"+conflictRange.getRangeType()+", Initial:"
                    +conflictRange.getRangeInit()+", Low Nr:"+conflictRange.getRangeLowNumber()+", High Nr:"+conflictRange.getRangeHighNumber()
                    +" and Terminal:"+conflictRange.getTerminal().getTerminalName());
        }
    }

    @Override
    public List<PoolEquipmentRange> getAllPoolEquipmentRanges() {
        List<PoolEquipmentRange> poolEquipmentRangeList = poolEquipmentRangeRepository.findAll();
        if(poolEquipmentRangeList.isEmpty()) {
            throw new NoRecordsFoundException("No Record Found!");
        }
        return poolEquipmentRangeList;
    }

    @Override
    public PoolEquipmentRange addPoolEquipmentRange(PoolEquipmentRange poolEquipmentRange, Map<String, String> headers) {

        poolEquipmentRangeValidations(poolEquipmentRange, headers);
        Long generatedPoolRangeId = poolEquipmentRangeRepository.SGKLong();
        poolEquipmentRange.setPoolRangeId(generatedPoolRangeId);
        if(poolEquipmentRangeRepository.existsByPoolRangeId(poolEquipmentRange.getPoolRangeId())) {
            throw new RecordNotAddedException("Pool Range Id:"+poolEquipmentRange.getPoolRangeId()+" already exists.");
        }

        poolEquipmentRange.setCreateUserId(headers.get(CommonConstants.USER_ID));
        poolEquipmentRange.setUpdateUserId(headers.get(CommonConstants.USER_ID));
        poolEquipmentRange.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
        poolEquipmentRange.setUversion("!");
        return poolEquipmentRangeRepository.save(poolEquipmentRange);
    }

    @Override
    public PoolEquipmentRange updatePoolEquipmentRange(PoolEquipmentRange poolEquipmentRange, Map<String, String> headers) {
        if(poolEquipmentRangeRepository.existsByPoolRangeIdAndUversion(poolEquipmentRange.getPoolRangeId(), poolEquipmentRange.getUversion())) {
            PoolEquipmentRange existingPoolEquipmentRange =poolEquipmentRangeRepository.findByPoolRangeIdAndUversion(poolEquipmentRange.getPoolRangeId(), poolEquipmentRange.getUversion());
            poolEquipmentRange.setPoolId(existingPoolEquipmentRange.getPoolId());
            poolEquipmentRangeValidations(poolEquipmentRange, headers);
            existingPoolEquipmentRange.setUpdateUserId(headers.get(CommonConstants.USER_ID));
            existingPoolEquipmentRange.setEquipmentInit(poolEquipmentRange.getEquipmentInit());
            existingPoolEquipmentRange.setEquipmentType(poolEquipmentRange.getEquipmentType());
            existingPoolEquipmentRange.setEquipmentLowNumber(poolEquipmentRange.getEquipmentLowNumber());
            existingPoolEquipmentRange.setEquipmentHighNumber(poolEquipmentRange.getEquipmentHighNumber());
            existingPoolEquipmentRange.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            if(StringUtils.isNotEmpty(existingPoolEquipmentRange.getUversion())) {
                existingPoolEquipmentRange.setUversion(
                        Character.toString((char) ((((int)existingPoolEquipmentRange.getUversion().charAt(0) - 32) % 94) + 33)));
            }
            poolEquipmentRangeRepository.save(existingPoolEquipmentRange);
            return existingPoolEquipmentRange;
        }
        else
            throw new NoRecordsFoundException("No record Found Under this Pool Range Id:"+ poolEquipmentRange.getPoolRangeId()
            +" and Uversion:" + poolEquipmentRange.getUversion());
    }

    @Override
    public PoolEquipmentRange deletePoolEquipmentRange(PoolEquipmentRange poolEquipmentRange) {
        if(poolEquipmentRangeRepository.existsByPoolRangeIdAndUversion(poolEquipmentRange.getPoolRangeId(), poolEquipmentRange.getUversion())) {
            PoolEquipmentRange existingPoolEquipmentRange = poolEquipmentRangeRepository.findByPoolRangeIdAndUversion(
                    poolEquipmentRange.getPoolRangeId(), poolEquipmentRange.getUversion());
            poolEquipmentRangeRepository.deleteById(poolEquipmentRange.getPoolRangeId());
            return existingPoolEquipmentRange;
        }
        else {
            String rep = "No record Found Under this Pool Range Id:" + poolEquipmentRange.getPoolRangeId()
                    + " and Uversion:" + poolEquipmentRange.getUversion();
            throw new RecordNotDeletedException(rep);
        }
    }
}
