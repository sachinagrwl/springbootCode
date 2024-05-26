package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.StackWellLength;
import com.nscorp.obis.domain.StackWellLengthPrimaryKey;

public interface StackWellLengthRepository extends JpaRepository<StackWellLength, StackWellLengthPrimaryKey> {

	void deleteByUmlerId(Long umlerId);

	boolean existsByUmlerId(Long umlerId);

	boolean existsByUmlerIdAndAar1stNr(Long umlerId, String aar1stNr);

	StackWellLength findByUmlerIdAndAar1stNr(Long umlerId, String aar1stNr);
}
