package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.NotifyCustomerInitView;
import com.nscorp.obis.dto.NotifyCustomerInitViewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotifyCustomerInitViewMapper {

    NotifyCustomerInitViewMapper INSTANCE = Mappers.getMapper(NotifyCustomerInitViewMapper.class);

    NotifyCustomerInitViewDTO notifyCustomerInitViewToNotifyCustomerInitViewDTO(NotifyCustomerInitView notifyCustomerInitView);

    NotifyCustomerInitView notifyCustomerInitViewDTOToNotifyCustomerInitView(NotifyCustomerInitViewDTO notifyCustomerInitViewDTO);
}
