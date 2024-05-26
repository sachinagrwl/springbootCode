package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentAAR600Cont;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.EquipmentAAR600ContRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EquipmentAAR600ContServiceImpl implements EquipmentAAR600ContService {

    @Autowired
    EquipmentAAR600ContRepository equipmentAAR600ContRepository;


    @Override
    public List<EquipmentAAR600Cont> getAllCont() {

        List<EquipmentAAR600Cont> equipmentAAR600ContList = equipmentAAR600ContRepository.findAllByOrderByEquipInit();

        if(equipmentAAR600ContList.isEmpty()){
            throw new NoRecordsFoundException("No Record Found under this search!");
        }

        return equipmentAAR600ContList;
    }
    
    @Override
	public EquipmentAAR600Cont addEqCont(EquipmentAAR600Cont eqCont, Map<String, String> headers) {
		
		UserId.headerUserID(headers);
		
		if(equipmentAAR600ContRepository.existsByEquipInitAndBeginningEqNrAndEndEqNbr(eqCont.getEquipInit(), eqCont.getBeginningEqNr(), eqCont.getEndEqNbr())) {
			throw new RecordAlreadyExistsException("Record already exists under Initial: " + eqCont.getEquipInit() + ", BeginningNumber: " + eqCont.getBeginningEqNr() + ", EndingNumber: " + eqCont.getEndEqNbr());
		}
		
		if(eqCont.getBeginningEqNr().compareTo(eqCont.getEndEqNbr()) > 0) {
			throw new RecordNotAddedException("Ending Equipment Number should be greater than the Beginning Equipment Number");
		}
		
		eqCont.setCreateUserId(headers.get(CommonConstants.USER_ID));
		eqCont.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		eqCont.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		eqCont.setUversion("!");
		
		EquipmentAAR600Cont eqAARCont = equipmentAAR600ContRepository.save(eqCont);
		if (eqAARCont == null) {
			throw new RecordNotAddedException("Record Not added");
		}
		return eqAARCont;
    }

	@Override
	public void deleteEqCont(EquipmentAAR600Cont eqCont) {
		if(equipmentAAR600ContRepository.existsByEquipInitAndBeginningEqNrAndEndEqNbr(eqCont.getEquipInit(), eqCont.getBeginningEqNr(), eqCont.getEndEqNbr())) {
			equipmentAAR600ContRepository.deleteByEquipInitAndBeginningEqNrAndEndEqNbr(eqCont.getEquipInit(), eqCont.getBeginningEqNr(), eqCont.getEndEqNbr());
		}
		else {
			String rep = eqCont.getEquipInit()  + " and " + eqCont.getBeginningEqNr() + " and " + eqCont.getEndEqNbr() + " Record Not Found!";
			throw new RecordNotDeletedException(rep);
		}
	}


}
