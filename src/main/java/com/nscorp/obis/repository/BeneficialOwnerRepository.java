package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.BeneficialOwner;

public interface BeneficialOwnerRepository extends JpaRepository<BeneficialOwner,Long> {
    
    List<BeneficialOwner> findAll(Specification<BeneficialOwner> specification);

    Boolean existsByBnfLongName(String longName);
    Boolean existsByBnfShortName(String shortName);
    Boolean existsByBnfCustomerId(Long bnfCustomerId);
    void deleteByBnfCustomerId(Long bnfCustomerId);

    @Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
	Long SGK();

    BeneficialOwner findByBnfCustomerId(Long bnfCustId);
}
