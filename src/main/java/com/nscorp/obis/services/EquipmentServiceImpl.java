package com.nscorp.obis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.Equipment;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.EquipmentRepository;


@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
	
	@Autowired(required = true)
	EquipmentRepository equiRepo;

	public List<Equipment> getAllEquiList(String equipInit, Integer equipNbr) {
		List<Equipment> equiList = equiRepo.findByEquipInitAndEquipNbr(equipInit, equipNbr);
		if (equiList.isEmpty()) {
			throw new NoRecordsFoundException("No records found");
		}
		return equiList;
	}
	

}
