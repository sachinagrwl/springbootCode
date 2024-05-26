package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.nscorp.obis.domain.ShipmentActiveView;
import com.nscorp.obis.dto.ShipmentActiveViewDTO;


@Mapper(componentModel = "spring")
public interface ShipmentActiveViewMapper {
	
	ShipmentActiveViewMapper INSTANCE = Mappers.getMapper(ShipmentActiveViewMapper.class);

	ShipmentActiveViewDTO shipmentActiveViewToShipmentActiveViewDTO(ShipmentActiveView shipmentActiveView);

	ShipmentActiveView shipmentActiveViewDTOToShipmentActiveView(ShipmentActiveView shipmentActiveViewMapperDTO);
	
}
