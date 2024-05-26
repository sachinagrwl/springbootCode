package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.TerminalFunction;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.TerminalFunctionRepository;
import com.nscorp.obis.repository.TerminalRepository;

@Service
@Transactional
public class TerminalFunctionServiceImpl implements TerminalFunctionService {

	@Autowired
	TerminalFunctionRepository terminalFunctionRepo;
	@Autowired
	TerminalRepository terminalRepo;


	@Override
	public List<TerminalFunction> getTerminalFunctionList(Long terminalId, String functionName) {
		List<TerminalFunction> terminalFunctionList = new ArrayList<>();
		if (terminalId != null) {
			if (!terminalFunctionRepo.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id : " + terminalId);
			}
		}
		if (functionName != null) {
			if (!terminalFunctionRepo.existsByFunctionName(functionName)) {
				throw new NoRecordsFoundException("No Function Name Found  : " + functionName);
			}
		} 
		if (terminalId == null && functionName == null) {
			 terminalFunctionList = terminalFunctionRepo.findAll();
			//return terminalFunctionList;
		}
		else if (terminalId != null && functionName != null) {
			TerminalFunction terminalFunction = terminalFunctionRepo.findByTerminalIdAndFunctionName(terminalId,functionName);	
			terminalFunctionList.add(terminalFunction);
			}
		 
		else {
			terminalFunctionList = terminalFunctionRepo.findByTerminalIdOrFunctionName(terminalId,functionName);
			}

//		if(terminalFunctionList.size()>0 && terminalFunctionList!=null){
//			for(TerminalFunction terminalFunction: terminalFunctionList){
//				Terminal terminal = terminalRepo.findByTerminalId(terminalFunction.getTerminalId());
//				if(terminal!=null)
//					terminalFunction.setTerminalName(terminal.getTerminalName());
//			}
//		}
		return terminalFunctionList;
	}




	@Override
	public TerminalFunction updateTerminalFunction(TerminalFunction terminalFunctionObj,
			Map<String, String> headers) {

		UserId.headerUserID(headers);
		if (terminalFunctionRepo.existsByTerminalIdAndFunctionName(terminalFunctionObj.getTerminalId(),
				terminalFunctionObj.getFunctionName())) {
			TerminalFunction terminalFun = terminalFunctionRepo.findByTerminalIdAndFunctionName(terminalFunctionObj.getTerminalId(),terminalFunctionObj.getFunctionName());
			terminalFun.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			terminalFun.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
			terminalFun.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			terminalFun.setUversion("!");
			terminalFun.setStatusFlag(terminalFunctionObj.getStatusFlag());
			if(terminalFunctionObj.getEndDate()!=null && terminalFunctionObj.getEffectiveDate()!=null) {
			int endDate = terminalFunctionObj.getEndDate().compareTo(terminalFunctionObj.getEffectiveDate());
			if(endDate>0){
				terminalFun.setEndDate(terminalFunctionObj.getEndDate());
				terminalFun.setEffectiveDate(terminalFunctionObj.getEffectiveDate());

			}else if(endDate ==0){
				terminalFun.setEndDate(terminalFunctionObj.getEndDate());
				terminalFun.setEffectiveDate(terminalFunctionObj.getEffectiveDate());

			}
			else
				throw new InvalidDataException("End Date should be later than the Effective Date");
			}
			else {
			terminalFun.setEndDate(terminalFunctionObj.getEndDate());
			terminalFun.setEffectiveDate(terminalFunctionObj.getEffectiveDate());
			}

			terminalFunctionRepo.save(terminalFun);
			return terminalFun;
		} else
			throw new NoRecordsFoundException("Record with Terminal Function Not Found!");
	}

}
