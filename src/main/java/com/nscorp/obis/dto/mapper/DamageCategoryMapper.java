package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DamageCategory;
import com.nscorp.obis.dto.DamageCategoryDTO;

@Mapper(componentModel = "spring")
public interface DamageCategoryMapper {

    DamageCategoryMapper INSTANCE = Mappers.getMapper(DamageCategoryMapper.class);

    DamageCategoryDTO damageCategoryToDamageCategoryDTO(DamageCategory damageCategory);

    DamageCategory damageCategoryDtoToDamageCategory(DamageCategoryDTO damageCategoryDto);

}