package com.nscorp.obis.repository;


import com.nscorp.obis.domain.ShipVessel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipVesselRepository extends JpaRepository<ShipVessel,Long>  {

    ShipVessel findBySvcIdAndVesselDirCd(Long svcId, String vesselDirCd);
}
