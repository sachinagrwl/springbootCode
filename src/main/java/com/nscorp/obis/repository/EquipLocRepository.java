package com.nscorp.obis.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.EquipLoc;
import com.nscorp.obis.domain.EquipLocPrimaryKey;

public interface EquipLocRepository extends JpaRepository<EquipLoc, EquipLocPrimaryKey>{

	

	EquipLoc findByEquipInitAndEquipNbrAndEquipTpAndEquipId(String equipInit,BigDecimal equipNbr,String equipTp,String equipId);
}
