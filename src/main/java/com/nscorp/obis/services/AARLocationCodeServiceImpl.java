package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.exception.RecordNotDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.AARLocationCode;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.AARLocationCodeRepository;

@Service
@Transactional
public class AARLocationCodeServiceImpl implements AARLocationCodeService {

	@Autowired
	AARLocationCodeRepository aarLocationCodeRepo;

	@Override
	public AARLocationCode updateAARLocationCode(@Valid AARLocationCode aarLocationCodeObj,
			Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (aarLocationCodeRepo.existsByLocCd(aarLocationCodeObj.getLocCd())) {
			AARLocationCode aarLocationCode = aarLocationCodeRepo.findByLocCd(aarLocationCodeObj.getLocCd());
			aarLocationCode.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			aarLocationCode.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
			aarLocationCode.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			aarLocationCode.setUversion("!");
			aarLocationCode.setLocDesc(aarLocationCodeObj.getLocDesc());
			aarLocationCodeRepo.save(aarLocationCode);
			return aarLocationCode;
		} else
			throw new NoRecordsFoundException(
					"Record with AARLocation Code " + aarLocationCodeObj.getLocCd() + " Not Found!");
	}

	@Override
	public AARLocationCode addAARLocationCode(@Valid AARLocationCode aarLocationCodeObj, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (!aarLocationCodeRepo.existsByLocCd(aarLocationCodeObj.getLocCd())) {
			aarLocationCodeObj.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			aarLocationCodeObj.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			aarLocationCodeObj.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
			aarLocationCodeObj.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
			aarLocationCodeObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			aarLocationCodeObj.setUversion("!");
			aarLocationCodeObj.setLocDesc(aarLocationCodeObj.getLocDesc());
			aarLocationCodeRepo.save(aarLocationCodeObj);
			return aarLocationCodeObj;
		} else
			throw new NoRecordsFoundException("Duplicate Aar Location Code!");
	}

	@Override
	public AARLocationCode getAARLocationCodesByLocCode(String aarLocationCode) {
		AARLocationCode aarfindByLocationCode = aarLocationCodeRepo.findByLocCd(aarLocationCode);

		if (aarfindByLocationCode == null) {
			throw new NoRecordsFoundException("No AAR Code List Found!");
		}
		return aarfindByLocationCode;
	}

	@Override
	public List<AARLocationCode> getAllAARLocationCodes() {
		List<AARLocationCode> aarLocationCodes = aarLocationCodeRepo.findAll();

		if (aarLocationCodes.isEmpty()) {
			throw new NoRecordsFoundException("No, AAR Code List Found!");
		}
		return aarLocationCodes;
	}

	@Override
	public void deleteAARLocationCode(AARLocationCode aarLocationCode) {
		if (aarLocationCodeRepo.existsByLocCdAndUversion(aarLocationCode.getLocCd(), aarLocationCode.getUversion())) {

			AARLocationCode existingRecord = aarLocationCodeRepo.findByLocCd(aarLocationCode.getLocCd());
			aarLocationCodeRepo.delete(existingRecord);

		} else {
			String rep = aarLocationCode.getLocCd() + " Record Not Found!";
			throw new RecordNotDeletedException(rep);
		}

	}

}
