package com.nscorp.obis.repository;

import com.nscorp.obis.domain.EMSIngateRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EMSIngateRestrictionRepository extends JpaRepository<EMSIngateRestriction, Long> {


    List<EMSIngateRestriction> findAll();

    EMSIngateRestriction findByRestrictId(Long restrictId);

    @Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
    Long SGK();

	boolean existsByRestrictId(Long restrictId);

}
