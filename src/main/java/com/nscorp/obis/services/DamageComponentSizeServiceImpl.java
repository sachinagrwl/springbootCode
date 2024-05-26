package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageComponentReason;
import com.nscorp.obis.domain.DamageComponentSize;
import com.nscorp.obis.dto.DamageComponentReasonDTO;
import com.nscorp.obis.dto.DamageComponentSizeDTO;
import com.nscorp.obis.dto.mapper.DamageComponentSizeMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.DamageCompSizeRepository;
import com.nscorp.obis.repository.DamageComponentReasonRepository;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Service
@Slf4j
@Transactional
public class DamageComponentSizeServiceImpl implements DamageComponentSizeService {

    @Autowired
    DamageCompSizeRepository damageCompSizeRepository;

    @Autowired
    DamageComponentReasonRepository damageComponentReasonRepository;

    @Autowired
    DamageComponentSizeMapper damageComponentSizeMapper;

    @Autowired
	SpecificationGenerator specificationGenerator;

    @Override
	public List<DamageComponentSizeDTO> fetchDamageComponentSize(Integer jobCode, Integer aarWhyMadeCode, Long componentSizeCode) {

		Specification<DamageComponentSize> specification = specificationGenerator.damageComponentSizeSpecification(jobCode, aarWhyMadeCode, componentSizeCode);
		List<DamageComponentSize> damageComponentSize = damageCompSizeRepository.findAll(specification);
		if (damageComponentSize.isEmpty()) {
			throw new NoRecordsFoundException("No records found for given combination");
		}
		List<DamageComponentSizeDTO> damageComponentSizeDTOs = damageComponentSize.stream()
				.map(damageComponentSizeMapper::damageComponentSizeToDamageComponentSizeDTO).collect(Collectors.toList());
		return damageComponentSizeDTOs;
	}

    @Override
	public DamageComponentSizeDTO deleteDamageComponentSize(@Valid DamageComponentSizeDTO componentSizeDTO) {

		DamageComponentSize componentSize = damageCompSizeRepository.findByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(
				componentSizeDTO.getJobCode(), componentSizeDTO.getAarWhyMadeCode(), componentSizeDTO.getComponentSizeCode());
		if (componentSize == null) {
			throw new NoRecordsFoundException("No records found for given combination");
		}
		componentSize.setUversion(componentSizeDTO.getUversion());
		damageCompSizeRepository.delete(componentSize);
		componentSizeDTO = damageComponentSizeMapper.damageComponentSizeToDamageComponentSizeDTO(componentSize);
		return componentSizeDTO;
	}

    @Override
    public DamageComponentSizeDTO insertDamageComponentSize(@Valid DamageComponentSizeDTO damageComponentSizeDTO, Map<String, String> headers) {
        log.info("DamageComponentSizeServiceImpl: insertDamageComponentSize : Method Starts");
        UserId.headerUserID(headers);
        if (damageCompSizeRepository.existsByJobCodeAndAarWhyMadeCodeAndComponentSizeCode
                (damageComponentSizeDTO.getJobCode(), damageComponentSizeDTO.getAarWhyMadeCode(), damageComponentSizeDTO.getComponentSizeCode())) {
            throw new RecordAlreadyExistsException("Record already exists with given jobCode,aarWhyMadeCode and componentSizeCode.");
        }
        if (damageCompSizeRepository.existsByJobCodeAndAarWhyMadeCodeAndOrderCode
                (damageComponentSizeDTO.getJobCode(), damageComponentSizeDTO.getAarWhyMadeCode(), damageComponentSizeDTO.getOrderCode())) {
            throw new RecordAlreadyExistsException("Record already exists with given jobCode,aarWhyMadeCode and orderCode");
        }
        damageComponentSizeDTO.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        damageComponentSizeDTO.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        damageComponentSizeDTO.setUversion("!");
        damageComponentSizeDTO.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        if (damageComponentSizeDTO.getUpdateExtensionSchema() == null) {
            damageComponentSizeDTO.setUpdateExtensionSchema("IMS01246");
        }

        if (!damageComponentReasonRepository.existsByJobCodeAndAarWhyMadeCodeAndSizeRequired(damageComponentSizeDTO.getJobCode(), damageComponentSizeDTO.getAarWhyMadeCode(), "Y")) {
            throw new NoRecordsFoundException("No damage component reason found for given JobCode and AarWhyMadeCode");
        }

        DamageComponentSize damageComponentSize = damageComponentSizeMapper.damageComponentSizeDTOToDamageComponentSize(damageComponentSizeDTO);
        damageComponentSize.setComponentSizeCode(damageCompSizeRepository.SGK());
        DamageComponentSize addedDamageComponentSize = damageCompSizeRepository.save(damageComponentSize);
        log.info("DamageComponentSizeServiceImpl: insertDamageComponentSize : Method Ends");
        return damageComponentSizeMapper.damageComponentSizeToDamageComponentSizeDTO(addedDamageComponentSize);
    }

    @Override
    public DamageComponentSizeDTO updateDamageCompSize(@Valid DamageComponentSizeDTO damageComponentSizeDTO, Map<String, String> headers) {
        log.info("DamageComponentSizeServiceImpl: updateDamageCompSize : Method Starts");
        UserId.headerUserID(headers);
        DamageComponentSize damageComponentSize;
        damageComponentSize = damageCompSizeRepository.findByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(damageComponentSizeDTO.getJobCode(), damageComponentSizeDTO.getAarWhyMadeCode(), damageComponentSizeDTO.getComponentSizeCode());
        if (damageComponentSize == null) {
            throw new NoRecordsFoundException("No record found for given JobCode ,AarWhyMadeCode and ComponentSizeCode");
        }
        if (!StringUtils.equals(damageComponentSize.getOrderCode(), damageComponentSizeDTO.getOrderCode())) {
        	 if (damageCompSizeRepository.existsByJobCodeAndAarWhyMadeCodeAndOrderCode
                     (damageComponentSizeDTO.getJobCode(), damageComponentSizeDTO.getAarWhyMadeCode(), damageComponentSizeDTO.getOrderCode())) {
                 throw new RecordAlreadyExistsException("Record already exists with given jobCode,aarWhyMadeCode and orderCode");
             }
        } 
        damageComponentSize.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        damageComponentSize.setUversion(damageComponentSizeDTO.getUversion());
        damageComponentSize.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        if (damageComponentSize.getUpdateExtensionSchema() == null) {
            damageComponentSize.setUpdateExtensionSchema("IMS01246");
        }
        damageComponentSize.setDamageSize(damageComponentSizeDTO.getDamageSize());
        damageComponentSize.setSizeDisplayTp(damageComponentSizeDTO.getSizeDisplayTp());
        damageComponentSize.setSizeDisplaySign(damageComponentSizeDTO.getSizeDisplaySign());
        damageComponentSize.setOrderCode(damageComponentSizeDTO.getOrderCode());

        damageComponentSize = damageCompSizeRepository.save(damageComponentSize);
        log.info("DamageComponentSizeServiceImpl: updateDamageCompSize : Method Ends");
        return damageComponentSizeMapper.damageComponentSizeToDamageComponentSizeDTO(damageComponentSize);
    }
}
