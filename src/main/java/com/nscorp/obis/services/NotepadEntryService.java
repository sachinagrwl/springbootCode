package com.nscorp.obis.services;

import com.nscorp.obis.domain.NotepadEntry;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


public interface NotepadEntryService {

	List<NotepadEntry> fetchNotepadEntry(@Valid Long customerId, @Valid Long terminalId) throws SQLException;
	NotepadEntry addNotepadEntry(NotepadEntry notepadEntry, Map<String,String> headers);
	
}
