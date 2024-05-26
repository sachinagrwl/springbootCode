package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.dto.StorageRatesDTO;

@Mapper(componentModel = "spring")
public interface StorageRateDetailMapper {

	StorageRateDetailMapper INSTANCE = Mappers.getMapper(StorageRateDetailMapper.class);

	StorageRatesDTO storageRatesToStorageRatesDTO(StorageRates storageRates);

	StorageRates storageRatesDTOToStorageRates(StorageRatesDTO storageRatesDTO);
}
