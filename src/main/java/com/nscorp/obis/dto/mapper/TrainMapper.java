package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.Train;
import com.nscorp.obis.dto.TrainDTO;


@Mapper(componentModel = "spring")
public interface TrainMapper {

	TrainMapper INSTANCE = Mappers.getMapper(TrainMapper.class);

	TrainDTO trainToTrainDTO(Train train);

	Train trainDTOToTrain(TrainDTO trainDTO);

}
