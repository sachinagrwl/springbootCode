package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.AARHitch;
import com.nscorp.obis.domain.CIFExcpView;
import com.nscorp.obis.dto.AARHitchDTO;
import com.nscorp.obis.dto.CIFExcpViewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CashInFistMapper {
    CashInFistMapper INSTANCE = Mappers.getMapper(CashInFistMapper.class);
    CIFExcpViewDTO CIFExcpViewToCIFExcpViewDTO(CIFExcpView cifExcpView);
    CIFExcpView CIFExcpViewDTOToCIFExcpView(CIFExcpViewDTO cifExcpViewDTO);
}
