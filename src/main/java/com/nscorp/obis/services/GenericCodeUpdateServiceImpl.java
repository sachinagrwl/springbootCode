package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.DomainValueConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.CodeTableSelectionRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GenericCodeUpdateServiceImpl implements GenericCodeUpdateService {
	
	@Autowired
	private GenericCodeUpdateRepository codeListRepo;

	@Autowired
	private CodeTableSelectionRepository codeTableSelectionRepository;
	
	private void isValidGenFlag(GenericCodeUpdate genericcodeupdate) {
		
		String tableName = genericcodeupdate.getGenericTable();
		String genFlag = genericcodeupdate.getGenericFlag();
		if(tableName.equalsIgnoreCase(CommonConstants.EDI_RSN_TABLE)) {
			if(!Arrays.asList(DomainValueConstants.EDI_RSN_VALUES).contains(genFlag)) {
				throw new RecordNotAddedException("'EDI_RSN' table can have 'GEN_FLAG' values of: 'Y', 'N' & null only");
			}
		} else if(tableName.equalsIgnoreCase(CommonConstants.INREJECT_TABLE) || tableName.equalsIgnoreCase(CommonConstants.INRTRJCT_TABLE) || tableName.equalsIgnoreCase(CommonConstants.LP_CFG_TABLE)) {
			if(!Arrays.asList(DomainValueConstants.ALLOWED_GEN_FLAG_VALUES).contains(genFlag)) {
				if(tableName.equalsIgnoreCase(CommonConstants.INREJECT_TABLE)) {
					throw new RecordNotAddedException(" 'INREJECT' table can have 'GEN_FLAG' values of: 'B', 'C', 'T' & null only");
				} else if(tableName.equalsIgnoreCase(CommonConstants.INRTRJCT_TABLE)) {
					throw new RecordNotAddedException(" 'INRTRJCT' table can have 'GEN_FLAG' values of: 'B', 'C', 'T' & null only");
				} else if(tableName.equalsIgnoreCase(CommonConstants.LP_CFG_TABLE)) {
					throw new RecordNotAddedException(" 'LP_CFG' table can have 'GEN_FLAG' values of: 'B', 'C', 'T' & null only");
				}
			}
		}
	}
	
	/* This Method Is Used To Fetch Values Using Table Name */
	@Override
	public List<GenericCodeUpdate> getByTableName(String tableName) {

		if(!codeTableSelectionRepository.existsByGenericTableIgnoreCase(tableName)) {
			throw new NoRecordsFoundException("No record found under the TableName: " + tableName);
		}
		return codeListRepo.findByGenericTableIgnoreCase(tableName);
	}
	
	@Override
	public GenericCodeUpdate insertCode(@Valid @NotNull GenericCodeUpdate genericcodeupdate, Map<String, String> headers) {
		
		UserId.headerUserID(headers);

		String tableName = genericcodeupdate.getGenericTable();
		if(!codeTableSelectionRepository.existsByGenericTableIgnoreCase(tableName))
			throw new NoRecordsFoundException("No record found under this TableName: " + tableName);

		Short a  = codeTableSelectionRepository.findBySize(tableName);
		if(genericcodeupdate.getGenericTableCode().length() > a){
			throw new SizeExceedException("Maximum Length for Table code is " + codeTableSelectionRepository.findBySize(tableName));
		}

		if(codeListRepo.existsByGenericTableAndGenericTableCodeIgnoreCase(genericcodeupdate.getGenericTable(), genericcodeupdate.getGenericTableCode())) {
			throw new RecordAlreadyExistsException("Table Code is already exists under " + genericcodeupdate.getGenericTable());

		}

		isValidGenFlag(genericcodeupdate);

		genericcodeupdate.setCreateUserId(headers.get("userid"));
		genericcodeupdate.setUpdateUserId(headers.get("userid"));

		genericcodeupdate.setUpdateExtensionSchema(headers.get("extensionschema"));
		genericcodeupdate.setUversion("!");


		GenericCodeUpdate code = codeListRepo.save(genericcodeupdate);

		if(code == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}

		return code;
	}

	@Override
	public GenericCodeUpdate updateCode(@Valid GenericCodeUpdate codeUpdate, Map<String, String> headers) {

		UserId.headerUserID(headers);
		
		if(!codeListRepo.existsByGenericTableAndGenericTableCodeIgnoreCase(codeUpdate.getGenericTable(), codeUpdate.getGenericTableCode())) {
			throw new NoRecordsFoundException("No record Found Under this TableName and Table Code");
		}
		GenericCodeUpdate code = codeListRepo.findByGenericTableAndGenericTableCodeIgnoreCase(codeUpdate.getGenericTable(), codeUpdate.getGenericTableCode());

		isValidGenFlag(codeUpdate);
		
		code.setUpdateUserId(headers.get("userid"));

		code.setGenericLongDescription(codeUpdate.getGenericLongDescription());
		code.setGenericShortDescription(codeUpdate.getGenericShortDescription());
		code.setGenericFlag(codeUpdate.getGenericFlag());
		codeListRepo.save(code);
		return code;
	}

	@Override
	public void deleteCode(@Valid GenericCodeUpdate codeDelete) {
			if (codeListRepo.existsByGenericTableAndGenericTableCodeIgnoreCase(codeDelete.getGenericTable(), codeDelete.getGenericTableCode())) {
				codeListRepo.deleteByGenericTableAndGenericTableCodeIgnoreCase(codeDelete.getGenericTable(), codeDelete.getGenericTableCode());

			} else {
				String rep = codeDelete.getGenericTable()  + " and " + codeDelete.getGenericTableCode() + " Record Not Found!";
				throw new RecordNotDeletedException(rep);
			}
		}
}




