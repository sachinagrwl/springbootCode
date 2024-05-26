package com.nscorp.obis.services;

import com.nscorp.obis.domain.DVIRCodes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface DVIRCodesService {
    List<DVIRCodes> getAllDVIRCodes();
    List<DVIRCodes> deleteDVIRCodes(DVIRCodes dvirCodes);
    DVIRCodes addDvirCodes(@Valid DVIRCodes dvirCodes, Map<String, String> headers);
	DVIRCodes updateDvirCodes(@Valid DVIRCodes dvirCodes, Map<String, String> headers);


}
