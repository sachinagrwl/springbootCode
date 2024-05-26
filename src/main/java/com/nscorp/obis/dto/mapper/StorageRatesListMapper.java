package com.nscorp.obis.dto.mapper;
import com.nscorp.obis.dto.StorageRatesDTO;
import com.nscorp.obis.dto.StorageRatesListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StorageRatesListMapper{

    StorageRatesListMapper INSTANCE= Mappers.getMapper(StorageRatesListMapper.class);

    StorageRatesDTO StorageRatesListDTOToStorageRatesDTO(StorageRatesListDTO storageRatesListDTO);

}