package com.nscorp.obis.dto.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.nscorp.obis.domain.CustomerPool;
import com.nscorp.obis.dto.CustomerPoolDTO;

@Mapper(componentModel = "spring")
public interface CustomerPoolMapper {
    CustomerPoolMapper INSTANCE = Mappers.getMapper(CustomerPoolMapper.class);
	
	CustomerPool CustomerPoolDTOToCustomerPool(CustomerPoolDTO customerPoolDTO);
	CustomerPoolDTO CustomerPoolToCustomerPoolDTO(CustomerPool customerPool);

}
