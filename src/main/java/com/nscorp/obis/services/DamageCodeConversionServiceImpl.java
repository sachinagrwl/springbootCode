package com.nscorp.obis.services;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageCodeConversion;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.AARDamageRepository;
import com.nscorp.obis.repository.AARWhyMadeCodesRepository;
import com.nscorp.obis.repository.DamageCategoryRepository;
import com.nscorp.obis.repository.DamageCodeConversionRepository;
import com.nscorp.obis.repository.DamageReasonRepository;

@Service
@Transactional
public class DamageCodeConversionServiceImpl implements DamageCodeConversionService{

    @Autowired
    private DamageCodeConversionRepository damageCodeConversionRepository;
    @Autowired
    DamageCategoryRepository damageCategoryRepo;
    @Autowired
    DamageReasonRepository damageReasonRepo;
    @Autowired
    AARDamageRepository aarDamageRepo;
    @Autowired 
    AARWhyMadeCodesRepository aarWhyCodesRepo;

    @Override
    public List<DamageCodeConversion> getAllDamageCodeConversions() {
        List<DamageCodeConversion> damageConversionList = damageCodeConversionRepository.findAllByOrderByCatCd();

        if (damageConversionList.isEmpty()) {
            throw new NoRecordsFoundException("No DamageConversionList Found!");
        }
        return damageConversionList;
    }

    @Override
    public DamageCodeConversion getDamageCodeConversionByCatCode(Integer catCd, String reasonCd) {
        DamageCodeConversion damageConversionCatCd = damageCodeConversionRepository.findByCatCdAndReasonCd(catCd, reasonCd);

        if (damageConversionCatCd == null) {
            throw new NoRecordsFoundException("No DamageCodeConversion Found!");
        }
        return damageConversionCatCd;
    }

    @Override
    public void deleteCodeConversion(DamageCodeConversion damageCodeConversion) {

        if (damageCodeConversionRepository.existsByCatCdAndReasonCdAndUversion(damageCodeConversion.getCatCd(), damageCodeConversion.getReasonCd(), damageCodeConversion.getUversion())) {

            DamageCodeConversion existingRecord = damageCodeConversionRepository.findByCatCdAndReasonCdAndUversion(damageCodeConversion.getCatCd(), damageCodeConversion.getReasonCd(), damageCodeConversion.getUversion());
            damageCodeConversionRepository.delete(existingRecord);

        } else {
            String rep = damageCodeConversion.getCatCd() + " Record Not Found!";
            throw new RecordNotDeletedException(rep);
        }

    }

	@Override
	public DamageCodeConversion addDamageCodeConversion(DamageCodeConversion damageCodeConversion,Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (!damageCategoryRepo.existsByCatCd(damageCodeConversion.getCatCd()))
			throw new InvalidDataException("Category Code does not exist");
		
		if (!damageReasonRepo.existsByCatCdAndReasonCd(damageCodeConversion.getCatCd(),damageCodeConversion.getReasonCd()))
			throw new InvalidDataException("Invalid Reason Code");

		if (damageCodeConversion.getAarJobCode() != null) {
			if (!aarDamageRepo.existsByJobCode(damageCodeConversion.getAarJobCode()))
			throw new InvalidDataException("Invalid Job Code");
		}

		if (damageCodeConversion.getAarWhyMadeCode() != null) {
			if (!aarWhyCodesRepo.existsByAarWhyMadeCd(damageCodeConversion.getAarWhyMadeCode()))
			throw new InvalidDataException("Invalid Aar Why Made Code");
		}

		if (damageCodeConversionRepository.existsByCatCdAndReasonCd(damageCodeConversion.getCatCd(), damageCodeConversion.getReasonCd()))
			throw new RecordAlreadyExistsException("Duplicate Data");

		damageCodeConversion.setCreateUserId(headers.get(CommonConstants.USER_ID));
		damageCodeConversion.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
		damageCodeConversion.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		damageCodeConversion.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		damageCodeConversion.setUversion("!");
		
		DamageCodeConversion code = damageCodeConversionRepository.save(damageCodeConversion);
		return code;
		
	}

	@Override
	public DamageCodeConversion updateDamageCodeConversion(DamageCodeConversion damageCodeReq,Map<String, String> headers) {
		UserId.headerUserID(headers);

		if (damageCodeReq.getAarJobCode() != null) {
			if (!aarDamageRepo.existsByJobCode(damageCodeReq.getAarJobCode()))
			throw new InvalidDataException("Invalid Job Code");
		}

		if (damageCodeReq.getAarWhyMadeCode() != null) {
			if (!aarWhyCodesRepo.existsByAarWhyMadeCd(damageCodeReq.getAarWhyMadeCode()))
			throw new InvalidDataException("Invalid Aar Why Made Code");
		}
		
		if (damageCodeConversionRepository.existsByAarJobCdAndAarWhyMadeCode(damageCodeReq.getAarJobCode(), damageCodeReq.getAarWhyMadeCode()))
			throw new RecordAlreadyExistsException("Duplicate Data");
		
		damageCodeReq.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		damageCodeReq.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
		damageCodeReq.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		damageCodeReq.setUversion(damageCodeReq.getUversion());
		
		DamageCodeConversion result = damageCodeConversionRepository.save(damageCodeReq);
		return result;
	}
}
