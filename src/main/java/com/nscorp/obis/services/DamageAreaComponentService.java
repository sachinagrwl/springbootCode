package com.nscorp.obis.services;

import com.nscorp.obis.dto.DamageAreaComponentDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

public interface DamageAreaComponentService {
    List<DamageAreaComponentDTO> fetchDamageComponentSize(Integer jobCode, String areaCode);

    void deleteDamageAreaComponent(DamageAreaComponentDTO damageComponentDTOObj);

    public DamageAreaComponentDTO addDamageAreaComponent(
            @Valid DamageAreaComponentDTO damageAreaComponentDTO, Map<String, String> headers) throws SQLException;

    public DamageAreaComponentDTO updateDamageAreaComponent(
            @Valid DamageAreaComponentDTO damageAreaComponentDTO, Map<String, String> headers) throws SQLException;

}
