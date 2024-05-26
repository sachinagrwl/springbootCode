package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.TempEVT;

@Repository
public interface TempEVTRepository extends JpaRepository <TempEVT, Long>{

	@Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
    Long SGK();

	boolean existsBySvcId(Long svcId);
}
