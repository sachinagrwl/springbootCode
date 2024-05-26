package com.nscorp.obis.repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.DamageCodeConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DamageCodeConversionRepository extends JpaRepository<DamageCodeConversion, Integer>, CommonKeyGenerator {

    List<DamageCodeConversion> findAllByOrderByCatCd();

    DamageCodeConversion findByCatCdAndReasonCd(Integer catCd, String reasonCd);

    DamageCodeConversion findByCatCdAndReasonCdAndUversion(Integer catCd, String reasonCd, String uVersion);

    boolean existsByCatCdAndReasonCdAndUversion(Integer catCd, String reasonCd, String uVersion);

	boolean existsByCatCdAndReasonCd(Integer catCd, String reasonCd);

	boolean existsByAarJobCdAndAarWhyMadeCode(Integer aarJobCode, Integer aarWhyMadeCode);


}
