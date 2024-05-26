package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.nscorp.obis.domain.DrayageCompany;
import com.nscorp.obis.dto.DrayageCompanyDTO;

@Mapper(componentModel = "spring")
public interface DrayageCompanyMapper {
	DrayageCompanyMapper INSTANCE = Mappers.getMapper(DrayageCompanyMapper.class);
	DrayageCompanyDTO drayageCompanyToDrayageCompanyDTO(DrayageCompany drayageCompany);
	DrayageCompany drayageCompanyDtoToDrayageCompany(DrayageCompanyDTO drayageCompanyDto);
}
