package com.nscorp.obis.repository;

import com.nscorp.obis.domain.ShiplineCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ShiplineCustomerRepository extends JpaRepository<ShiplineCustomer,String>{

	boolean existsByShiplineNumber(String shiplineNumber);

	void deleteByShiplineNumber(String shiplineNumber);

	boolean existsByShiplineNumberAndDescription(String shiplineNumber, String description);

	boolean existsByDescriptionAndCustomerId(String shiplineNumber, Long customerId);

}
