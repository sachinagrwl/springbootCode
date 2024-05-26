package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nscorp.obis.domain.InterChangeParty;

public interface InterChangePartyRepository extends JpaRepository<InterChangeParty, String> {
	
	Boolean existsByIchgCode(String ichgCode);
	
	InterChangeParty findByIchgCode(String ichgCode);

	//List<InterChangeParty> findAll(String ichgCode);

	//List<InterChangeParty> findByIchgCode(String ichgCode);

	@Query("select icp from InterChangeParty icp where icp.ichgCode = :ichgCode or :ichgCode is null")
	List<InterChangeParty> getByIchgCode(String ichgCode);

	boolean existsById(String ichgCode);

	void deleteById(String ichgCode);


}
