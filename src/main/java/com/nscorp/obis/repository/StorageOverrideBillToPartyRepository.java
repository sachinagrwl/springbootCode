package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.StorageOverrideBillToParty;

@Repository
public interface StorageOverrideBillToPartyRepository extends JpaRepository<StorageOverrideBillToParty, Long> {

	boolean existsByCorporateCustomerIdAndUversion(Long corporateCustomerId, String uversion);

}
