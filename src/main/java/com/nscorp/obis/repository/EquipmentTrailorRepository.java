package com.nscorp.obis.repository;

import com.nscorp.obis.domain.EquipmentTrailor;
import com.nscorp.obis.domain.EquipmentTrailorPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface EquipmentTrailorRepository extends JpaRepository<EquipmentTrailor, EquipmentTrailorPrimaryKey> {

    boolean existsByTrailorInitAndTrailorNumber(String carInit, BigDecimal carNbr);
}
