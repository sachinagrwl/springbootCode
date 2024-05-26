package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.nscorp.obis.exception.RecordAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.HazRestriction;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.HazRestrictionRepository;

@Service
@Transactional
public class HazRestrictionServiceImpl implements HazRestrictionService{

    @Autowired
    private HazRestrictionRepository hazRestrictionRepository;
    @Override
    public HazRestriction insertHazRestriction(@Valid HazRestriction hazRestrictionObj, Map<String, String> headers) {
        hazRestrictionValidations(hazRestrictionObj, headers);
        if(!hazRestrictionRepository.existsByUnCd(hazRestrictionObj.getUnCd())) {
            UserId.headerUserID(headers);
            hazRestrictionObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
            hazRestrictionObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
            hazRestrictionObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            hazRestrictionObj.setUversion("!");
            HazRestriction hazRestriction = hazRestrictionRepository.save(hazRestrictionObj);
            return hazRestriction;
        } else {
            throw new NoRecordsFoundException("Record Already exists for UnCode: " + hazRestrictionObj.getUnCd());
        }
    }

    @Override
    public HazRestriction updateHazRestriction(HazRestriction hazRestrictionObj, Map<String, String> headers) {
            UserId.headerUserID(headers);
            hazRestrictionValidations(hazRestrictionObj, headers);
            if(hazRestrictionRepository.existsByUnCd(hazRestrictionObj.getUnCd())){
                HazRestriction existingHazRestriction = hazRestrictionRepository.findByUnCd(hazRestrictionObj.getUnCd());
                existingHazRestriction.setUversion(
                        Character.toString((char) ((((int)hazRestrictionObj.getUversion().charAt(0) - 32) % 94) + 33)));
                existingHazRestriction.setRestoreClass(hazRestrictionObj.getRestoreClass());
                existingHazRestriction.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
                existingHazRestriction.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
                hazRestrictionRepository.save(existingHazRestriction);
                return existingHazRestriction;
           }
          else
             throw new NoRecordsFoundException("No record Found");

    }



    private void hazRestrictionValidations(HazRestriction hazRestrictionObj, Map<String, String> headers) {

        if(hazRestrictionObj.getUnCd() == null){
            throw new InvalidDataException("Please check for UNCode value, cannot be null or empty");
        }

        if(hazRestrictionObj.getRestoreClass() == null){
            throw new InvalidDataException("Please check for restriction class value, cannot be null or empty");
        }
    }
    @Override
    public List<HazRestriction> getHazRestriction(String unCd) {
		List<HazRestriction> hazRestrictionList = hazRestrictionRepository.findAll(unCd);
		if (hazRestrictionList.isEmpty()) {
			throw new NoRecordsFoundException("No records found");
		}
		return hazRestrictionList;
	}

    @Override
    public HazRestriction deleteHazRestriction(HazRestriction hazRestriction) {
        if(hazRestrictionRepository.existsByUnCd(hazRestriction.getUnCd())) {
           List<HazRestriction> hazRestrictionDel = hazRestrictionRepository.findAll(hazRestriction.getUnCd());
            hazRestrictionRepository.deleteAll(hazRestrictionDel);
            return hazRestriction;
        }else {
            throw new RecordNotDeletedException("No records found");
        }
    }

}
