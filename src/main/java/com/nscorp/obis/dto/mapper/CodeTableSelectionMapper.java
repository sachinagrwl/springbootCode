package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.CodeTableSelection;
import com.nscorp.obis.dto.CodeTableSelectionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CodeTableSelectionMapper {
	
	CodeTableSelectionMapper INSTANCE = Mappers.getMapper(CodeTableSelectionMapper.class);

	CodeTableSelectionDTO codeTableSelectionToCodeTableSelectionDTO(CodeTableSelection codeTableSelection);

	CodeTableSelection codeTableSelectionDTOToCodeTableSelection(CodeTableSelectionDTO codeTableSelectionDTO);
	
	/* default  CodeTableSelectionDTO codeTableSelectionToCodeTableSelectionDTO(CodeTableSelection codeTableSelection)
	{
        if ( codeTableSelection == null ) {
            return null;
        }

        CodeTableSelectionDTO codeTableSelectionDTO = new CodeTableSelectionDTO();

        if ( codeTableSelection.getGenericTable() != null ) {
        	codeTableSelectionDTO.
        	setGenericTable( codeTableSelection.getGenericTable() );
        }

        return codeTableSelectionDTO;
    } */
}
