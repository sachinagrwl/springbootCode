package com.nscorp.obis.repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.Ports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortsRepository extends JpaRepository <Ports, Long>, CommonKeyGenerator {
    boolean existsByPortIdAndUversion(Long portId, String uversion);

    Ports findByPortId(Long portId);
}
