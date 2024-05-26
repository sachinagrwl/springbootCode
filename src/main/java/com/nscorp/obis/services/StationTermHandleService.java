package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.StationTermHandleDTO;

public interface StationTermHandleService {

	List<StationTermHandleDTO> getTermHandleDetails(long handleTermId);

	StationTermHandleDTO deleteStationTermHandle(StationTermHandleDTO DTO);

	StationTermHandleDTO insertStationTermHandle(StationTermHandleDTO DTO, Map<String, String> headers);

}
