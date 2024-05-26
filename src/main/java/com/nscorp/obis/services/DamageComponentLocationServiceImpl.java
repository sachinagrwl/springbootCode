package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.dto.DamageCompLocDTO;
import com.nscorp.obis.dto.mapper.DamageCompLocMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageAreaRepository;
import com.nscorp.obis.repository.DamageCompLocRepository;
import com.nscorp.obis.repository.DamageComponentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class DamageComponentLocationServiceImpl implements DamageComponentLocationService {

    @Autowired
    DamageCompLocRepository damageCompLocRepository;

    @Autowired
    DamageComponentRepository damageComponentRepository;

    @Autowired
    DamageAreaRepository damageAreaRepository;

    @Autowired
    DamageCompLocMapper damageCompLocMapper;

    @Override
    public List<DamageCompLoc> getDamageComponentLocation(Integer jobCode, String areaCode) {
        List<DamageCompLoc> damageComponentList = new ArrayList<>();
        if (jobCode != null && areaCode != null) {
            damageComponentList = damageCompLocRepository.findByDamageComponent_JobCodeAndDamageArea_AreaCdIgnoreCase(jobCode, areaCode);
        } else if (jobCode != null) {
            damageComponentList = damageCompLocRepository.findByDamageComponent_JobCode(jobCode);
        } else if (areaCode != null) {
            damageComponentList = damageCompLocRepository.findByDamageArea_AreaCdIgnoreCase(areaCode);
        } else {
            damageComponentList = damageCompLocRepository.findAll();
        }

		if (damageComponentList.isEmpty()) {
			throw new NoRecordsFoundException("No Records Found!");
		}
		return damageComponentList;
	}

    @Override
    public void deleteDamageComponent(DamageCompLocDTO damageComponent) {

            DamageCompLoc existingRecord = damageCompLocRepository.findByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(
                    damageComponent.getCompLocCode(), damageComponent.getJobCode(), damageComponent.getAreaCd());
            if(existingRecord==null) {
            	throw new RecordNotDeletedException("Record Not Found!");
            }
           // existingRecord.setUversion(damageComponent.getUversion());
            damageCompLocRepository.delete(existingRecord);

    }

    @Override
    public DamageCompLocDTO addDamageCompLocation(DamageCompLocDTO damageCompLocDTO, Map<String, String> headers) {
        log.info("DamageComponentLocationServiceImpl: addDamageCompLocation : Method Starts");
        UserId.headerUserID(headers);
        if (damageCompLocRepository.existsByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(damageCompLocDTO.getCompLocCode(), damageCompLocDTO.getJobCode(), damageCompLocDTO.getAreaCd())) {
            throw new RecordAlreadyExistsException("2006 – Illegal duplicate key");
        }
        damageCompLocDTO.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        damageCompLocDTO.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        damageCompLocDTO.setUversion("!");
        damageCompLocDTO.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        if (damageCompLocDTO.getUpdateExtensionSchema() == null) {
            damageCompLocDTO.setUpdateExtensionSchema("IMS01244");
        }

        DamageComponent damageComponent = damageComponentRepository.findByJobCode(damageCompLocDTO.getJobCode());
        if (damageComponent==null){
            throw new NoRecordsFoundException("No Damage Component record found for given JobCode");
        }
        DamageArea damageArea = damageAreaRepository.findByAreaCd(damageCompLocDTO.getAreaCd());
        if (damageArea==null){
            throw new NoRecordsFoundException("No Damage Area record found for given AreaCd");
        }
        DamageCompLoc damageCompLoc = damageCompLocMapper.damageCompLocDTOToDamageCompLoc(damageCompLocDTO);
        damageCompLoc.setDamageComponent(damageComponent);
        damageCompLoc.setDamageArea(damageArea);
        damageCompLoc = damageCompLocRepository.save(damageCompLoc);
        log.info("DamageComponentLocationServiceImpl: addDamageCompLocation : Method Ends");
        return damageCompLocMapper.damageCompLocToDamageCompLocDTO(damageCompLoc);
    }

    @Override
    public DamageCompLocDTO updateDamageCompLocation(DamageCompLocDTO damageCompLocDTO, Map<String, String> headers) {
        log.info("DamageComponentLocationServiceImpl: updateDamageCompLocation : Method Starts");
        UserId.headerUserID(headers);
        DamageCompLoc damageCompLoc;
        damageCompLoc = damageCompLocRepository.findByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(damageCompLocDTO.getCompLocCode(), damageCompLocDTO.getJobCode(), damageCompLocDTO.getAreaCd());
        if (damageCompLoc == null) {
            throw new NoRecordsFoundException("No record found for given compLocCode,JobCode and AreaCd");
        }
        damageCompLoc.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        damageCompLoc.setUversion(damageCompLocDTO.getUversion());
        damageCompLoc.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        damageCompLoc.setLocDisplayCode(damageCompLocDTO.getLocDisplayCode());
        damageCompLoc.setTireIoCode(damageCompLocDTO.getTireIoCode());
        damageCompLoc = damageCompLocRepository.save(damageCompLoc);
        log.info("DamageComponentLocationServiceImpl: updateDamageCompLocation : Method Ends");
        return damageCompLocMapper.damageCompLocToDamageCompLocDTO(damageCompLoc);
    }
}
