package com.nscorp.obis.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nscorp.obis.domain.EquipmentCustomerLesseeRange;

public interface EquipmentCustomerLesseeRangeService {

	Page<EquipmentCustomerLesseeRange> getEquipLessee(String equipmentInit, String corporateLongName,
			Pageable pageable);

	EquipmentCustomerLesseeRange deleteEquipLesseeRange(
			EquipmentCustomerLesseeRange equipmentCustomerLesseeRangeDTOToEquipmentCustomerLesseeRange);

	List<String> getAllEquipmentInit();

	List<String> getAllCorporateLongName();

}
