package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.DeliveryDetail;
import com.nscorp.obis.dto.DeliveryDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeliveryDetailMapper {
	
	DeliveryDetailMapper INSTANCE = Mappers.getMapper(DeliveryDetailMapper.class);
	DeliveryDetailDTO deliveryDetailToDeliveryDetailDTO(DeliveryDetail deliveryDetail);
	DeliveryDetail deliveryDetailDTOToDeliveryDetail(DeliveryDetailDTO deliveryDetailDTO);

}
