package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.domain.StationTermHandle;
import com.nscorp.obis.dto.StationTermHandleDTO;
import com.nscorp.obis.dto.mapper.StationTermHandleMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.StationTermHandleRepository;
import com.nscorp.obis.repository.TerminalRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StationTermHandleServiceImpl implements StationTermHandleService {

	@Autowired
	StationTermHandleRepository repository;

	@Autowired
	TerminalRepository terminalRepository;

	@Autowired
	StationRepository stationRepo;

	@Autowired
	StationTermHandleMapper mapper;

	@Override
	public List<StationTermHandleDTO> getTermHandleDetails(long handleTermId) {
		log.info("getTermHandleDetails : Method starts");
		if (!terminalRepository.existsByTerminalId(handleTermId)) {
			throw new NoRecordsFoundException("No Terminal Found with this handle terminal id : " + handleTermId);
		}
		List<StationTermHandle> handles = repository.findByHandleTermId(handleTermId);
		if (handles.isEmpty()) {
			throw new NoRecordsFoundException("No records found for given terminal id");
		}
		List<StationTermHandleDTO> dtos = handles.stream().map(mapper::stationTermHandleToStationTermHandleDTO)
				.collect(Collectors.toList());
		log.info("getTermHandleDetails : Method ends");
		return dtos;
	}

	@Override
	public StationTermHandleDTO deleteStationTermHandle(StationTermHandleDTO DTO) {
		log.info("deleteStationTermHandle : Method starts");
		Long handleTermId = DTO.getHandleTermId();
		Long stationId = DTO.getStationId();
		if (!repository.existsByHandleTermIdAndStationId(handleTermId, stationId)) {
			throw new NoRecordsFoundException("No records found with given combination");
		}
		StationTermHandle stationTermHandle = repository.findByHandleTermIdAndStationId(handleTermId, stationId);
		stationTermHandle.setUversion(DTO.getUversion());
		repository.delete(stationTermHandle);
		DTO = mapper.stationTermHandleToStationTermHandleDTO(stationTermHandle);
		log.info("deleteStationTermHandle : Method ends");
		return DTO;
	}

	@Override
	public StationTermHandleDTO insertStationTermHandle(StationTermHandleDTO DTO, Map<String, String> headers) {
		log.info("insertStationTermHandle : Method starts");
		Long handleTermId = DTO.getHandleTermId();
		Long stationId = DTO.getStationId();
		if (!terminalRepository.existsByTerminalId(handleTermId)) {
			throw new NoRecordsFoundException("No terminal found with this handle terminal id : " + handleTermId);
		}
		if (stationRepo.findByTermId(stationId) == null) {
			throw new NoRecordsFoundException("No station found with this station id : " + stationId);
		}
		if (repository.existsByHandleTermIdAndStationId(handleTermId, stationId)) {
			throw new RecordAlreadyExistsException("Record already exist with given handle terminal id and station id");
		}
		StationTermHandle stationTermHandle = mapper.stationTermHandleDTOToStationTermHandle(DTO);
		stationTermHandle.setUversion("!");
		stationTermHandle.setCreateUserId(headers.get("userid"));
		stationTermHandle.setUpdateUserId(headers.get("userid"));
		stationTermHandle.setUpdateExtensionSchema(headers.get("extensionschema"));
		if (stationTermHandle.getUpdateExtensionSchema() == null) {
			stationTermHandle.setUpdateExtensionSchema("IMS02710");
		}
		stationTermHandle = repository.save(stationTermHandle);
		DTO = mapper.stationTermHandleToStationTermHandleDTO(stationTermHandle);
		log.info("insertStationTermHandle : Method ends");
		return DTO;
	}

}
