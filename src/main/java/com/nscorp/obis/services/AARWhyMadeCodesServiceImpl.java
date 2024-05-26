package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.RecordAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.AARWhyMadeCodes;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.AARWhyMadeCodesRepository;

@Service
public class AARWhyMadeCodesServiceImpl implements AARWhyMadeCodesService{

	
	@Autowired
	private AARWhyMadeCodesRepository aarWhyMadeCodesRepo;
	
	@Override
	public List<AARWhyMadeCodes> getAARWhyMadeCodes() {
		List<AARWhyMadeCodes> aarWhyMadeCodes = aarWhyMadeCodesRepo.findAll();

		if (aarWhyMadeCodes == null) {
			throw new NoRecordsFoundException("No AAR Why Made Codes List Found!");
		}
		return aarWhyMadeCodes;
	}

	@Override
	public AARWhyMadeCodes updateAARWhyMadeCodes(AARWhyMadeCodes aarWhyMadeCodeObj,Map<String, String> headers) {
		// TODO Auto-generated method stub
		UserId.headerUserID(headers);
		if(aarWhyMadeCodesRepo.existsById(aarWhyMadeCodeObj.getAarWhyMadeCd())) {
			AARWhyMadeCodes aarWhyMadeCodes = aarWhyMadeCodesRepo.findByAarWhyMadeCd(aarWhyMadeCodeObj.getAarWhyMadeCd());
			aarWhyMadeCodes.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			aarWhyMadeCodes.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
			aarWhyMadeCodes.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			aarWhyMadeCodes.setUversion("!");
			aarWhyMadeCodes.setAarDesc(aarWhyMadeCodeObj.getAarDesc());
			aarWhyMadeCodesRepo.save(aarWhyMadeCodes);
			return aarWhyMadeCodes;
			
		}
		else
			throw new NoRecordsFoundException("Record with AAR Why Code " + aarWhyMadeCodeObj.getAarWhyMadeCd()+ " Not Found!");
	}
	
	@Override
	public void deleteAARWhyMadeCodes(AARWhyMadeCodes aarWhyMadeCodesObj) {
		if(aarWhyMadeCodesRepo.existsById(aarWhyMadeCodesObj.getAarWhyMadeCd())) {
			aarWhyMadeCodesRepo.deleteById(aarWhyMadeCodesObj.getAarWhyMadeCd());
		}else {
			throw new RecordNotDeletedException("Record Not Found!");
		}
	}

	@Override
	public AARWhyMadeCodes addAARWhyMadeCodes(AARWhyMadeCodes aarWhyMadeCodeObj, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (!aarWhyMadeCodesRepo.existsByAarWhyMadeCd(aarWhyMadeCodeObj.getAarWhyMadeCd())) {
			aarWhyMadeCodeObj.setUversion("!");
			aarWhyMadeCodeObj.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			aarWhyMadeCodeObj.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			aarWhyMadeCodeObj.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
			aarWhyMadeCodeObj.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
			aarWhyMadeCodeObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			aarWhyMadeCodeObj.setAarDesc(aarWhyMadeCodeObj.getAarDesc());
			aarWhyMadeCodeObj.setAarWhyMadeCd(aarWhyMadeCodeObj.getAarWhyMadeCd());
			
			aarWhyMadeCodesRepo.save(aarWhyMadeCodeObj);
			return aarWhyMadeCodeObj;
		}
		else {
			throw new RecordAlreadyExistsException("Record Already Exists");
		}
	}
}
