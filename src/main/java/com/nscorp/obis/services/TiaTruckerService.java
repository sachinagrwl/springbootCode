package com.nscorp.obis.services;

import java.util.List;

import com.nscorp.obis.domain.TiaTrucker;
import com.nscorp.obis.domain.Trucker;

public interface TiaTruckerService {
	public List<Trucker> getTruckerNameByTruckerCode(String truckerCode);
}
