package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.Block;
import com.nscorp.obis.dto.BlockDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BlockMapper {

	BlockMapper INSTANCE = Mappers.getMapper(BlockMapper.class);
	BlockDTO blockToBlockDTO (Block block);
	Block blockDTOToBlock (BlockDTO blockDTO);
}
