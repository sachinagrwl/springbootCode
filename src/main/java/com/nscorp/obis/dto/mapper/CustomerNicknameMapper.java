package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CustomerNickname;
import com.nscorp.obis.dto.CustomerNicknameDTO;

@Mapper(componentModel = "spring")
public interface CustomerNicknameMapper {

	CustomerNicknameMapper INSTANCE = Mappers.getMapper(CustomerNicknameMapper.class);

	CustomerNickname customerNicknameDTOToCustomerNickname(CustomerNicknameDTO customerNicknameDTO);

	CustomerNicknameDTO customerNicknameToCustomerNicknameDTO(CustomerNickname customerNickname);

}
