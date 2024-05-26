package com.nscorp.obis.repository;

import com.nscorp.obis.domain.TruckerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckerGroupRepository extends JpaRepository<TruckerGroup, String> {


    boolean existsByTruckerGroupCode(String truckerGroupCode);

    TruckerGroup findByTruckerGroupCode(String truckerGroupCode);

    void deleteByTruckerGroupCode(String truckerGroupCode);

    boolean existsByTruckerGroupCodeAndUversion(String truckerGroupCode, String uversion);
}
