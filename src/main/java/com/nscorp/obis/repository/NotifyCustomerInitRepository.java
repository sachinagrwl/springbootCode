package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.NotifyCustomerInit;
import com.nscorp.obis.domain.NotifyCustomerInitPrimaryKeys;

public interface NotifyCustomerInitRepository extends JpaRepository<NotifyCustomerInit, NotifyCustomerInitPrimaryKeys> {

	List<NotifyCustomerInit> findByCustIdOrderByEqInitAsc(Long custId);

	boolean existsByEqInit(String eqInit);

	boolean existsByEqInitAndCustIdAndUversion(String eqInit, Long custId, String uversion);

	void deleteByEqInitAndCustId(String eqInit, Long custId);

	List<NotifyCustomerInit> findByCustId(Long custId);

	NotifyCustomerInit findByEqInit(String eqInit);

	List<NotifyCustomerInit> findAllByOrderByEqInitAsc();

	NotifyCustomerInit findByEqInitAndCustId(String eqInit, Long custId);

	boolean existsByEqInitAndCustId(String eqInit, Long custId);
}
