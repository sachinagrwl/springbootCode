package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.RulesCircular;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.RulesCircularRepository;

@Service
@Transactional
public class RulesCircularServiceImpl implements RulesCircularService {

    @Autowired
    RulesCircularRepository rulesCircularRepository;

    @Override
    public List<RulesCircular> getAllRulesCircular() {

        List<RulesCircular> rulesCircularList = rulesCircularRepository.findAllByOrderByEquipmentTypeAscEquipmentLengthAsc();

        if(rulesCircularList.isEmpty()){
            throw new NoRecordsFoundException("No Record Found under this search!");
        }

        return rulesCircularList;
    }

    @Override
    public RulesCircular addRulesCircular(RulesCircular rulesCircular, Map<String, String> headers) {
    	
        UserId.headerUserID(headers);
        if(rulesCircularRepository.existsByEquipmentTypeAndEquipmentLength(rulesCircular.getEquipmentType(),rulesCircular.getEquipmentLength())) {
            throw new RecordAlreadyExistsException("Rules Circular already exists under Equipment Type:" + rulesCircular.getEquipmentType() + " and Equipemnt Length:"+rulesCircular.getEquipmentLength());
        }
        
        if(!((rulesCircular.getEquipmentType().equalsIgnoreCase("C")) || (rulesCircular.getEquipmentType().equalsIgnoreCase("T")) || (rulesCircular.getEquipmentType().equalsIgnoreCase("Z"))
                || (rulesCircular.getEquipmentType().equalsIgnoreCase("F")) || (rulesCircular.getEquipmentType().equalsIgnoreCase("G")))) {
			throw new RecordNotAddedException("EquipmentType value must be 'C', 'T', 'Z', 'F' and 'G'");
		}
        
        if(rulesCircular.getEquipmentType().equals("C") && !((rulesCircular.getEquipmentLength().equals(0)) || (rulesCircular.getEquipmentLength().equals(20)) || (rulesCircular.getEquipmentLength().equals(40))
                || (rulesCircular.getEquipmentLength().equals(45)) || (rulesCircular.getEquipmentLength().equals(48)) || (rulesCircular.getEquipmentLength().equals(53)))) {
        	throw new RecordNotAddedException(CommonConstants.EQUIPMENT_LENGTH + rulesCircular.getEquipmentLength() + CommonConstants.EQUIPMENT_TYPE + rulesCircular.getEquipmentType());
        }
        
        if(rulesCircular.getEquipmentType().equals("T") && !((rulesCircular.getEquipmentLength().equals(0)) || (rulesCircular.getEquipmentLength().equals(28)) || (rulesCircular.getEquipmentLength().equals(40))
                || (rulesCircular.getEquipmentLength().equals(45)) || (rulesCircular.getEquipmentLength().equals(48)) || (rulesCircular.getEquipmentLength().equals(53)))) {
        	throw new RecordNotAddedException(CommonConstants.EQUIPMENT_LENGTH + rulesCircular.getEquipmentLength() + CommonConstants.EQUIPMENT_TYPE + rulesCircular.getEquipmentType());
        }
        
        if(rulesCircular.getEquipmentType().equals("Z") && !((rulesCircular.getEquipmentLength().equals(0)) || (rulesCircular.getEquipmentLength().equals(20)) || (rulesCircular.getEquipmentLength().equals(40))
                || (rulesCircular.getEquipmentLength().equals(45)) || (rulesCircular.getEquipmentLength().equals(48)) || (rulesCircular.getEquipmentLength().equals(53)))) {
        	throw new RecordNotAddedException(CommonConstants.EQUIPMENT_LENGTH + rulesCircular.getEquipmentLength() + CommonConstants.EQUIPMENT_TYPE + rulesCircular.getEquipmentType());
        }

        rulesCircular.setCreateUserId(headers.get(CommonConstants.USER_ID));
        rulesCircular.setUpdateUserId(headers.get(CommonConstants.USER_ID));

        rulesCircular.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        rulesCircular.setUversion("!");
        RulesCircular rulesCircularObj=rulesCircularRepository.save(rulesCircular);
        if(rulesCircularObj == null) {
            throw new RecordNotAddedException("Record Not added to Database");
        }
        return rulesCircularObj;
    }

    @Override
    public RulesCircular updateRulesCircular(RulesCircular rulesCircular, Map<String, String> headers) {
        UserId.headerUserID(headers);
        if(rulesCircularRepository.existsByEquipmentTypeAndEquipmentLength(rulesCircular.getEquipmentType(),rulesCircular.getEquipmentLength())) {
            RulesCircular existingRulesCircular =rulesCircularRepository.findByEquipmentTypeAndEquipmentLength(rulesCircular.getEquipmentType(),rulesCircular.getEquipmentLength());
            existingRulesCircular.setUpdateUserId(headers.get(CommonConstants.USER_ID));
            existingRulesCircular.setMaximumShipWeight(rulesCircular.getMaximumShipWeight());
            rulesCircularRepository.save(existingRulesCircular);
            return existingRulesCircular;
        }
        else
            throw new NoRecordsFoundException("No record Found Under this Equipment Type:"+rulesCircular.getEquipmentType()+" and Equipment Length:"+rulesCircular.getEquipmentLength());
    }
    
    @Override
    public RulesCircular deleteRulesCircular(RulesCircular rulesCircular) {
    	if(rulesCircularRepository.existsByEquipmentTypeAndEquipmentLength(rulesCircular.getEquipmentType(),rulesCircular.getEquipmentLength())) {
    		RulesCircular existingRulesCircular = rulesCircularRepository.findByEquipmentTypeAndEquipmentLength(rulesCircular.getEquipmentType(),rulesCircular.getEquipmentLength());
    		rulesCircularRepository.deleteByEquipmentTypeAndEquipmentLength(rulesCircular.getEquipmentType(),rulesCircular.getEquipmentLength());
    		return existingRulesCircular;
    	}
    	else
            throw new NoRecordsFoundException("No record Found to delete Under this Equipment Type: "+rulesCircular.getEquipmentType()
												+" and Equipment Length: "+rulesCircular.getEquipmentLength());
	}
	
}
