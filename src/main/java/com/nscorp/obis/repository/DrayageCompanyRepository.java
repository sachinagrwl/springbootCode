package com.nscorp.obis.repository;

import java.util.List;

import com.nscorp.obis.domain.ContainerChassisAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.DrayageCompany;
import com.nscorp.obis.domain.DrayageCustomer;
import com.nscorp.obis.domain.DrayageCustomerPrimaryKeys;
import org.springframework.data.jpa.repository.Query;

public interface DrayageCompanyRepository extends JpaRepository<DrayageCompany, Long> {

	List<DrayageCompany> findByDrayageId(String drayageId);

	@Query("SELECT drayageCompany from DrayageCompany drayageCompany where drayageCompany.drayageId = :drayageId")
	DrayageCompany getByDrayageId(String drayageId);

    boolean existsByDrayageId(String drayId);
}
