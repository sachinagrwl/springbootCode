package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.NotifyProfileMethod;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface NotifyProfileMethodMapper {

	
	NotifyProfileMethodMapper INSTANCE = Mappers.getMapper(NotifyProfileMethodMapper.class);

	NotifyProfileMethodDTO notifyProfileMethodToNotifyProfileMethodDTO(NotifyProfileMethod notifyProfileMethods);

	NotifyProfileMethod notifyProfileMethodDTOToNotifyProfileMethod(NotifyProfileMethodDTO notifyProfileMethodsDTO);
	
}