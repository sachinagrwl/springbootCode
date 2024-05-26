package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.InterChangeParty;
import com.nscorp.obis.domain.Road;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;

import com.nscorp.obis.exception.RecordNotDeletedException;

import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.InterChangePartyRepository;
import com.nscorp.obis.repository.RoadRepository;

@Service
@Transactional
public class InterChangePartyServiceImpl implements InterChangePartyService{

	@Autowired
	InterChangePartyRepository interChangePartyRepo;

	@Autowired
	RoadRepository roadRepo;

	@Override
	public List<InterChangeParty> getAllTables(String ichgCode) {
		while(StringUtils.isNotBlank(ichgCode) && ichgCode.length() < 4){
			ichgCode += StringUtils.SPACE;
		}
		List<InterChangeParty> interChangeParty = interChangePartyRepo.getByIchgCode(ichgCode);
		if (interChangeParty.isEmpty()) {
			throw new NoRecordsFoundException("No Records found");
		}
		return interChangeParty;
	}


	@Override
	public InterChangeParty insertInterChangeParty(@Valid InterChangeParty interChangePartyObj, Map<String, String> headers) {
		String ichgPartyCode = interChangePartyObj.getIchgCode();
		while(StringUtils.isNotBlank(ichgPartyCode) && ichgPartyCode.length() < 4){
			ichgPartyCode += StringUtils.SPACE;
		}
		interChangePartyObj.setIchgCode(ichgPartyCode);
		if(interChangePartyObj.getIchgCode()!=null && interChangePartyRepo.existsById(interChangePartyObj.getIchgCode())) {
			throw new RecordAlreadyExistsException("Record with IchgCode Already Exists!");
		}

		if(interChangePartyObj.getRoadOtherInd().equals("R")) {
			if(roadRepo.existsByRoadName(interChangePartyObj.getIchgCode())) {
				interChangePartyObj.setIchgCode(interChangePartyObj.getIchgCode());
			}else {
				throw new NoRecordsFoundException("Interchange party is not defined in Road Table");
			}
		}


		UserId.headerUserID(headers);

		//logger.info("Header {}", headers.get("userid"));
		interChangePartyObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		interChangePartyObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		interChangePartyObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		interChangePartyObj.setUversion("!");


		InterChangeParty code = interChangePartyRepo.save(interChangePartyObj);


		return code;
	}


	@Override
	public void deleteInterChangeParty(InterChangeParty interChangeParty) {
		String ichgPartyCode = interChangeParty.getIchgCode();
		while(StringUtils.isNotBlank(ichgPartyCode) && ichgPartyCode.length() < 4){
			ichgPartyCode += StringUtils.SPACE;
		}
		if(interChangePartyRepo.existsById(interChangeParty.getIchgCode())) {
			interChangePartyRepo.deleteById(interChangeParty.getIchgCode());

		}else {
			throw new RecordNotDeletedException("Record Not Found!");
		}
	}



	@Override
	public InterChangeParty updateInterChangeParty(InterChangeParty interChangeParty, Map<String, String> headers) {
		// TODO Auto-generated method stub
		String ichgPartyCode = interChangeParty.getIchgCode();
		while(StringUtils.isNotBlank(ichgPartyCode) && ichgPartyCode.length() < 4){
			ichgPartyCode += StringUtils.SPACE;
		}
		//interChangeParty.setIchgCode(ichgPartyCode);
		UserId.headerUserID(headers);
		if(interChangePartyRepo.existsByIchgCode(interChangeParty.getIchgCode())) {
			InterChangeParty interChangePartyData = interChangePartyRepo.findByIchgCode(interChangeParty.getIchgCode());


			interChangePartyData.setCreateUserId(headers.get(CommonConstants.USER_ID));
			interChangePartyData.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			interChangePartyData.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			interChangePartyData.setUversion("!");
			interChangePartyData.setRoadOtherInd(interChangeParty.getRoadOtherInd());
			interChangePartyData.setIchgCdDesc(interChangeParty.getIchgCdDesc());
			if(interChangePartyData.getRoadOtherInd().equals("R")) {
				if(roadRepo.existsByRoadName(interChangePartyData.getIchgCode())) {
					interChangePartyData.setIchgCode(ichgPartyCode);
				}else {
					throw new NoRecordsFoundException("Interchange party is not defined in Road Table");
				}

			}

			InterChangeParty code = interChangePartyRepo.save(interChangePartyData);

			return code;
		}
		else
			throw new NoRecordsFoundException("Record with IchgCode " + interChangeParty.getIchgCode()+ " Not Found!");
	}

}

