package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nscorp.obis.domain.DrayageCustomerInfo;
import com.nscorp.obis.domain.DrayageCustomerInfoPrimaryKey;


public interface DrayageCustomerInfoRepository extends JpaRepository<DrayageCustomerInfo, DrayageCustomerInfoPrimaryKey> {
	List<DrayageCustomerInfo> findByCustomerIdAndDrayageId(Long customerId, String drayageId);
	boolean existsByCustomerId(Long customerId);
	DrayageCustomerInfo findByCustomerId(Long customerId);
}
