package com.nscorp.obis.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.nscorp.obis.domain.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.TermFreeDay;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.TermFreeDayRepository;
import com.nscorp.obis.repository.TerminalRepository;

@Service
@Transactional
public class TermFreeDayServiceImpl implements TermFreeDayService {
	
	@Autowired
	private GenericCodeUpdateRepository genericCodeRepo;

    @Autowired
    private TermFreeDayRepository termFreeDayRepository;

	@Autowired
	private TerminalRepository terminalRepo;

    private void validations(TermFreeDay termDay) {
    	
    	if(termDay.getCloseRsnDesc() != null && (!genericCodeRepo.existsByGenericTableAndGenericTableCodeIgnoreCase("CLOSERSN", termDay.getCloseRsnCd()))) {
			throw new NoRecordsFoundException("Close Code " + termDay.getCloseRsnCd() + " is not available");
		}

        if(termDay.getCloseRsnDesc() == null && !genericCodeRepo.existsByGenericTableAndGenericTableCodeIgnoreCase("CLOSERSN", termDay.getCloseRsnCd())){
            throw new NoRecordsFoundException("Close Code " + termDay.getCloseRsnCd() + " is not available");
        }
    			
		//Checking Free Day value constraints//
		if(termDay.getFreeDay() != "Y" && termDay.getFreeDay() != "N") {
			throw new RecordNotAddedException("'Free Day' value must be either 'Y' or 'N'");
		}

        if(termDay.getCloseFromTime().isBefore(CommonConstants.ONE_MINUTE_AFTER_MIDNIGHT))
            throw new RecordNotAddedException("The Close from time should be greater than or equal to 00:01:00");

		//Time Value Constraint//
		if(termDay.getCloseToTime() != null && (termDay.getCloseFromTime().compareTo(termDay.getCloseToTime()) >= 0)) {
			throw new RecordNotAddedException("Time Reopened should be greater than Time Closed");
		}
		
    }
    
	@Override
	public List<TermFreeDay> getAllFreeDays(List<Long> termId, LocalDate closeDate, String closeCode, LocalTime closeFromTime) {

		String nullListCheck = null;
		if(termId != null && !termId.isEmpty())
			nullListCheck = "hasValues";

		List<TermFreeDay> termFreeDayList = termFreeDayRepository.findAll(termId,closeDate,closeCode,closeFromTime,nullListCheck);


		if(termFreeDayList.isEmpty())
			throw new NoRecordsFoundException("No Record Found under this search!");

		return termFreeDayList;
	}

    @Override
	public TermFreeDay addTermDay(@Valid TermFreeDay termDay, Map<String, String> headers) {
    	UserId.headerUserID(headers);
		Terminal terminal = terminalRepo.findByTerminalId(termDay.getTermId());
		if(terminalRepo.existsByTerminalId(termDay.getTermId())) {
			if (termFreeDayRepository.existsByTermIdAndCloseDateAndCloseFromTime(termDay.getTermId(), termDay.getCloseDate(), termDay.getCloseFromTime())) {
				throw new RecordAlreadyExistsException("Record already exists under Terminal Name: " + terminal.getTerminalName() + ", CloseDate: " + termDay.getCloseDate() + ", TimeClosed: " + termDay.getCloseFromTime());
			}
			validations(termDay);
			termDay.setCreateUserId(headers.get(CommonConstants.USER_ID));
			termDay.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			termDay.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			termDay.setUversion("!");
			TermFreeDay termFreeDay = termFreeDayRepository.save(termDay);
			if (termFreeDay == null) {
				throw new RecordNotAddedException("Record Not added");
			}
			return termFreeDay;
		} else{
			throw new RecordNotAddedException("Terminal Id: "+termDay.getTermId()+ " Not Found!");
		}
	}
    
    @Override
	public TermFreeDay updateTermDay(@Valid TermFreeDay termDay, Map<String, String> headers) {
    	
    	UserId.headerUserID(headers);
		if(termFreeDayRepository.existsByTermIdAndCloseDateAndCloseFromTime(termDay.getTermId(), termDay.getCloseDate(), termDay.getCloseFromTime())) {
			TermFreeDay existingTermDay = termFreeDayRepository.findByTermIdAndCloseDateAndCloseFromTime(termDay.getTermId(), termDay.getCloseDate(), termDay.getCloseFromTime());
			
			validations(termDay);

			existingTermDay.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			
			existingTermDay.setCloseRsnCd(termDay.getCloseRsnCd());
			existingTermDay.setCloseRsnDesc(termDay.getCloseRsnDesc());
			existingTermDay.setFreeDay(termDay.getFreeDay());
			existingTermDay.setCloseToTime(termDay.getCloseToTime());
			existingTermDay.setUpdateExtensionSchema(CommonConstants.EXTENSION_SCHEMA);
			
			termFreeDayRepository.save(existingTermDay);
			return existingTermDay;
		
		} else {
			throw new NoRecordsFoundException("No record Found for TermId: " + termDay.getTermId() + ", CloseDate: " + termDay.getCloseDate() + ", TimeClosed: " + termDay.getCloseFromTime());
		}
	}

    @Override
    public void deleteTermFreeDay(TermFreeDay termFreeDayObj) {
        if(termFreeDayRepository.existsByTermIdAndCloseDateAndCloseFromTime(termFreeDayObj.getTermId(),termFreeDayObj.getCloseDate(),termFreeDayObj.getCloseFromTime())) {
            termFreeDayRepository.deleteByTermIdAndCloseDateAndCloseFromTime(termFreeDayObj.getTermId(),termFreeDayObj.getCloseDate(),termFreeDayObj.getCloseFromTime());
        }
        else {
            String rep = "Record with TermId: " + termFreeDayObj.getTermId() + " and Closed Date: " + termFreeDayObj.getCloseDate() + " and Time Closed: " + termFreeDayObj.getCloseFromTime() + " Record Not Found!";
            throw new RecordNotDeletedException(rep);
        }
    }

	@Override
	public List<String> getAllReasonDesc() {
		List<String> reasonDesc = termFreeDayRepository.findByDistinctReasonDesc();
		return reasonDesc.stream().map(String :: trim).collect(Collectors.toList());
	}
}
