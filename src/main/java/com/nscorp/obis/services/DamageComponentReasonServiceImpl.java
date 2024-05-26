package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.DMax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageComponentReason;
import com.nscorp.obis.dto.DamageComponentReasonDTO;
import com.nscorp.obis.dto.mapper.DamageComponentReasonMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.AARWhyMadeCodesRepository;
import com.nscorp.obis.repository.DamageComponentReasonRepository;
import com.nscorp.obis.repository.DamageComponentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DamageComponentReasonServiceImpl implements DamageComponentReasonService {

	@Autowired
	private DamageComponentReasonRepository damageComponentReasonRepository;

	@Autowired
	private DamageComponentRepository damageComponentRepo;

	@Autowired
	private AARWhyMadeCodesRepository aarwhyMadeCodeRepo;

	@Autowired
	private SpecificationGenerator specificationGenerator;

	@Autowired
	private DamageComponentReasonMapper mapper;

	@Override
	public List<DamageComponentReasonDTO> getDamageComponentReasons(Integer jobCode, Integer aarWhyMadeCode,
			String orderCode, String sizeRequired) {
		log.info("getDamageComponentReasons : Method Starts");
		Specification<DamageComponentReason> specification = specificationGenerator
				.damageComponentReasonSpecification(jobCode, aarWhyMadeCode, orderCode, sizeRequired);
		List<DamageComponentReason> damageComponentReasons = damageComponentReasonRepository.findAll(specification);
		if (damageComponentReasons.isEmpty()) {
			throw new NoRecordsFoundException("No records found for given combination");
		}
		List<DamageComponentReasonDTO> damageComponentReasonDTOs = damageComponentReasons.stream()
				.map(mapper::damageComponentReasonToDamageComponentReasonDTO).collect(Collectors.toList());
		log.info("getDamageComponentReasons : Method Ends");
		return damageComponentReasonDTOs;
	}

	@Override
	public DamageComponentReasonDTO deleteDamageComponentReason(DamageComponentReasonDTO componentReasonDTO) {
		log.info("deleteDamageComponentReason : Method Starts");
		Integer jobCode = componentReasonDTO.getJobCode();
		Integer aarWhyMadeCode = componentReasonDTO.getAarWhyMadeCode();
		if (jobCode != null && String.valueOf(jobCode).length() > 5) {
			throw new InvalidDataException("jobCode shouldn't have more than 5 digits");
		}
		if (aarWhyMadeCode != null && String.valueOf(aarWhyMadeCode).length() > 5) {
			throw new InvalidDataException("aarWhyMadeCode shouldn't have more than 5 digits");
		}
		DamageComponentReason componentReason = damageComponentReasonRepository.findByJobCodeAndAarWhyMadeCode(
				componentReasonDTO.getJobCode(), componentReasonDTO.getAarWhyMadeCode());
		if (componentReason == null) {
			throw new NoRecordsFoundException("Record Not Found!");
		}
		componentReason.setUversion(componentReasonDTO.getUversion());
		damageComponentReasonRepository.delete(componentReason);
		componentReasonDTO = mapper.damageComponentReasonToDamageComponentReasonDTO(componentReason);
		log.info("deleteDamageComponentReason : Method Ends");
		return componentReasonDTO;
	}

	@Override
	public DamageComponentReasonDTO createDamageComponentReason(DamageComponentReasonDTO damageComponentReasonDTO,
			Map<String, String> headers) {
		log.info("createDamageComponentReason : Method Starts");
		UserId.headerUserID(headers);
		if (!damageComponentRepo.existsByJobCode(damageComponentReasonDTO.getJobCode())) {
			throw new NoRecordsFoundException("No records found for given jobCode");
		}
		if (!aarwhyMadeCodeRepo.existsByAarWhyMadeCd(damageComponentReasonDTO.getAarWhyMadeCode())) {
			throw new NoRecordsFoundException("No records found for given aarWhyMadeCode");
		}
		if (damageComponentReasonRepository.existsByJobCodeAndAarWhyMadeCode(damageComponentReasonDTO.getJobCode(),
				damageComponentReasonDTO.getAarWhyMadeCode())) {
			throw new RecordAlreadyExistsException("Record already exist with given jobCode and aarWhyMadeCode");
		}
		if (damageComponentReasonDTO.getOrderCode()!=null && damageComponentReasonRepository.existsByJobCodeAndOrderCode(damageComponentReasonDTO.getJobCode(),
				damageComponentReasonDTO.getOrderCode())) {
			throw new RecordAlreadyExistsException("Record already exist with given jobCode and orderCode");
		}
		DamageComponentReason entity = mapper.damageComponentReasonDTOToDamageComponentReason(damageComponentReasonDTO);
		entity.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		entity.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		if (headers.get(CommonConstants.EXTENSION_SCHEMA) != null)
			entity.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
		entity.setUversion("!");
		entity = damageComponentReasonRepository.save(entity);
		damageComponentReasonDTO = mapper.damageComponentReasonToDamageComponentReasonDTO(entity);
		log.info("createDamageComponentReason : Method Ends");
		return damageComponentReasonDTO;
	}

	@Override
	public DamageComponentReasonDTO updateDamageComponentReason(DamageComponentReasonDTO damageComponentReasonDTO,
			Map<String, String> headers) {
		log.info("updateDamageComponentReason : Method Starts");
		UserId.headerUserID(headers);
		DamageComponentReason entity = damageComponentReasonRepository.findByJobCodeAndAarWhyMadeCode(
				damageComponentReasonDTO.getJobCode(), damageComponentReasonDTO.getAarWhyMadeCode());
		if (entity == null) {
			throw new NoRecordsFoundException("No records found for given jobCode and aarWhyMadeCode");
		}
		entity.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		if (headers.get(CommonConstants.EXTENSION_SCHEMA) != null)
			entity.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
		entity.setUversion(damageComponentReasonDTO.getUversion());

		if (!StringUtils.equals(damageComponentReasonDTO.getOrderCode(), entity.getOrderCode())) {
			if (damageComponentReasonDTO.getOrderCode()!=null && damageComponentReasonRepository.existsByJobCodeAndOrderCode(damageComponentReasonDTO.getJobCode(),
					damageComponentReasonDTO.getOrderCode())) {
				throw new RecordAlreadyExistsException("Record already exist with given jobCode and orderCode");
			}
			entity.setOrderCode(damageComponentReasonDTO.getOrderCode());
		}
		entity.setMaxQuantity(damageComponentReasonDTO.getMaxQuantity());
		entity.setBadOrder(damageComponentReasonDTO.getBadOrder());
		entity.setSizeRequired(damageComponentReasonDTO.getSizeRequired());
		entity.setDisplayCode(damageComponentReasonDTO.getDisplayCode());
		entity = damageComponentReasonRepository.save(entity);
		damageComponentReasonDTO = mapper.damageComponentReasonToDamageComponentReasonDTO(entity);
		log.info("updateDamageComponentReason : Method Ends");
		return damageComponentReasonDTO;
	}

}
