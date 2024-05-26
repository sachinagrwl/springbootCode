package com.nscorp.obis.repository;

import com.nscorp.obis.domain.CIFExcpView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

 @Repository
 public interface CashInFistRepository extends JpaRepository<CIFExcpView, String>{
     List<CIFExcpView> findAllByCustomerNameIsNotNullOrderByCustomerNameAsc();
}


