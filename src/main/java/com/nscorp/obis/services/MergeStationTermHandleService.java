package com.nscorp.obis.services;

import com.nscorp.obis.dto.MergeStationTermHandleDTO;

import java.util.List;
import java.util.Map;

public interface MergeStationTermHandleService {

	List<MergeStationTermHandleDTO> getMergeStationTermHandleDetails(long handleTermId);

	MergeStationTermHandleDTO deleteMergeStationTermHandle(MergeStationTermHandleDTO mergeStationTermHandleDTO);

	MergeStationTermHandleDTO insertMergeStationTermHandle(MergeStationTermHandleDTO mergeStationTermHandleDTO, Map<String, String> headers);

}
