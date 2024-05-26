package com.nscorp.obis.services;

import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.dto.DamageCompLocDTO;

import java.util.List;
import java.util.Map;

public interface DamageComponentLocationService {

    List<DamageCompLoc> getDamageComponentLocation(Integer jobCode, String areaCode);

    void deleteDamageComponent(DamageCompLocDTO damageComponent);

    DamageCompLocDTO addDamageCompLocation(DamageCompLocDTO damageCompLocDTO, Map<String, String> headers);

    DamageCompLocDTO updateDamageCompLocation(DamageCompLocDTO damageCompLocDTO, Map<String, String> headers);
}
