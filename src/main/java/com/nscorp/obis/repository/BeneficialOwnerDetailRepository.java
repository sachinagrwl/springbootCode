package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.BeneficialOwnerDetail;
import com.nscorp.obis.domain.BeneficialOwnerDetailPrimarykey;

import java.util.List;

public interface BeneficialOwnerDetailRepository
		extends JpaRepository<BeneficialOwnerDetail, BeneficialOwnerDetailPrimarykey> {
	Boolean existsBybnfOwnerNumber(String bnfOwnerNumber);


	List<BeneficialOwnerDetail> findByBnfCustId(Long bnfCustomerId);


	boolean existsByBnfCustId(Long bnfCustomerId);

	boolean existsByBnfOwnerNumber(String bnfOwnerNumber);

	BeneficialOwnerDetail findByBnfOwnerNumberAndBnfCustId(String bnfOwnerNumber, Long bnfCustomerId);

	void deleteByBnfOwnerNumberAndBnfCustId(String bnfOwnerNumber, Long bnfCustomerId);
	List<BeneficialOwnerDetail> findAll(Specification<BeneficialOwnerDetail> specification);
}
