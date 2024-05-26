package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Train;
import com.nscorp.obis.dto.TrainDTO;
import com.nscorp.obis.dto.mapper.TrainMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.TerminalTrainRepository;
import com.nscorp.obis.repository.TrainRepository;
import com.nscorp.obis.repository.TrainScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainServiceImpl implements TrainService {

	@Autowired
	TrainRepository trainRepo;

	@Autowired
	TerminalTrainRepository terminalTrainRepo;

	@Autowired
	TrainScheduleRepository trainScheduleRepo;

	@Autowired
	SpecificationGenerator specificationGenerator;

	@Autowired
	TrainMapper mapper;

	@Override
	public List<TrainDTO> fetchTrainDetails(String trainNumber, String trainDesc) {
		log.info("fetchTrainDetails : Method Starts");
		List<Train> trains = trainRepo.findAll(specificationGenerator.trainSpecification(trainNumber, trainDesc),Sort.by("trainNumber"));
		if (trains.isEmpty()) {
			throw new NoRecordsFoundException("No records found for given combination");
		}
		List<TrainDTO> trainDTOs = trains.stream().map(entity -> mapper.trainToTrainDTO(entity))
				.collect(Collectors.toList());
		log.info("fetchTrainDetails : Method Ends");
		return trainDTOs;
	}

	@Override
	public TrainDTO addTrain(TrainDTO trainDTO, Map<String, String> headers) {
		log.info("addTrain : Method Starts");
		UserId.headerUserID(headers);
		if (trainRepo.existsById(trainDTO.getTrainNumber())) {
			throw new RecordAlreadyExistsException(
					"Record already exists with given train number " + trainDTO.getTrainNumber());
		}
		trainDTO.setUversion("!");
		trainDTO.setCreateUserId(headers.get(CommonConstants.USER_ID));
		trainDTO.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		trainDTO.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if (trainDTO.getUpdateExtensionSchema() == null) {
			trainDTO.setUpdateExtensionSchema("IMS02654");
		}
		Train train = mapper.trainDTOToTrain(trainDTO);
		train = trainRepo.save(train);
		trainDTO = mapper.trainToTrainDTO(train);
		log.info("addTrain : Method Ends");
		return trainDTO;
	}

	@Override
	public TrainDTO updateTrain(TrainDTO trainDTO, Map<String, String> headers) {
		log.info("updateTrain : Method Starts");
		UserId.headerUserID(headers);
		if (!trainRepo.existsById(trainDTO.getTrainNumber())) {
			throw new NoRecordsFoundException("No records found for given train number");
		}
		Train train = trainRepo.findById(trainDTO.getTrainNumber()).get();
		train.setUversion(trainDTO.getUversion());
		train.setTrainDesc(trainDTO.getTrainDesc());
		train.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		train.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		train = trainRepo.save(train);
		trainDTO = mapper.trainToTrainDTO(train);
		log.info("updateTrain : Method Ends");
		return trainDTO;
	}

	@Override
	public TrainDTO deleteTrain(TrainDTO trainDTO) {
		log.info("deleteTrain : Method Starts");
		Train train = trainRepo.findById(trainDTO.getTrainNumber())
				.orElseThrow(() -> new NoRecordsFoundException("No records found for given train number"));
		if (terminalTrainRepo.existsByTrainNr(trainDTO.getTrainNumber())
				|| trainScheduleRepo.existsByTrainNumber(trainDTO.getTrainNumber())) {
			throw new RecordNotDeletedException(
					"Delete Operation of this record is restricted due to presence of links with terminal train/ train schedule");

		}
		trainRepo.delete(train);
		trainDTO = mapper.trainToTrainDTO(train);
		log.info("deleteTrain : Method Ends");
		return trainDTO;
	}

}
