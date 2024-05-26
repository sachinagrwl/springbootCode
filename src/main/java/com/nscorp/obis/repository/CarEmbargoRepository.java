package com.nscorp.obis.repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.CarEmbargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarEmbargoRepository extends JpaRepository<CarEmbargo, Double>, CommonKeyGenerator {

    List<CarEmbargo> findAllByOrderByAarType();

    boolean existsByEmbargoIdAndUversion(Long embargoId, String uversion);

    CarEmbargo findByEmbargoId(Long embargoId);

    void deleteByEmbargoId(Long embargoId);
}
