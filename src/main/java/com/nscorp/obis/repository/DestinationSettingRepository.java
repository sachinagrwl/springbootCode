package com.nscorp.obis.repository;

import com.nscorp.obis.domain.DestinationSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

public interface DestinationSettingRepository extends JpaRepository<DestinationSetting, Double> {

    @Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
    Long SGK();

}
