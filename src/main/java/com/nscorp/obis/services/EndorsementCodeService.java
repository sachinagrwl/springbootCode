package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.EndorsementCode;

public interface EndorsementCodeService {

	List<EndorsementCode> getAllTables(String endorsementCd, String endorseCdDesc);

	EndorsementCode updateEndorsementCode(EndorsementCode endorsementCode, Map<String,String> headers);

	EndorsementCode addEndorsementCode(EndorsementCode endorsementCode, Map<String,String> headers);

	void deleteEndorsementCode(EndorsementCode endorsementCodeDTOToEndorsementCode);

}
