package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.StorageOverrideBillToParty;
import com.nscorp.obis.dto.StorageOverrideBillToPartyDTO;

@Mapper(componentModel = "spring")
public interface StorageOverrideBillToPartyMapper {
	
	StorageOverrideBillToPartyMapper INSTANCE = Mappers.getMapper(StorageOverrideBillToPartyMapper.class);
	StorageOverrideBillToPartyDTO StorageOverrideBillToPartyToStorageOverrideBillToPartyDTO(StorageOverrideBillToParty storageOverride);
	StorageOverrideBillToParty  StorageOverrideBillToPartyDTOToStorageOverrideBillToParty(StorageOverrideBillToPartyDTO storageOverrideDto);

}
