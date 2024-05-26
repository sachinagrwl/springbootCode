package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.StorageRates;

public interface StorageRatesRepository extends JpaRepository<StorageRates, Long> {

	StorageRates findByStorageId(Long rateId);
	Page<StorageRates> findAll(Specification<StorageRates> specification, Pageable pageable);

	List<StorageRates> findAll(Specification<StorageRates> specification);
	
	@Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
	Long SGK();

}
