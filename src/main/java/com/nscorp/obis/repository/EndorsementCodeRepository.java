package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.EndorsementCode;

@Repository
public interface EndorsementCodeRepository extends JpaRepository <EndorsementCode,String>  {
	
	@Query(value = "Select rv from EndorsementCode rv where (rv.endorsementCd=:endorsementCd) or  : endorsementCd is null " +
			"AND ((rv.endorseCdDesc =:endorseCdDesc) or :endorseCdDesc is null)")
	List<EndorsementCode> searchAll(String endorsementCd, String endorseCdDesc);

	EndorsementCode findByEndorsementCd(String endorsementCd);
	

}
