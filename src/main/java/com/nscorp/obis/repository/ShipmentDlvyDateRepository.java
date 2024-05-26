package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.ShipmentDlvyDate;

public interface ShipmentDlvyDateRepository  extends JpaRepository<ShipmentDlvyDate, String>{

	ShipmentDlvyDate findBySvcId(Long svcId);
	

}
