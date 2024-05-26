package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.Shipment;


@Repository
public interface ShipmentRepository extends JpaRepository <Shipment, Long>{

	boolean existsBySvcId(Long svcId);
	
	boolean existsByOnlDest(Long onlDest);

	Shipment findBySvcId(Long svcId);
}
