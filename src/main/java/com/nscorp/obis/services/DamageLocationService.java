package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.DamageLocation;
import com.nscorp.obis.dto.DamageLocationDTO;

public interface DamageLocationService {
    
    public List<DamageLocation> getAllDamageLocation();
    public void deleteDamageLocation(DamageLocation damageLocation);
    public DamageLocationDTO addDamageLocation(@Valid DamageLocationDTO damageLocationDTO, Map<String, String> headers)throws SQLException;
    public DamageLocationDTO updateDamageLocation(@Valid DamageLocationDTO damageLocationDTO, Map<String, String> headers);
}
