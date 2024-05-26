package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.GuaranteeCustCrossRef;

public interface GuaranteeCustCrossRefService {

	List<GuaranteeCustCrossRef> getAllGuaranteeCustCrossRef(String customerName, String customerNumber,
			String terminalName);

	GuaranteeCustCrossRef addGuaranteeCustCrossRef(GuaranteeCustCrossRef guaranteeCustCrossRef,
			Map<String, String> headers);

	GuaranteeCustCrossRef updateGuaranteeCustCrossRef(GuaranteeCustCrossRef guaranteeCustCrossRef,
			Map<String, String> headers);

	GuaranteeCustCrossRef deleteGuaranteeCustCrossRef(GuaranteeCustCrossRef guaranteeCustCrossRef,
			Map<String, String> headers);

}
