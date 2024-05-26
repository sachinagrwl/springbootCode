package com.nscorp.obis.repository;

import com.nscorp.obis.domain.NotifyCustomerInitView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotifyCustomerInitViewRepository extends JpaRepository<NotifyCustomerInitView, Long> {
    List<NotifyCustomerInitView> findAllByOrderByCustomerNameAsc();
}
