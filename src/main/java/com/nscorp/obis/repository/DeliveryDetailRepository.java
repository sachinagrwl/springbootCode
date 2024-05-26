package com.nscorp.obis.repository;

import com.nscorp.obis.domain.DeliveryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, Double> {

}
