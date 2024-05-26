package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.UnCd;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.UnCdRepository;

@Service
public class UnCdServiceImpl implements UnCdService {

	@Autowired
	UnCdRepository unCdRepo;

	@Override
	public List<UnCd> getAllTables(String unCd) {
		List<UnCd> list = new ArrayList<>();
		list = unCdRepo.searchAll(unCd);
		if (list.isEmpty()) {
			throw new NoRecordsFoundException("No Records found!");
		}
		return list;
	}

	@Override
	public UnCd deleteUnCode(UnCd unCd) {
		if (unCdRepo.existsByUnCdAndUversion(unCd.getUnCd(), unCd.getUversion())) {
			try {
				UnCd del = unCdRepo.findByUnCdAndUversion(unCd.getUnCd(), unCd.getUversion());
				unCdRepo.delete(del);
				return del;
			}
			catch(Exception e){
				throw new InvalidDataException("UN Code(s) is associated with Shipment Hazard and cannot be deleted.");
			}
		} else {
			throw new RecordNotDeletedException("Record Not Found!");
		}
	}

	@Override
	public UnCd updateUnDesc(UnCd unCd, Map<String, String> headers) {
		UserId.headerUserID(headers);

		if (unCdRepo.existsByUnCdAndUversion(unCd.getUnCd(), unCd.getUversion())) {

			UnCd existingUnCdDesc = unCdRepo.findByUnCd(unCd.getUnCd());

			if (!StringUtils.equals(existingUnCdDesc.getUnCd(), unCd.getUnCd()))
				throw new InvalidDataException("Invalid Change, UnCode is not editable");

			existingUnCdDesc.setUversion("!");
			existingUnCdDesc.setUnDsc(unCd.getUnDsc());
			existingUnCdDesc.setUnInstructCd(unCd.getUnInstructCd());
			existingUnCdDesc.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			existingUnCdDesc.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());

			unCdRepo.save(existingUnCdDesc);
			return existingUnCdDesc;
		} else {
			throw new NoRecordsFoundException("No record found for this 'Un Code': " + unCd.getUnCd());
		}
	}

	@Override
	public UnCd addUnCode(UnCd unCdObj, Map<String, String> headers) {
		// TODO Auto-generated method stub
		UserId.headerUserID(headers);
		if (!unCdRepo.existsByUnCd(unCdObj.getUnCd())) {
			unCdObj.setUversion("!");
			unCdObj.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			unCdObj.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			unCdObj.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
			unCdObj.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
			unCdObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			unCdObj.setUnCd(unCdObj.getUnCd());
			unCdObj.setUnDsc(unCdObj.getUnDsc());
			unCdObj.setUnInstructCd(unCdObj.getUnInstructCd());
			

			unCdRepo.save(unCdObj);
			return unCdObj;
		}
		else {
			throw new RecordAlreadyExistsException("Record Already Exists");
		}
	}
}
