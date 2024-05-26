package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.DamageArea;
import org.springframework.transaction.annotation.Transactional;


public interface DamageAreaRepository  extends JpaRepository <DamageArea, String> {
   @Transactional
   void deleteById(String areaCd);

   boolean existsByAreaCd(String areaCd);

   DamageArea findByAreaCd(String areaCd);
}
