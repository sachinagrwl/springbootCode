package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.TermFreeDay;
import com.nscorp.obis.dto.TermFreeDayDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TermFreeDayMapper {

    TermFreeDayMapper INSTANCE = Mappers.getMapper(TermFreeDayMapper.class);
    TermFreeDayDTO TermFreeDayToTermFreeDayDTO(TermFreeDay termFreeDay);
    TermFreeDay TermFreeDayDTOToTermFreeDay(TermFreeDayDTO termFreeDayDTO);
    
}
