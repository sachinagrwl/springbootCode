package com.nscorp.obis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.AARType;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.AARTypeRepository;

@Service
@Transactional
public class AARTypeServiceImpl implements AARTypeService {
	
	@Autowired
	private AARTypeRepository aarTypeRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(AARTypeServiceImpl.class);
	
	@Override
	public List<AARType> getAllAARTypes(String type) {
		List<String> search = new ArrayList<>();
		List<AARType> aarTypeList;
		List<AARType> list = new ArrayList<>();
		logger.info("Type - {}", type);
		if(type.equals("car")) {
			logger.info("Type - {}", type);
			search.add("P");
			search.add("Q");
			search.add("S");
			for(String var : search) {
				aarTypeList = aarTypeRepo.findByAarTypeStartsWith(var);
				list.addAll(aarTypeList);
			}
		}
		else if(type.equals("freight")) {
			logger.info("Type - {}", type);
			search.add("U");
			search.add("Z");
			for(String var : search) {
				aarTypeList = aarTypeRepo.findByAarTypeStartsWith(var);
				list.addAll(aarTypeList);
			}
		}
		if(list.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for AAR Type!");
		}
		return list;
	}
	
	@Override
	public AARType updateAARType(@Valid AARType aarTypeObj, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if(aarTypeRepo.existsByAarType(aarTypeObj.getAarType())) {
			AARType aarType = aarTypeRepo.findByAarType(aarTypeObj.getAarType());
			aarType.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			aarType.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			aarType.setUversion("!");
			aarType.setAarDescription(aarTypeObj.getAarDescription());
			aarType.setAarCapacity(aarTypeObj.getAarCapacity());
			aarType.setImDescription(aarTypeObj.getImDescription());
			aarType.setStandardAarType(aarTypeObj.getStandardAarType());
			AARType code = aarTypeRepo.save(aarType);
			return code;
	}
		else
			throw new NoRecordsFoundException("Record with AAR Type " + aarTypeObj.getAarType()+ " Not Found!");
	}
	
	@Override
	public AARType insertAARType(@Valid AARType aarTypeObj, Map<String, String> headers) {
		if(aarTypeObj.getAarType()!=null && aarTypeRepo.existsByAarType(aarTypeObj.getAarType())) {
			throw new RecordAlreadyExistsException("Record with AAR Type Already Exists!");
		}
		
		UserId.headerUserID(headers);
		logger.info("Header {}", headers.get("userid"));
		aarTypeObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		aarTypeObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		aarTypeObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		aarTypeObj.setUversion("!");
		AARType code = aarTypeRepo.save(aarTypeObj);
		return code;
	}
	@Override
	public void deleteAARType(AARType aarType) {
		if(aarTypeRepo.existsByAarType(aarType.getAarType())) {
			aarTypeRepo.deleteByAarType(aarType.getAarType());
		}else {
			throw new RecordNotDeletedException("Record Not Found!");
		}
	}

	@Override
	public List<AARType> getAllAARTypesList(List<String> type,List<String> description,List<Integer> capacity) {
		List<AARType> aarTypeList = new ArrayList<>();
		logger.info("--> Type: {} and desc: {} and cap: {}", type, description, capacity);
		if(type==null && description==null && capacity ==null) {
			aarTypeList=aarTypeRepo.findAllByOrderByAarType();
		}else if (type!=null && !type.isEmpty() && description==null && capacity ==null){
			aarTypeList=aarTypeRepo.findByAarTypeInOrderByAarTypeAsc(type);
		}else if (type!=null && !type.isEmpty() && description!=null && !description.isEmpty() && capacity ==null){
			aarTypeList=aarTypeRepo.findByAarTypeInAndAarDescriptionInOrderByAarTypeAsc(type, description);
		}else if (type!=null && !type.isEmpty() && description!=null && !description.isEmpty() && capacity !=null && !capacity.isEmpty()){
			aarTypeList=aarTypeRepo.findByAarTypeInAndAarDescriptionInAndAarCapacityInOrderByAarTypeAsc(type, description, capacity);
		}else if(type==null && description!=null && !description.isEmpty() && capacity ==null){
			aarTypeList=aarTypeRepo.findByAarDescriptionInOrderByAarTypeAsc(description);
		}else if(type==null && description!=null && !description.isEmpty() && capacity != null && !capacity.isEmpty()){
			aarTypeList=aarTypeRepo.findByAarDescriptionInAndAarCapacityInOrderByAarTypeAsc(description, capacity);
		}else if(type==null && description==null && capacity != null && !capacity.isEmpty()){
			aarTypeList=aarTypeRepo.findByAarCapacityInOrderByAarTypeAsc(capacity);
		}else if(type!=null && !type.isEmpty() && description==null && capacity != null && !capacity.isEmpty()){
			aarTypeList=aarTypeRepo.findByAarTypeInAndAarCapacityInOrderByAarTypeAsc(type,capacity);
		}
		if(aarTypeList.isEmpty()) {
			throw new NoRecordsFoundException("No Records found !");
		}
		return aarTypeList;
	}
}
