package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.PoolEquipmentController;

public interface PoolEquipmentControllerService {

	List<PoolEquipmentController> getAllPool();

	PoolEquipmentController insertPoolCtrl(PoolEquipmentController poolCtrl, Map<String, String> headers);

	PoolEquipmentController updatePoolCtrl(PoolEquipmentController poolCtrl, Map<String, String> headers);

	PoolEquipmentController deletePoolCtrl(PoolEquipmentController poolCtrl);

}
