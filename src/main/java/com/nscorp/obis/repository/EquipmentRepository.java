package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.Equipment;

@Repository
public interface EquipmentRepository  extends JpaRepository <Equipment, String>{

	List<Equipment> findByEquipInitAndEquipNbr(String equipInit, Integer equipNbr);

}
