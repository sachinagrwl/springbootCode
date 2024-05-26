package com.nscorp.obis.repository;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.CustomerNotifyProfile;
import com.nscorp.obis.domain.DayOfWeek;


@Repository
public interface CustomerNotifyProfileRepository extends JpaRepository<CustomerNotifyProfile,Long>{


	List<CustomerNotifyProfile> findByCustomer_CustomerId(@Valid Long customerId);

	CustomerNotifyProfile findByNotifyProfileId(@Valid Long notifyProfileId);
	
	void deleteByNotifyProfileId(long notifyProfileId);
	
	boolean existsByNotifyProfileId(@NotNull(message = "Profile Id should not be Null.") Long notifyProfileId);

	List<CustomerNotifyProfile> findByCustomer_CustomerIdAndEventCodeAndNotifyTerminalIdOrderByNotifyTerminalIdDesc(
			long customerId, String evtCd, Long terminalId);
	
}