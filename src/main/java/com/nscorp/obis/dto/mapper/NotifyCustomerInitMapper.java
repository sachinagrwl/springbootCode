package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.NotifyCustomerInit;
import com.nscorp.obis.dto.NotifyCustomerInitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotifyCustomerInitMapper {

    NotifyCustomerInitMapper INSTANCE = Mappers.getMapper(NotifyCustomerInitMapper.class);

    NotifyCustomerInitDTO notifyCustomerInitToNotifyCustomerInitDTO(NotifyCustomerInit notifyCustomerInit);

    NotifyCustomerInit notifyCustomerInitDTOToNotifyCustomerInit(NotifyCustomerInitDTO notifyCustomerInitDTO);
}
