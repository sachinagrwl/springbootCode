package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageLocation;
import com.nscorp.obis.dto.DamageComponentDTO;
import com.nscorp.obis.dto.DamageLocationDTO;
import com.nscorp.obis.dto.mapper.DamageLocationMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageCategoryRepository;
import com.nscorp.obis.repository.DamageLocationRepository;

@Transactional
@Service
public class DamageLocationServiceImpl implements DamageLocationService{
    
    @Autowired
    DamageLocationRepository damageLocationRepository;

	@Autowired
	DamageLocationMapper damageLocationMapper;

	@Autowired
	DamageCategoryRepository damageCategoryRepo;

    @Override
	public List<DamageLocation> getAllDamageLocation() {

		List<DamageLocation> damageLocationList = new ArrayList<>();
		damageLocationList = damageLocationRepository.findAll();
		if (damageLocationList.isEmpty()) {
			throw new NoRecordsFoundException("No Records found!");
		}
		return damageLocationList;
	}

    public void deleteDamageLocation(DamageLocation damageLocation) {

		if (damageLocation.getCatCd() == null) {
			throw new InvalidDataException("CatCd should not be null");
		}
		if (damageLocation.getLocCd() == null) {
			throw new InvalidDataException("LocCd should not be null");
		}

		if (damageLocationRepository.existsByCatCdAndLocCd(damageLocation.getCatCd(), damageLocation.getLocCd())) {
			damageLocationRepository.deleteByCatCdAndLocCd(damageLocation.getCatCd(), damageLocation.getLocCd());
		} else {
			String errMsg =" Record Not Found!";
			throw new RecordNotDeletedException(errMsg);
		}
	}

	@Override
	public DamageLocationDTO addDamageLocation(@Valid DamageLocationDTO damageLocationDTO, Map<String, String> headers)
			throws SQLException {
		UserId.headerUserID(headers);

		if (!damageCategoryRepo.existsByCatCd(damageLocationDTO.getCatCd())) {
			throw new NoRecordsFoundException("No records found for given category CatCd");
		}
		if(damageLocationRepository.existsByCatCdAndLocCd(damageLocationDTO.getCatCd(), damageLocationDTO.getLocCd())){
			throw new RecordAlreadyExistsException("Record already exists with given CatCd and LocCd");
		}
		damageLocationDTO.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		damageLocationDTO.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		damageLocationDTO.setUversion("!");
		damageLocationDTO.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if (damageLocationDTO.getUpdateExtensionSchema() == null) {
			damageLocationDTO.setUpdateExtensionSchema("IMS01146");
		}
		String dscr = damageLocationDTO.getLocationDscr();
		damageLocationDTO.setLocationDscr(dscr.toUpperCase());
		DamageLocation damageLocation = damageLocationMapper.damageLocationDTOToDamageLocation(damageLocationDTO);
		damageLocation = damageLocationRepository.save(damageLocation);
		damageLocationDTO = damageLocationMapper.damageLocationToDamageLocationDTO(damageLocation);

		return damageLocationDTO;
	}

	@Override
    public DamageLocationDTO updateDamageLocation(@Valid DamageLocationDTO damageLocationDTO, Map<String, String> headers) {

		UserId.headerUserID(headers);

		DamageLocation damageLocation;
		Optional<DamageLocation> optional = damageLocationRepository.findByCatCdAndLocCd(damageLocationDTO.getCatCd(), damageLocationDTO.getLocCd());
		if (!optional.isPresent())
            throw new NoRecordsFoundException("No Record Found For Given CatCd and LocCd");

        damageLocation = optional.get();
		damageLocation.setLocationDscr((damageLocationDTO.getLocationDscr()).toUpperCase());
		damageLocation.setPrtOrder(damageLocationDTO.getPrtOrder());

		damageLocation.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if (damageLocation.getUpdateExtensionSchema() == null) {
			damageLocation.setUpdateExtensionSchema("IMS01146");
		}
		damageLocation.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		damageLocation.setUversion("!");

		damageLocation = damageLocationRepository.save(damageLocation);
		damageLocationDTO = damageLocationMapper.damageLocationToDamageLocationDTO(damageLocation);
		return damageLocationDTO;
	}

}
