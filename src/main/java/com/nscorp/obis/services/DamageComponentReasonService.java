package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;


import com.nscorp.obis.dto.DamageComponentReasonDTO;

public interface DamageComponentReasonService {

	List<DamageComponentReasonDTO> getDamageComponentReasons(Integer jobCode, Integer aarWhyMadeCode, String orderCode,
			String sizeRequired);
	
	DamageComponentReasonDTO deleteDamageComponentReason(DamageComponentReasonDTO componentReasonDTO);

	DamageComponentReasonDTO createDamageComponentReason(DamageComponentReasonDTO damageComponentReasonDTO,
			Map<String, String> headers);
	
	DamageComponentReasonDTO updateDamageComponentReason(DamageComponentReasonDTO damageComponentReasonDTO,
			Map<String, String> headers);

}
