package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.FhwaStopRange;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.FhwaStopRangeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Transactional
@Service
public class FhwaStopRangeServiceImpl implements FhwaStopRangeService{
    @Autowired
    FhwaStopRangeRepository fhwaStopRangeRepository;


    public void fhwaStopRangeValidations(FhwaStopRange fhwaStopRange) {

        BigDecimal eqNrLow = fhwaStopRange.getEquipmentNumberLow();
        BigDecimal eqNrHigh = fhwaStopRange.getEquipmentNumberHigh();

        if(eqNrLow.compareTo(eqNrHigh) > 0) {
            throw new RecordNotAddedException("Equipment Low Number: " + eqNrLow + " should be less than or equals to Equipment High Number: "
                    + eqNrHigh);
        }
        if(eqNrLow.intValue() == 1 && eqNrHigh.intValue() == 999999) {
            if(fhwaStopRangeRepository.existsByEquipmentTypeAndEquipmentInitAndEquipmentNumberLowAndEquipmentNumberHigh(fhwaStopRange.getEquipmentType(),
                    fhwaStopRange.getEquipmentInit(), eqNrLow, eqNrHigh)) {
                throw new RecordNotAddedException("Full Range already exists for Equipment Type and Init.");
            }
        }
        else if(fhwaStopRangeRepository.existsByEquipmentTypeAndEquipmentInit(fhwaStopRange.getEquipmentType(), fhwaStopRange.getEquipmentInit())) {
            List<FhwaStopRange> list = fhwaStopRangeRepository.findByEquipmentTypeAndEquipmentInit(fhwaStopRange.getEquipmentType(), fhwaStopRange.getEquipmentInit());
            list.removeIf(list1 -> list1.getRangeId().equals(fhwaStopRange.getRangeId()));
            list.forEach(overlap -> {
                BigDecimal overlapNrLow = overlap.getEquipmentNumberLow();
                BigDecimal overlapNrHigh = overlap.getEquipmentNumberHigh();
                if((overlapNrLow.compareTo(eqNrLow) >= 0 && overlapNrHigh.compareTo(eqNrHigh) <= 0) || (overlapNrLow.compareTo(eqNrLow) <= 0 && overlapNrHigh.compareTo(eqNrHigh) >= 0)
                        || (overlapNrLow.compareTo(eqNrLow) <= 0 && overlapNrHigh.compareTo(eqNrLow) >= 0)|| (overlapNrLow.compareTo(eqNrHigh) <= 0 && overlapNrHigh.compareTo(eqNrHigh) >= 0)) {
                    throw new RecordNotAddedException("The Range entered conflicts with other ranges for these association");
                }
            });
        }
    }
    @Override
    public List<FhwaStopRange> getAllFhwaStopRanges(String equipmentInit, String equipmentType, BigDecimal equipmentNumberLow, BigDecimal equipmentNumberHigh) {
        List<FhwaStopRange> fhwaStopRangeList = fhwaStopRangeRepository.findAll(equipmentInit, equipmentType, equipmentNumberLow, equipmentNumberHigh);
        if(fhwaStopRangeList.isEmpty()) {
            throw new NoRecordsFoundException("No Record Found under this search!");
        }
        return fhwaStopRangeList;
    }

    @Override
    public FhwaStopRange addFhwaStopRange(FhwaStopRange fhwaStopRange, Map<String, String> headers) {
        UserId.headerUserID(headers);
        String userId = headers.get(CommonConstants.USER_ID);
        String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
        Long generatedRangeId = fhwaStopRangeRepository.SGKLong();
        fhwaStopRange.setRangeId(generatedRangeId);
        fhwaStopRange.setCreateUserId(userId.toUpperCase());
        fhwaStopRange.setUpdateUserId(userId.toUpperCase());
        fhwaStopRange.setUpdateExtensionSchema(extensionSchema);
        fhwaStopRange.setUversion("!");
        fhwaStopRangeValidations(fhwaStopRange);
        fhwaStopRangeRepository.save(fhwaStopRange);
        return fhwaStopRangeRepository.findByRangeId(fhwaStopRange.getRangeId());
    }

    @Override
    public FhwaStopRange updateFhwaStopRange(FhwaStopRange fhwaStopRange, Map<String, String> headers) {
        UserId.headerUserID(headers);
        if(fhwaStopRangeRepository.existsById(fhwaStopRange.getRangeId())) {
            fhwaStopRangeValidations(fhwaStopRange);
            FhwaStopRange existingFhwaStopRange =fhwaStopRangeRepository.findByRangeId(fhwaStopRange.getRangeId());
            existingFhwaStopRange.setUpdateUserId(headers.get(CommonConstants.USER_ID));
            existingFhwaStopRange.setEquipmentInit(fhwaStopRange.getEquipmentInit());
            existingFhwaStopRange.setEquipmentType(fhwaStopRange.getEquipmentType());
            existingFhwaStopRange.setEquipmentNumberLow(fhwaStopRange.getEquipmentNumberLow());
            existingFhwaStopRange.setEquipmentNumberHigh(fhwaStopRange.getEquipmentNumberHigh());
            existingFhwaStopRange.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            if(StringUtils.isNotEmpty(existingFhwaStopRange.getUversion())) {
                existingFhwaStopRange.setUversion(
                        Character.toString((char) ((((int)existingFhwaStopRange.getUversion().charAt(0) - 32) % 94) + 33)));
            }
            fhwaStopRangeRepository.save(existingFhwaStopRange);
            return existingFhwaStopRange;
        }
        else
            throw new NoRecordsFoundException("No record Found Under this Range Id:"+ fhwaStopRange.getRangeId());
    }

    @Override
    public void deleteFhwaStopRange(FhwaStopRange fhwaStopRange) {
        if (fhwaStopRangeRepository.existsById(fhwaStopRange.getRangeId())) {
            fhwaStopRangeRepository.deleteById(fhwaStopRange.getRangeId());
        } else
            throw new NoRecordsFoundException("No record Found to delete Under this Range Id: " + fhwaStopRange.getRangeId());
    }
}
