package com.nscorp.obis.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.HazRestriction;

@Repository
public interface HazRestrictionRepository extends JpaRepository<HazRestriction,String> {
	@Query(value = "SELECT haz from HazRestriction haz " +
			"Where (haz.unCd=: unCd or :unCd is null)"+
			"Order By haz.unCd ASC"
	)
	List<HazRestriction> findAll(String unCd);

	boolean existsByUnCd(String unCd);
	HazRestriction findByUnCd(String unCd);

}
