package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DVIRCodes;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DVIRCodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class DVIRCodesServiceImpl implements DVIRCodesService{

    @Autowired
    private DVIRCodesRepository dvirCodesRepository;

    @Override
    public List<DVIRCodes> getAllDVIRCodes() {
        List<DVIRCodes> dvirCodesList = dvirCodesRepository.findAll();
        if (dvirCodesList.isEmpty()) {
            throw new NoRecordsFoundException("No DVIRCodes Found!");
        }
        return dvirCodesList;
    }

    @Override
    public List<DVIRCodes> deleteDVIRCodes(DVIRCodes dvirCodes) {
        if(dvirCodesRepository.existsByDvirCd(dvirCodes.getDvirCd())) {
            List<DVIRCodes> dvirDel = dvirCodesRepository.findByDvirCd(dvirCodes.getDvirCd());
            dvirCodesRepository.deleteByDvirCd(dvirCodes.getDvirCd());
            return dvirDel;
        }else {
            throw new RecordNotDeletedException("No records found");
        }
    }

    @Override
    public DVIRCodes addDvirCodes(DVIRCodes dvirCodesObj, Map<String, String> headers) {
        UserId.headerUserID(headers);
        if (!dvirCodesRepository.existsByDvirCd(dvirCodesObj.getDvirCd())) {
            dvirCodesObj.setUversion("!");
            dvirCodesObj.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
            dvirCodesObj.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
            dvirCodesObj.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
            dvirCodesObj.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
            dvirCodesObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            dvirCodesObj.setDvirCd(dvirCodesObj.getDvirCd());
            dvirCodesObj.setDvirDesc(dvirCodesObj.getDvirDesc());
            dvirCodesObj.setDvirHHDesc(dvirCodesObj.getDvirHHDesc());
            dvirCodesObj.setDisplayCd(dvirCodesObj.getDisplayCd());

            dvirCodesRepository.save(dvirCodesObj);
            return dvirCodesObj;
        }
        else {
            throw new RecordAlreadyExistsException("Record Already Exists");
        }
    }

	@Override
	public DVIRCodes updateDvirCodes(@Valid DVIRCodes dvirCodesObj, Map<String, String> headers) {
		UserId.headerUserID(headers);
        if (dvirCodesRepository.existsByDvirCdAndUversion(dvirCodesObj.getDvirCd(),dvirCodesObj.getUversion())) {
        	DVIRCodes updateCode = dvirCodesRepository.findById(dvirCodesObj.getDvirCd()).get();
        	updateCode.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        	updateCode.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
            updateCode.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
            updateCode.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            updateCode.setDvirDesc(dvirCodesObj.getDvirDesc());
            updateCode.setDvirHHDesc(dvirCodesObj.getDvirHHDesc());
            updateCode.setDisplayCd(dvirCodesObj.getDisplayCd());

            dvirCodesRepository.save(updateCode);
            return updateCode;
        }
        else {
            throw new NoRecordsFoundException("No Record Found");
        }
	}

}
