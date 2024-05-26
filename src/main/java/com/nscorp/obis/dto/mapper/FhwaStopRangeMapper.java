package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.dto.FhwaStopRangeDTO;
import com.nscorp.obis.domain.FhwaStopRange;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FhwaStopRangeMapper {

    FhwaStopRangeMapper INSTANCE = Mappers.getMapper(FhwaStopRangeMapper.class);

    FhwaStopRangeDTO fhwaStopRangeToFhwaStopRangeDTO(FhwaStopRange fhwaStopRange);

    FhwaStopRange fhwaStopRangeDTOToFhwaStopRange(FhwaStopRangeDTO fhwaStopRangeDTO);
}
