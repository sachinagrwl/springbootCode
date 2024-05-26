package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.AARWhyMadeCodes;
import org.springframework.stereotype.Repository;

@Repository
public interface AARWhyMadeCodesRepository extends JpaRepository <AARWhyMadeCodes, Integer>  {

	AARWhyMadeCodes findByAarWhyMadeCd(Integer aarWhyMadeCd);

	boolean existsByAarWhyMadeCd(Integer aarWhyMadeCd);

}
