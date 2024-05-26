package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.NotifyQueue;
import com.nscorp.obis.domain.PayGuarantee;
import com.nscorp.obis.dto.NotifyQueueDTO;
import com.nscorp.obis.dto.PayGuaranteeDTO;

@Mapper(componentModel = "spring")
public interface PayGuaranteeMapper {

	
	PayGuaranteeMapper INSTANCE = Mappers.getMapper(PayGuaranteeMapper.class);
	PayGuaranteeDTO payGuaranteeToPayGuaranteeDTO(PayGuarantee payGuarantee);
	PayGuarantee payGuaranteeDTOToPayGuarantee(PayGuaranteeDTO payGuaranteeDTO);
}
