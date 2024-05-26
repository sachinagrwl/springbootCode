package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentRestrict;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.EquipmentRestrictRepository;

@Service
@Transactional
public class EquipmentRestrictServiceImpl implements EquipmentRestrictService {

    @Autowired
    EquipmentRestrictRepository equipRestrictRepo;

    private void equipmentRestrictValidations(EquipmentRestrict equipRestrict){

        if(!equipRestrict.getEquipmentInit().matches("[a-zA-Z]+")){
            throw new RecordNotAddedException("'equipmentInit' value should have only alphabets");
        }
        if(!equipRestrict.getEquipmentType().equals("C") && !equipRestrict.getEquipmentType().equals("F")){
            throw new RecordNotAddedException("'equipmentType' value should be only 'C' and 'F'");
        }
        System.out.println("Sample");
        if(equipRestrict.getEquipmentNumberLow() != null && equipRestrict.getEquipmentNumberHigh() == null){
            throw new RecordNotAddedException("'equipmentNumberHigh' value should be provided");
        } else if(equipRestrict.getEquipmentNumberLow() == null && equipRestrict.getEquipmentNumberHigh() != null){
            throw new RecordNotAddedException("'equipmentNumberLow' value should be provided");
        } else if(equipRestrict.getEquipmentNumberLow() == null && equipRestrict.getEquipmentNumberHigh() == null){
            equipRestrict.setEquipmentNumberLow(null);
            equipRestrict.setEquipmentNumberHigh(null);
        }
        System.out.println("Sample 1");
        if((equipRestrict.getEquipmentNumberLow() != null && equipRestrict.getEquipmentNumberHigh() != null) && equipRestrict.getEquipmentNumberLow().compareTo(equipRestrict.getEquipmentNumberHigh()) >= 0){
            throw new RecordNotAddedException("'Equipment Low Number' should be less than 'Equipment High Number'");
        }
        equipRestrict.setEquipmentRestrictionType("D");
    }

    @Override
    public List<EquipmentRestrict> getAllEquipRestrictions() {

        List<EquipmentRestrict> equipRestrictList = equipRestrictRepo.findAll();
        System.out.println(equipRestrictList);
        if(equipRestrictList.isEmpty()) {
            throw new NoRecordsFoundException("No Records found for Equipment Restrictions");
        }
        return equipRestrictList;
    }

    @Override
    public EquipmentRestrict addEquipRestrictions(EquipmentRestrict equipRestrict, Map<String, String> headers) {

        UserId.headerUserID(headers);
        String userId = headers.get(CommonConstants.USER_ID);
        String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);

        equipmentRestrictValidations(equipRestrict);
        Long generatedRestrictionId = equipRestrictRepo.SGKLong();

        equipRestrict.setRestrictionId(generatedRestrictionId);
        equipRestrict.setCreateUserId(userId.toUpperCase());
        equipRestrict.setUpdateUserId(userId.toUpperCase());
        equipRestrict.setUpdateExtensionSchema(extensionSchema);
        equipRestrict.setUversion("!");
        System.out.println("Sample 3");
        EquipmentRestrict eqRestrict = equipRestrictRepo.save(equipRestrict);
        System.out.println("Sample 4");
        return eqRestrict;
    }

    @Override
    public EquipmentRestrict updateEquipRestriction(EquipmentRestrict equipRestrict, Map<String, String> headers) {

        UserId.headerUserID(headers);
        if(equipRestrictRepo.existsById(equipRestrict.getRestrictionId())){

            equipmentRestrictValidations(equipRestrict);
            EquipmentRestrict existingEqRestriction =equipRestrictRepo.findByRestrictionId(equipRestrict.getRestrictionId());
            existingEqRestriction.setUpdateUserId(headers.get(CommonConstants.USER_ID));
            if(headers.get(CommonConstants.EXTENSION_SCHEMA) != null){
                existingEqRestriction.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            }
            existingEqRestriction.setEquipmentInit(equipRestrict.getEquipmentInit());
            existingEqRestriction.setEquipmentType(equipRestrict.getEquipmentType());
            existingEqRestriction.setEquipmentNumberLow(equipRestrict.getEquipmentNumberLow());
            existingEqRestriction.setEquipmentNumberHigh(equipRestrict.getEquipmentNumberHigh());
            existingEqRestriction.setEquipmentRestrictionType(equipRestrict.getEquipmentRestrictionType());
            existingEqRestriction.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
            if(StringUtils.isNotEmpty(existingEqRestriction.getUversion())) {
                existingEqRestriction.setUversion(
                        Character.toString((char) ((((int)existingEqRestriction.getUversion().charAt(0) - 32) % 94) + 33)));
            }
            equipRestrictRepo.save(existingEqRestriction);
            return existingEqRestriction;
        } else{
            throw new NoRecordsFoundException("No record found for this 'restrictionId': "+equipRestrict.getRestrictionId());
        }

    }

    @Override
    public EquipmentRestrict deleteEquipRestriction(EquipmentRestrict equipRestrict) {

        if(equipRestrictRepo.existsByRestrictionId(equipRestrict.getRestrictionId())){
            EquipmentRestrict eqRestrict = equipRestrictRepo.findByRestrictionId(equipRestrict.getRestrictionId());
            equipRestrictRepo.deleteByRestrictionId(equipRestrict.getRestrictionId());
            return eqRestrict;
        } else {
            throw new NoRecordsFoundException("No record Found to delete Under this Restriction Id: " + equipRestrict.getRestrictionId());
        }
    }
}
