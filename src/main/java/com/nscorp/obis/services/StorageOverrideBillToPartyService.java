package com.nscorp.obis.services;

import java.util.Map;

import com.nscorp.obis.domain.StorageOverrideBillToParty;

public interface StorageOverrideBillToPartyService {

	StorageOverrideBillToParty updateOverrideBillToParty(StorageOverrideBillToParty storageOverrideBillToParty,
			Map<String, String> headers);

}
