package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.EquipmentEmbargo;

@Repository
public interface EquipmentEmbargoRepository extends JpaRepository<EquipmentEmbargo, Long>, CommonKeyGenerator {

	List<EquipmentEmbargo> findAllByOrderByUpdateDateTimeDesc();

	EquipmentEmbargo findByEmbargoId(Long embargoId);

	boolean existsByEmbargoIdAndUversion(Long embargoId, String uversion);
}
