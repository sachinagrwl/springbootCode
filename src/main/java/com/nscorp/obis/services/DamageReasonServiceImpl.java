package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageReason;
import com.nscorp.obis.dto.DamageReasonDTO;
import com.nscorp.obis.dto.mapper.DamageReasonMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.DamageCategoryRepository;
import com.nscorp.obis.repository.DamageReasonRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DamageReasonServiceImpl implements DamageReasonService {

	@Autowired
	DamageReasonRepository damageReasonRepo;

	@Autowired
	DamageCategoryRepository damageCategoryRepo;

	@Autowired
	DamageReasonMapper mapper;

	@Override
	public DamageReasonDTO addDamageReason(@Valid DamageReasonDTO damageReasonDTO, Map<String, String> headers) {
		log.info("addDamageReason : Method Starts");
		UserId.headerUserID(headers);
		if (!damageCategoryRepo.existsByCatCd(damageReasonDTO.getCatCd())) {
			throw new NoRecordsFoundException("No records found for given category");
		}
		if (damageReasonRepo.existsByCatCdAndReasonCd(damageReasonDTO.getCatCd(), damageReasonDTO.getReasonCd())) {
			throw new RecordAlreadyExistsException("Record already exist with given category and reason code");
		}
		damageReasonDTO.setUversion("!");
		damageReasonDTO.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		damageReasonDTO.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		damageReasonDTO.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA) != null
				? headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase()
				: headers.get(CommonConstants.EXTENSION_SCHEMA));
		DamageReason damageReason = mapper.damageReasonDTOToDamageReason(damageReasonDTO);
		damageReason = damageReasonRepo.save(damageReason);
		damageReasonDTO = mapper.damageReasonToDamageReasonDTO(damageReason);
		log.info("addDamageReason : Method Ends");
		return damageReasonDTO;
	}

	@Override
	public DamageReasonDTO updateDamageReason(@Valid DamageReasonDTO damageReasonDTO, Map<String, String> headers) {
		log.info("updateDamageReason : Method Starts");
		UserId.headerUserID(headers);
		DamageReason damageReason = damageReasonRepo.findByCatCdAndReasonCd(damageReasonDTO.getCatCd(),
				damageReasonDTO.getReasonCd());
		if (damageReason == null) {
			throw new NoRecordsFoundException("No records found with given category and reason code");
		}
		damageReason.setBordInd(damageReasonDTO.getBordInd());
		damageReason.setReasonDscr(damageReasonDTO.getReasonDscr());
		damageReason.setPrtOrder(damageReasonDTO.getPrtOrder());
		damageReason.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		damageReason.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA) != null
				? headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase()
				: headers.get(CommonConstants.EXTENSION_SCHEMA));
		damageReason.setUversion(damageReasonDTO.getUversion());
		damageReason = damageReasonRepo.save(damageReason);
		damageReasonDTO = mapper.damageReasonToDamageReasonDTO(damageReason);
		log.info("updateDamageReason : Method Ends");
		return damageReasonDTO;
	}

	//	@Override
//	public List<DamageReasonDTO> deleteDamageReasons(List<DamageReasonDTO> damageReasonDTOS) {
//		DamageReason damageReason;
//		if(damageReasonDTOS != null){
//			for(DamageReasonDTO damageReasonDTOObj : damageReasonDTOS){
//				if(damageReasonRepo.existsByCatCd(damageReasonDTOObj.getCatCd())){
//					damageReason = mapper.damageReasonDTOToDamageReason(damageReasonDTOObj);
//					damageReasonRepo.deleteByCatCd(damageReason.getCatCd());
//				}
//			}
//		}else{
//			throw new NoRecordsFoundException("No record Found to delete");
//		}
//		return null;
//	}
	@Override
	public List<DamageReason> getDamageReason(Integer catCd) {
		List<DamageReason> damageReasonList;
		{
			damageReasonList = damageReasonRepo.findByCatCd(catCd);
			if(damageReasonList.isEmpty()) {
				throw new NoRecordsFoundException("No Records found!");
			}

		}
		return damageReasonList;
	}

	@Override
	public DamageReason deleteDamageReasons(DamageReason damageReason) {
		if(damageReason.getCatCd() ==null){
			throw new InvalidDataException("Damage Reason CatCd Can't Be Null");
		}
		if(damageReason.getReasonCd() ==null){
			throw new InvalidDataException("Damage Reason Code Can't Be Null");
		}
		if(!damageReasonRepo.existsByCatCdAndReasonCd(damageReason.getCatCd(),damageReason.getReasonCd())){
			throw new NoRecordsFoundException(
					"No Damage Reason found with given category and reason code");

		}
		damageReasonRepo.delete(damageReason);
		return damageReason;
	}
}

/*
	@Transactional
    public BeneficialOwner deleteBeneficialCustomers(BeneficialOwner beneficialOwner) {
        log.info("deleteBeneficialCustomers: Method Starts");

        if (beneficialOwner.getBnfCustomerId() == null) {
            throw new InvalidDataException("Beneficial Customer Id Can't Be Null");
        }

        if (!repository.existsByBnfCustomerId(beneficialOwner.getBnfCustomerId())) {
            throw new NoRecordsFoundException(
                    "No Details Found with this given Beneficial Customer Id : " + beneficialOwner.getBnfCustomerId());
        }
		repository.deleteByBnfCustomerId(beneficialOwner.getBnfCustomerId());
		log.info("deleteBeneficialCustomers: Method Ends");
		return beneficialOwner;

}

 */
