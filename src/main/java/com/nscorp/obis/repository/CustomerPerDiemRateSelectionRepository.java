package com.nscorp.obis.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.CustomerPerDiemRateSelection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerPerDiemRateSelectionRepository extends JpaRepository<CustomerPerDiemRateSelection, Long> {

    List<CustomerPerDiemRateSelection> findByCustPrimSix(@Valid String custPrimSix);

    boolean existsByCustPrimSixAndTerminalIdAndBeneficialPrimSixAndShipPrimSixAndOutgateLoadEmptyStatusAndIngateLoadEmptyStatusAndEquipTp
            (String custPrimSix,
             Long terminalId,
             String beneficialPrimSix,
             String shipPrimSix,
             String outgateLoadEmptyStatus,
             String ingateLoadEmptyStatus,
             String equipTp
            );

    @Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
    Long SGK();

}