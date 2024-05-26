package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.ShiplineCustomer;
import com.nscorp.obis.dto.ShiplineCustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShiplineCustomerMapper {

	ShiplineCustomerMapper INSTANCE = Mappers.getMapper(ShiplineCustomerMapper.class);

	ShiplineCustomerDTO steamshipCustomerToSteamshipCustomerDTO(ShiplineCustomer steamshipCustomer);

	ShiplineCustomer steamshipCustomerDTOToSteamshipCustomer(ShiplineCustomerDTO steamshipCustomerDTO);
}
