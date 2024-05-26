package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentRestrict;
import com.nscorp.obis.domain.TruckerGroup;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.PoolRepository;
import com.nscorp.obis.repository.TruckerGroupRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TruckerGroupServiceImpl implements TruckerGroupService{

    @Autowired
    TruckerGroupRepository truckerGroupRepo;

    @Autowired
    PoolRepository poolRepo;

    @Override
    public List<TruckerGroup> getAllTruckerGroups() {
        List<TruckerGroup> truckerGroupList = truckerGroupRepo.findAll();
        System.out.println(truckerGroupList);
        if(truckerGroupList.isEmpty()) {
            throw new NoRecordsFoundException("No Records found for Trucker Groups");
        }
        return truckerGroupList;
    }

    @Override
    public TruckerGroup addTruckerGroup(TruckerGroup truckerGroupObj, Map<String, String> headers) {
        UserId.headerUserID(headers);

        if(truckerGroupRepo.existsByTruckerGroupCode(truckerGroupObj.getTruckerGroupCode())){
            throw new RecordAlreadyExistsException("Record with Trucker: "+truckerGroupObj.getTruckerGroupCode()+" already exists!");
        } else {
            String userId = headers.get(CommonConstants.USER_ID);
            String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);

            truckerGroupObj.setCreateUserId(userId.toUpperCase());
            truckerGroupObj.setUpdateUserId(userId.toUpperCase());
            truckerGroupObj.setUpdateExtensionSchema(extensionSchema);
            truckerGroupObj.setUversion("!");
            TruckerGroup truckerGroup = truckerGroupRepo.save(truckerGroupObj);
            return truckerGroup;
        }
    }

    @Override
    public TruckerGroup updateTruckerGroup(TruckerGroup truckerGroupObj, Map<String, String> headers) {
        UserId.headerUserID(headers);
        if(truckerGroupRepo.existsByTruckerGroupCodeAndUversion(truckerGroupObj.getTruckerGroupCode(), truckerGroupObj.getUversion())){

            TruckerGroup existingTruckerGroup =truckerGroupRepo.findByTruckerGroupCode(truckerGroupObj.getTruckerGroupCode());
            truckerGroupObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
            if(headers.get(CommonConstants.EXTENSION_SCHEMA) != null){
                existingTruckerGroup.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            }
            existingTruckerGroup.setTruckerGroupDesc(truckerGroupObj.getTruckerGroupDesc());
            existingTruckerGroup.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
            if(StringUtils.isNotEmpty(existingTruckerGroup.getUversion())) {
                existingTruckerGroup.setUversion(
                        Character.toString((char) ((((int)existingTruckerGroup.getUversion().charAt(0) - 32) % 94) + 33)));
            }
            truckerGroupRepo.save(existingTruckerGroup);
            return existingTruckerGroup;
        } else{
            throw new NoRecordsFoundException("No record found for this 'truckerGroupCode': "+truckerGroupObj.getTruckerGroupCode());
        }
    }

    @Override
    public TruckerGroup deleteTruckerGroup(TruckerGroup truckerGroupObj) {
        if(truckerGroupRepo.existsByTruckerGroupCode(truckerGroupObj.getTruckerGroupCode())){
            if(poolRepo.existsByTruckerGroup_TruckerGroupCode(truckerGroupObj.getTruckerGroupCode())){
                throw new RecordNotAddedException("Record is already in use in Pool");
            }
            TruckerGroup truckerGroup = truckerGroupRepo.findByTruckerGroupCode(truckerGroupObj.getTruckerGroupCode());
            if(truckerGroup.getSetupSchema() != null){
                throw new RecordNotAddedException("Record with Trucker: "+truckerGroupObj.getTruckerGroupCode()+" has Setup Schema value & cannot be deleted");
            }
            truckerGroupRepo.deleteByTruckerGroupCode(truckerGroupObj.getTruckerGroupCode());
            return truckerGroup;
        } else {
            throw new NoRecordsFoundException("No record Found to delete Under this Trucker Group Code: " + truckerGroupObj.getTruckerGroupCode());
        }
    }
}
