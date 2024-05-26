package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CustomerPerDiemRateSelection;
import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;

@Mapper(componentModel = "spring")
public interface CustomerPerDiemRateSelectionMapper {
 
    CustomerPerDiemRateSelectionMapper INSTANCE = Mappers.getMapper(CustomerPerDiemRateSelectionMapper.class);

    CustomerPerDiemRateSelection customerPerDiemRateSelectionDTOToCustomerPerDiemRateSelection(CustomerPerDiemRateSelectionDTO customerPerDiemRateSelectionDTO);
    CustomerPerDiemRateSelectionDTO customerPerDiemRateSelectionToCustomerPerDiemRateSelectionDTO(CustomerPerDiemRateSelection customerPerDiemRateSelection);
}
