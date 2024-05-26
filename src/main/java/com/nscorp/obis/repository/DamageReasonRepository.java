package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.DamageReason;
import com.nscorp.obis.domain.DamageReasonPrimaryKey;

import java.util.List;

public interface DamageReasonRepository extends JpaRepository<DamageReason, DamageReasonPrimaryKey> {

	Boolean existsByCatCdAndReasonCd(Integer catId, String reasonCd);

	DamageReason findByCatCdAndReasonCd(Integer catId, String reasonCd);
	List<DamageReason> findByCatCd (Integer catCd);
	//List<DamageReason> findAll(Specification<DamageReason> specification, Sort sort);
	boolean existsByCatCd(Integer catCd);
	void deleteByCatCd(Integer catCd);
}
