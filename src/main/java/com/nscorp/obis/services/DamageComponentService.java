package com.nscorp.obis.services;

import com.nscorp.obis.domain.DamageComponent;

import java.util.List;
import java.util.Map;

public interface DamageComponentService {

    List<DamageComponent> getAllDamageComponents();

    DamageComponent getDamageComponentsByJobCode(Integer damageEntity);

    void deleteDamageComponent(DamageComponent damageComponent);

    DamageComponent insertDamageComponent(DamageComponent damageComponent, Map<String, String> headers);

    DamageComponent UpdateDamageComponent(DamageComponent damageComponent, Map<String, String> headers);
}
