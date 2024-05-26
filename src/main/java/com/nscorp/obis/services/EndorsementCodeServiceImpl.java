package com.nscorp.obis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EndorsementCode;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.EndorsementCodeRepository;

@Transactional
@Service
public class EndorsementCodeServiceImpl implements EndorsementCodeService {

	@Autowired
	EndorsementCodeRepository endorsementCodeRepo;

	@Override
	public List<EndorsementCode> getAllTables(String endorsementCd, String endorseCdDesc) {

		List<EndorsementCode> list = new ArrayList<>();
		list = endorsementCodeRepo.searchAll(endorsementCd, endorseCdDesc);
		if (list.isEmpty()) {
			throw new NoRecordsFoundException("No Records found!");
		}
		return list;
	}

	@Override
	public EndorsementCode addEndorsementCode(@Valid EndorsementCode endorsementCodeObj, Map<String, String> headers) {
		if (endorsementCodeObj.getEndorsementCd() != null) {
			if (endorsementCodeRepo.existsById(endorsementCodeObj.getEndorsementCd())) {
				throw new RecordAlreadyExistsException("Duplicate Endorsement Code");
			} else {
				if (endorsementCodeObj.getEndorseCdDesc() != null) {
					endorsementCodeObj.setEndorsementCd(endorsementCodeObj.getEndorsementCd());
					endorsementCodeObj.setEndorseCdDesc(endorsementCodeObj.getEndorseCdDesc());
				} else
					throw new NoRecordsFoundException("W-DESC_DATA required");
			}
		} else {
			throw new NoRecordsFoundException("W-ENDORSEMENT_CD Required");
		}

		UserId.headerUserID(headers);

		// logger.info("Header {}", headers.get("userid"));
		endorsementCodeObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		endorsementCodeObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		endorsementCodeObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		endorsementCodeObj.setUversion("!");

		EndorsementCode code = endorsementCodeRepo.save(endorsementCodeObj);

		return code;
	}

	@Override
	public EndorsementCode updateEndorsementCode(@Valid EndorsementCode endorsementCodeObj,
			Map<String, String> headers) {
		if (endorsementCodeObj.getEndorsementCd() != null) {
			if (endorsementCodeObj.getEndorseCdDesc() != null) {
				endorsementCodeObj.setEndorseCdDesc(endorsementCodeObj.getEndorseCdDesc());
			} else
				throw new NoRecordsFoundException("W-DESC_DATA required");

		} else {
			throw new NoRecordsFoundException("W-ENDORSEMENT_CD Required");
		}

		UserId.headerUserID(headers);

		// logger.info("Header {}", headers.get("userid"));
		endorsementCodeObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		endorsementCodeObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		endorsementCodeObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		endorsementCodeObj.setUversion(endorsementCodeObj.getUversion());

		EndorsementCode code = endorsementCodeRepo.save(endorsementCodeObj);

		return code;
	}

	@Override
	public void deleteEndorsementCode(EndorsementCode endorsementCode) {
		if (endorsementCodeRepo.existsById(endorsementCode.getEndorsementCd())) {
			endorsementCodeRepo.deleteById(endorsementCode.getEndorsementCd());

		} else {
			throw new RecordNotDeletedException("Record Not Found!");
		}
	}

}
