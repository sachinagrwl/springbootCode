package com.nscorp.obis.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.HoldOrders;

@Repository
public interface HoldOrdersRepository extends JpaRepository<HoldOrders, Long>{

	List<HoldOrders> findByEquipmentInitAndEquipmentNbrAndEquipmentType(String carInit, BigDecimal carNbr,
			String carEquipType);

}
