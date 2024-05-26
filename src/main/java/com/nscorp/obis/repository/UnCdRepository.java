package com.nscorp.obis.repository;

import com.nscorp.obis.domain.UnCd;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnCdRepository extends JpaRepository<UnCd,String> {
    boolean existsByUnCd(String unCd);
    @Query(value = "Select rv from UnCd rv where (rv.unCd=:unCd) or  : unCd is null " )
	List<UnCd> searchAll(String unCd);

    boolean existsByUnCdAndUversion(String unCd, String uVersion);
    UnCd findByUnCdAndUversion(String unCd, String uversion);

    UnCd findByUnCd(String unCd);
}
