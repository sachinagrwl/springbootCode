package com.nscorp.obis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.PlacardType;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.PlacardTypeRepository;

@Service
@Transactional
public class PlacardTypeServiceImpl implements PlacardTypeService {

	@Autowired
	PlacardTypeRepository placardTypeRepo;

	@Override
	public List<PlacardType> getAllPlacard(String placardCd) {

		List<PlacardType> placardTypeList = new ArrayList<>();
		placardTypeList = placardTypeRepo.findAllByOrderByPlacardCdAsc();
		if (placardTypeList.isEmpty()) {
			throw new NoRecordsFoundException("No Records found!");
		}
		return placardTypeList;
	}

	@Override
	public PlacardType addPlacard(PlacardType placardTypeObj, Map<String, String> headers) {
		UserId.headerUserID(headers);

		if (placardTypeObj.getPlacardCd() == null || placardTypeRepo.existsByPlacardCd(placardTypeObj.getPlacardCd())) {
			throw new RecordNotAddedException("PlacardCd Already Exists");
		}

		placardTypeObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		placardTypeObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		placardTypeObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		placardTypeObj.setUversion("!");

		PlacardType code = placardTypeRepo.save(placardTypeObj);
		return code;
	}
	
	@Override
	public PlacardType updatePlacard(PlacardType placardTypeObj, Map<String, String> headers) {
		UserId.headerUserID(headers);

		if (placardTypeRepo.existsByPlacardCd(placardTypeObj.getPlacardCd())) {
			PlacardType placard = placardTypeRepo.findByPlacardCd(placardTypeObj.getPlacardCd());
			placard.setCreateUserId(headers.get(CommonConstants.USER_ID));
			placard.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			placard.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			placard.setUversion(placardTypeObj.getUversion());
			placard.setPlacardLongDesc(placardTypeObj.getPlacardLongDesc());
			placard.setPlacardShortDesc(placardTypeObj.getPlacardShortDesc());
			PlacardType code = placardTypeRepo.save(placard);
			return code;
		}else
			throw new NoRecordsFoundException("No Record Found!");
	}
	
	@Override
	public List<PlacardType> deletePlacardType(PlacardType placardType) throws ConstraintViolationException {
		if(placardTypeRepo.existsByPlacardCdAndUversion(placardType.getPlacardCd(),placardType.getUversion())) {
			List<PlacardType> placardDel = placardTypeRepo.findByPlacardCdAndUversion(placardType.getPlacardCd(),placardType.getUversion());
			placardTypeRepo.deleteByPlacardCdAndUversion(placardType.getPlacardCd(),placardType.getUversion());
			return placardDel;
		}
		else {
			throw new NoRecordsFoundException("No Records Found!");
		}
	}
}
