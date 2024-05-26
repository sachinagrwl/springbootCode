package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.PoolTerminal;
import com.nscorp.obis.dto.PoolTerminalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PoolTerminalMapper {

    PoolTerminalMapper INSTANCE = Mappers.getMapper(PoolTerminalMapper.class);
    PoolTerminalDTO poolTerminalToPoolTerminalDto(PoolTerminal poolTerminal);
    PoolTerminal poolTerminalDtoToPoolTerminal(PoolTerminalDTO poolTerminalDTO);
}
