package com.nscorp.obis.repository;

import com.nscorp.obis.domain.ShipEntity;
import com.nscorp.obis.domain.ShipEntityPrimary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipEntityRepo extends JpaRepository<ShipEntity, ShipEntityPrimary> {
//    @Query(value="SELECT COUNT(s.svcId) FROM ShipEntity s WHERE s.svcId =:svcId AND s.segType = 'BN'")
//    int getCount(Long svcId);
    List<ShipEntity> findAllBySegTypeAndSvcId(String segType, Long SvcId);
    @Query(value="SELECT s.entCustNr FROM ShipEntity s WHERE s.svcId =:svcId AND s.segType = 'BN' AND s.entCustNr IS NOT NULL AND s.entCustNr <> ''")
    String getEntityCustomerNr(Long svcId);

}
