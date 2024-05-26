package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.DVIRCodes;
import com.nscorp.obis.dto.DVIRCodesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DVIRCodesMapper {

    DVIRCodesMapper INSTANCE = Mappers.getMapper(DVIRCodesMapper.class);

    DVIRCodes DvirCodesDtoToDvirCodes(DVIRCodesDTO dvirCodesDTO);

    DVIRCodesDTO DvirCodesToDvirCodesDTO(DVIRCodes dvirCodes);

}
