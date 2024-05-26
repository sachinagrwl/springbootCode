package com.nscorp.obis.repository;

import com.nscorp.obis.domain.DamageComponentSize;
import com.nscorp.obis.domain.DamageComponentSizePrimaryKey;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DamageCompSizeRepository extends JpaRepository<DamageComponentSize, DamageComponentSizePrimaryKey> {

    List<DamageComponentSize> findAll(Specification<DamageComponentSize> specification);
    
    Boolean existsByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(Integer jobCode, Integer aarWhyMadeCode, Long componentSizeCode);
    
    Boolean existsByJobCodeAndAarWhyMadeCodeAndOrderCode(Integer jobCode, Integer aarWhyMadeCode, String orderCode);

    DamageComponentSize findByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(Integer jobCode, Integer aarWhyMadeCode, Long componentSizeCode);
    @Transactional
	@Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
	Long SGK();
}
