package com.nscorp.obis.services;

import com.nscorp.obis.domain.MergeStationTermHandle;
import com.nscorp.obis.domain.StationTermHandle;
import com.nscorp.obis.dto.MergeStationTermHandleDTO;
import com.nscorp.obis.dto.StationTermHandleDTO;
import com.nscorp.obis.dto.mapper.MergeStationTermHandleMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.MergeStationTermHandleRepository;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.TerminalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MergeStationTermHandleServiceImpl implements MergeStationTermHandleService {

	@Autowired
	MergeStationTermHandleRepository repository;

	@Autowired
	TerminalRepository terminalRepository;

	@Autowired
	StationRepository stationRepo;

	@Autowired
	MergeStationTermHandleMapper mapper;


	@Override
	public List<MergeStationTermHandleDTO> getMergeStationTermHandleDetails(long handleTermId) {
		log.info("getMergeStationTermHandleDetails : Method starts");
		if (!terminalRepository.existsByTerminalId(handleTermId)) {
			throw new NoRecordsFoundException("No Terminal Found with this handle terminal id : " + handleTermId);
		}
		List<MergeStationTermHandle> handles = repository.findByHandleTermId(handleTermId);
		if (handles.isEmpty()) {
			throw new NoRecordsFoundException("No records found for given terminal id");
		}
		List<MergeStationTermHandleDTO> dtos = handles.stream().map(mapper::mergeStationTermHandleToMergeStationTermHandleDTO)
				.collect(Collectors.toList());
		log.info("getMergeStationTermHandleDetails : Method ends");
		return dtos;
	}

	@Override
	public MergeStationTermHandleDTO deleteMergeStationTermHandle(MergeStationTermHandleDTO mergeStationTermHandleDTO) {
		log.info("deleteMergeStationTermHandle : Method starts");
		Long handleTermId = mergeStationTermHandleDTO.getHandleTermId();
		Long stationId = mergeStationTermHandleDTO.getStationId();
		if (!repository.existsByHandleTermIdAndStationId(handleTermId, stationId)) {
			throw new NoRecordsFoundException("No records found with given combination");
		}
		MergeStationTermHandle mergeStationTermHandle = repository.findByHandleTermIdAndStationId(handleTermId, stationId);
		mergeStationTermHandle.setUversion(mergeStationTermHandleDTO.getUversion());
		repository.delete(mergeStationTermHandle);
		mergeStationTermHandleDTO = mapper.mergeStationTermHandleToMergeStationTermHandleDTO(mergeStationTermHandle);
		log.info("deleteMergeStationTermHandle : Method ends");
		return mergeStationTermHandleDTO;
	}

	@Override
	public MergeStationTermHandleDTO insertMergeStationTermHandle(MergeStationTermHandleDTO mergeStationTermHandleDTO, Map<String, String> headers) {
		log.info("insertMergeStationTermHandle : Method starts");
		Long handleTermId = mergeStationTermHandleDTO.getHandleTermId();
		Long stationId = mergeStationTermHandleDTO.getStationId();
		if (!terminalRepository.existsByTerminalId(handleTermId)) {
			throw new NoRecordsFoundException("No terminal found with this handle terminal id : " + handleTermId);
		}
		if (stationRepo.findByTermId(stationId) == null) {
			throw new NoRecordsFoundException("No station found with this station id : " + stationId);
		}
		if (repository.existsByHandleTermIdAndStationId(handleTermId, stationId)) {
			throw new RecordAlreadyExistsException("Record already exist with given handle terminal id and station id");
		}
		MergeStationTermHandle mergeStationTermHandle = mapper.mergeStationTermHandleDTOToMergeStationTermHandle(mergeStationTermHandleDTO);
		mergeStationTermHandle.setUversion("!");
		mergeStationTermHandle.setCreateUserId(headers.get("userid"));
		mergeStationTermHandle.setUpdateUserId(headers.get("userid"));
		mergeStationTermHandle.setUpdateExtensionSchema(headers.get("extensionschema"));
		if (mergeStationTermHandle.getUpdateExtensionSchema() == null) {
			mergeStationTermHandle.setUpdateExtensionSchema("IMS02713");
		}
		mergeStationTermHandle = repository.save(mergeStationTermHandle);
		mergeStationTermHandleDTO = mapper.mergeStationTermHandleToMergeStationTermHandleDTO(mergeStationTermHandle);
		log.info("insertMergeStationTermHandle : Method ends");
		return mergeStationTermHandleDTO;
	}
}
