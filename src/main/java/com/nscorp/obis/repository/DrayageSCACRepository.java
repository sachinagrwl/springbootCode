package com.nscorp.obis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.DrayageScac;

public interface DrayageSCACRepository extends JpaRepository<DrayageScac, String> {

	Page<DrayageScac> findAll(Specification<DrayageScac> specification, Pageable pageable);

	DrayageScac getByDrayId(String drayId);

}
