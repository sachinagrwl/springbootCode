package com.nscorp.obis.repository;

import com.nscorp.obis.domain.DVIRCodes;
import com.nscorp.obis.domain.HazRestriction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DVIRCodesRepository extends JpaRepository<DVIRCodes, String> {

    boolean existsByDvirCd(String dvirCd);

    Optional<DVIRCodes> findById(String dvirCd);

    void deleteByDvirCd(String dvirCd);

	boolean existsByDvirCdAndUversion(String dvirCd, String uversion);

	List<DVIRCodes> findByDvirCd(String dvirCd);
    
}
