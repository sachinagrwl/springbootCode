package com.nscorp.obis.services;

import com.nscorp.obis.domain.AARDamage;
import com.nscorp.obis.domain.AARHitch;

import java.util.List;

public interface AARDamageService {
    List<AARDamage> getAllAarDamageCodes(String aarDamage);
}
