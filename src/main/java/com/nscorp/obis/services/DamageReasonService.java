package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.DamageReason;
import com.nscorp.obis.dto.DamageReasonDTO;

public interface DamageReasonService {

	DamageReasonDTO addDamageReason(@Valid DamageReasonDTO damageReasonDTO, Map<String, String> headers);

	DamageReasonDTO updateDamageReason(@Valid DamageReasonDTO damageReasonDTO, Map<String, String> headers);
	List<DamageReason> getDamageReason(Integer catCd);
	public DamageReason deleteDamageReasons(DamageReason damageReason);

}
