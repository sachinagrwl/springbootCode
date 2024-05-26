package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.UnCd;

public interface UnCdService {
	List<UnCd> getAllTables(String unCd);
    UnCd deleteUnCode(UnCd unCd);
    UnCd updateUnDesc(UnCd unCd, Map<String, String> headers);
	UnCd addUnCode(UnCd unCdObj, Map<String, String> headers);
}

