package com.nscorp.obis.services;

import com.nscorp.obis.domain.EquipmentAAR600Cont;

import java.util.List;
import java.util.Map;

public interface EquipmentAAR600ContService {

    List<EquipmentAAR600Cont> getAllCont();

	EquipmentAAR600Cont addEqCont(EquipmentAAR600Cont eqCont, Map<String, String> headers);

    void deleteEqCont(EquipmentAAR600Cont eqCont);
}
