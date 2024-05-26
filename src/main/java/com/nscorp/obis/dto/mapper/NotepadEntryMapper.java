package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.NotepadEntry;
import com.nscorp.obis.dto.NotepadEntryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotepadEntryMapper {

    NotepadEntryMapper INSTANCE = Mappers.getMapper(NotepadEntryMapper.class);

    NotepadEntry NotepadEntryDTOToNotepadEntry(NotepadEntryDTO notepadEntryDTO);

    NotepadEntryDTO NotepadEntryToNotepadEntryDTO(NotepadEntry notepadEntry);
}
