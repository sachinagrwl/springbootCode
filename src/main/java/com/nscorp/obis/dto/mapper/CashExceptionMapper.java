package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CashException;
import com.nscorp.obis.dto.CashExceptionDTO;

@Mapper(componentModel = "spring")
public interface CashExceptionMapper {

	CashExceptionMapper INSTANCE = Mappers.getMapper(CashExceptionMapper.class);

	CashExceptionDTO cashExceptionToCashExceptionDTO(CashException cashException);

	CashException cashExceptionDTOToCashException(CashExceptionDTO cashExceptionDTO);

}
