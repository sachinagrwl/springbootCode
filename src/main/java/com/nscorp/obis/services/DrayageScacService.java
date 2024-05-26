package com.nscorp.obis.services;

import java.util.Map;

import com.nscorp.obis.domain.DrayageScac;
import com.nscorp.obis.dto.DrayageScacDTO;
import com.nscorp.obis.response.data.PaginatedResponse;

public interface DrayageScacService {
	PaginatedResponse<DrayageScacDTO> getDrayageScac(String drayId, String carrierName, String carrierCity,
			String state,Integer pageSize,Integer pageNumber,String[] sort);
	
	DrayageScac addDrayageScac(DrayageScac drayageScac, Map<String, String> headers);
	DrayageScacDTO updateDrayageScac(DrayageScacDTO drayageScacDTO, Map<String, String> headers);
	DrayageScacDTO deleteDrayageScac(DrayageScacDTO drayageScacDTO);
}
