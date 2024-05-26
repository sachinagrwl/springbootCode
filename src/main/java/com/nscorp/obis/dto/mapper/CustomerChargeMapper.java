package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.dto.CustomerChargeDTO;

@Mapper(componentModel = "spring")
public interface CustomerChargeMapper {
	
	CustomerChargeMapper INSTANCE = Mappers.getMapper(CustomerChargeMapper.class);
	CustomerChargeDTO CustomerChargeToCustomerChargeDTO(CustomerCharge customerCharge);
	CustomerCharge CustomerChargeDTOToCustomerCharge(CustomerChargeDTO customerChargeDto);
	//CustomerCharge pageCustomerChargesToCustomerChargeDTO(Page<CustomerCharge> pageCustomerCharges);
	

}
