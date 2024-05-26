package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.NotepadEntry;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.NotepadEntryRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@Transactional
@Service
public class NotepadEntryServiceImpl implements NotepadEntryService {
	
	@Autowired
	NotepadEntryRepository notepadEntryRepository;

	@Autowired
	CustomerRepository CustomerRepository;

	@Autowired
	TerminalRepository terminalRepository;
	
	@Autowired
	SpecificationGenerator specificationGenerator;

	@Override
	public List<NotepadEntry> fetchNotepadEntry(@Valid Long customerId, @Valid Long terminalId) throws SQLException {
		
		if (terminalId != null) {
			if (!terminalRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id : " + terminalId);
			}
		}
		if (customerId != null) {
			if (!CustomerRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer Found with this customer id : " + customerId);
			}
		}
		if (customerId == null && terminalId == null) {
			throw new NoRecordsFoundException("Pass any parameter");
		}
		Specification<NotepadEntry> specification = specificationGenerator.notepadEntrySpecification(customerId,terminalId);
		List<NotepadEntry> notepadEntry = notepadEntryRepository.findAll(specification);
		if (notepadEntry.isEmpty()) {
			throw new NoRecordsFoundException("No Record found for this combination");
		}
		return notepadEntry;
	}

	public NotepadEntry addNotepadEntry(NotepadEntry notepadEntry, Map<String, String> headers) {
		Long customerId = notepadEntry.getCustomerId();

		if (customerId != null) {
			if (!CustomerRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer Found with this customer id : " + customerId);
			}
		} else {
			throw new NoRecordsFoundException("customerId cant be null. Enter Valid customerId");
		}
		UserId.headerUserID(headers);
		String userId = headers.get(CommonConstants.USER_ID);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		Double generatedNotepadId = notepadEntryRepository.SGK();
		notepadEntry.setNotepadId(generatedNotepadId);
		notepadEntry.setCreateUserId(userId.toUpperCase());
		notepadEntry.setUpdateUserId(userId.toUpperCase());
		notepadEntry.setUpdateExtensionSchema(extensionSchema);
		notepadEntry.setUversion("!");
		NotepadEntry notepadEntryObj = notepadEntryRepository.save(notepadEntry);
		if(notepadEntryObj == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}
		return notepadEntryObj;
	}
	

}
