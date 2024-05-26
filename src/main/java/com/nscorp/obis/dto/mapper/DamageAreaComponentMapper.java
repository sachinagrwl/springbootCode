package com.nscorp.obis.dto.mapper;
import com.nscorp.obis.domain.DamageAreaComp;
import com.nscorp.obis.dto.DamageAreaComponentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface DamageAreaComponentMapper {
    DamageAreaComponentMapper INSTANCE = Mappers.getMapper(DamageAreaComponentMapper.class);

    DamageAreaComp DamageAreaComponentDTOToDamageAreaComponent(DamageAreaComponentDTO damageAreaComponentDTO);
    DamageAreaComponentDTO DamageAreaComponentToDamageAreaComponentDTO(DamageAreaComp damageAreaComponent);
}
