package com.nscorp.obis.services;

import com.nscorp.obis.dto.DamageComponentSizeDTO;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

public interface DamageComponentSizeService {

     List<DamageComponentSizeDTO> fetchDamageComponentSize(Integer jobCode, Integer aarWhyMadeCode, Long componentSizeCode);
     DamageComponentSizeDTO deleteDamageComponentSize(@Valid DamageComponentSizeDTO componentSizeDTO);
     DamageComponentSizeDTO insertDamageComponentSize(@Valid DamageComponentSizeDTO damageComponentSizeDTO, Map<String, String> headers);
     DamageComponentSizeDTO updateDamageCompSize(@Valid DamageComponentSizeDTO damageComponentSizeDTO, Map<String, String> headers);

}
