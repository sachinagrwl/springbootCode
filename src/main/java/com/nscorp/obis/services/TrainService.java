package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.TrainDTO;

public interface TrainService {
	List<TrainDTO> fetchTrainDetails(String trainNumber, String trainDesc);
	TrainDTO addTrain(TrainDTO trainDTO,Map<String, String> headers);
	TrainDTO updateTrain(TrainDTO trainDTO,Map<String, String> headers);
	TrainDTO deleteTrain(TrainDTO trainDTO);
}
