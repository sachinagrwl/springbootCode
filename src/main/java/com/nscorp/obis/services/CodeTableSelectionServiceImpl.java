package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CodeTableSelection;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CodeTableSelectionRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.ResourceListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CodeTableSelectionServiceImpl implements CodeTableSelectionService {
	
	@Autowired
	private GenericCodeUpdateRepository genericCodeRepo;
	
	@Autowired
	private CodeTableSelectionRepository genericTablesRepo;

	@Autowired
	private ResourceListRepository resourceListRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(CodeTableSelectionServiceImpl.class);
	
	@Override
	public List<CodeTableSelection> getAllTables() {
		
		List<CodeTableSelection> generics = genericTablesRepo.findAllByOrderByGenericTableAsc();
		if(generics.isEmpty()) {
			throw new NoRecordsFoundException("No Records are found for Generic Table");
		}
		return generics;
	}
	
	@Override
	public CodeTableSelection insertTable(@Valid CodeTableSelection codeObj, Map<String, String> headers) {
		
		UserId.headerUserID(headers);
		
		if(genericTablesRepo.existsById(codeObj.getGenericTable())) {
			throw new RecordAlreadyExistsException("Record with Table Name "+codeObj.getGenericTable()+" Already Exists!");
		}

		if(codeObj.getResourceNm() == null && !resourceListRepository.existsByResourceNameIgnoreCase(codeObj.getResourceNm())){
			throw new NoRecordsFoundException("Resource NM "  + codeObj.getResourceNm() + " is not available ");
		}
		else if(codeObj.getResourceNm() != null) {
			codeObj.setResourceNm(codeObj.getResourceNm().toUpperCase());
		}

		logger.info("Header {}", headers.get("userid"));

		codeObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		codeObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		codeObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		codeObj.setUversion("!");
		CodeTableSelection code = genericTablesRepo.save(codeObj);
		
		if(code == null) {
			throw new RecordNotAddedException("Record with Table Name "+codeObj.getGenericTable()+" cannot be Added!");
		}
		return code;
	}
	
	@Override
	public CodeTableSelection updateCodeTableSelection(CodeTableSelection tableObj, Map<String, String> headers) {
		
		UserId.headerUserID(headers);
		
		if(genericTablesRepo.existsByGenericTable(tableObj.getGenericTable())) {
			CodeTableSelection codeObj = genericTablesRepo.findByGenericTable(tableObj.getGenericTable());

			if(tableObj.getResourceNm() != null && !resourceListRepository.existsByResourceNameIgnoreCase(tableObj.getResourceNm())){
				throw new NoRecordsFoundException("Resource NM "  + tableObj.getResourceNm() + " is not available ");
			} else if(tableObj.getResourceNm() != null) {
				codeObj.setResourceNm(tableObj.getResourceNm().toUpperCase());
			}

			codeObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));

			codeObj.setGenericTableDesc(tableObj.getGenericTableDesc());
			codeObj.setGenCdFldSize(tableObj.getGenCdFldSize());
			codeObj.setUpdateDateTime(tableObj.getUpdateDateTime());
			genericTablesRepo.save(codeObj);
			return codeObj;
		} else {
			throw new NoRecordsFoundException("Record with Table Name "+tableObj.getGenericTable()+" Not Found!");
		}
	}

	@Override
	public void deleteTable(@Valid CodeTableSelection tableObj) {
		
			if(genericTablesRepo.existsByGenericTable(tableObj.getGenericTable())) {
				
				if(genericCodeRepo.existsByGenericTableIgnoreCase(tableObj.getGenericTable())) {
					String rep = tableObj.getGenericTable() + " Table has records and cannot be deleted!";
					throw new RecordNotDeletedException(rep);
				} else {
					genericTablesRepo.deleteByGenericTable(tableObj.getGenericTable());
				}
			} else {
				String rep = tableObj.getGenericTable() + " Table Not Found!";
				throw new RecordNotDeletedException(rep);
			}
			return;
	}
	
}
