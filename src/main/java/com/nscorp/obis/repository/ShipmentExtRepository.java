package com.nscorp.obis.repository;


import com.nscorp.obis.domain.ShipmentExt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentExtRepository extends JpaRepository<ShipmentExt,Long> {
    ShipmentExt findBySvcId(Long svcId);
}
