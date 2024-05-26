package com.nscorp.obis.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.EMSIngateAllocation;
import com.nscorp.obis.domain.Station;

@Repository
public interface EMSIngateAllocationRepository extends JpaRepository<EMSIngateAllocation, Long> {

	@Query(value = "SELECT allocation FROM EMSIngateAllocation allocation "
			+ "LEFT OUTER JOIN Station station ON allocation.onlineDestinationStation = station.termId "
			+ "LEFT OUTER JOIN Terminal terminal ON allocation.ingateTerminalId = terminal.terminalId "
			+ "LEFT OUTER JOIN Station station1 ON allocation.offlineDestinationStation = station1.termId "
			+ "LEFT OUTER JOIN CorporateCustomer corporateCustomer ON allocation.corporateCustomerId = corporateCustomer.corporateCustomerId "
			+ "where (allocation.ingateTerminalId = :ingateTerminalId or :ingateTerminalId is null) "
			+ "AND (allocation.onlineOriginStation = :onlineOriginStation or :onlineOriginStation is null) "
			+ "AND (allocation.onlineDestinationStation = :onlineDestinationStation or :onlineDestinationStation is null) "
			+ "AND (allocation.offlineDestinationStation = :offlineDestinationStation or :offlineDestinationStation is null) "
			+ "AND (allocation.corporateCustomerId = :corporateCustomerId or :corporateCustomerId is null) "
			+ "AND ((allocation.domestic like CONCAT('%',upper(:domestic),'%')) "
			+ "OR (allocation.international like CONCAT('%',upper(:international),'%')) "
			+ "OR (allocation.premium like CONCAT('%',upper(:premium),'%'))) "
			+ "AND (allocation.active like CONCAT('%',upper(:active),'%') or :active is null) "
			+ "AND (allocation.wayBillRoute like CONCAT('%',upper(:wayBillRoute),'%') or :wayBillRoute is null) "
			+ "AND (allocation.local like CONCAT('%',upper(:local),'%') or :local is null) "
			+ "AND (allocation.steelWheel like CONCAT('%',upper(:steelWheel),'%') or :steelWheel is null) "
			+ "AND (allocation.rubberTire like CONCAT('%',upper(:rubberTire),'%') or :rubberTire is null) "
			+ "AND (allocation.startDate like :startDate or :startDate is null) "
			+ "AND (allocation.endDate like :endDate or :endDate is null) "
			+ "AND (allocation.temporaryIndicator like CONCAT('%',upper(:temporaryIndicator),'%') or :temporaryIndicator is null) "
			+ "AND ((allocation.endDate >= :currentDate or allocation.endDate is null) or :currentDate is null )"
			, countQuery = "SELECT COUNT(*) FROM EMSIngateAllocation allocation "
			+ "where (allocation.ingateTerminalId = :ingateTerminalId or :ingateTerminalId is null) "
			+ "AND (allocation.onlineOriginStation = :onlineOriginStation or :onlineOriginStation is null) "
			+ "AND (allocation.onlineDestinationStation = :onlineDestinationStation or :onlineDestinationStation is null) "
			+ "AND (allocation.offlineDestinationStation = :offlineDestinationStation or :offlineDestinationStation is null) "
			+ "AND (allocation.corporateCustomerId = :corporateCustomerId or :corporateCustomerId is null) "
			+ "AND ((allocation.domestic like CONCAT('%',upper(:domestic),'%')) "
			+ "OR (allocation.international like CONCAT('%',upper(:international),'%')) "
			+ "OR (allocation.premium like CONCAT('%',upper(:premium),'%'))) "
			+ "AND (allocation.active like CONCAT('%',upper(:active),'%') or :active is null) "
			+ "AND (allocation.wayBillRoute like CONCAT('%',upper(:wayBillRoute),'%') or :wayBillRoute is null) "
			+ "AND (allocation.local like CONCAT('%',upper(:local),'%') or :local is null) "
			+ "AND (allocation.steelWheel like CONCAT('%',upper(:steelWheel),'%') or :steelWheel is null) "
			+ "AND (allocation.rubberTire like CONCAT('%',upper(:rubberTire),'%') or :rubberTire is null) "
			+ "AND (allocation.startDate like :startDate or :startDate is null) "
			+ "AND (allocation.endDate like :endDate or :endDate is null) "
			+ "AND (allocation.temporaryIndicator like CONCAT('%',upper(:temporaryIndicator),'%') or :temporaryIndicator is null) "
			+ "AND ((allocation.endDate >= :currentDate or allocation.endDate is null) or :currentDate is null )"
			)
			Page<EMSIngateAllocation> searchAllIngateAllocations(Long ingateTerminalId, Station onlineOriginStation,
												Station onlineDestinationStation, Station offlineDestinationStation, Long corporateCustomerId,
												String domestic, String international, String premium, String active, String wayBillRoute, String local, String steelWheel,
												String rubberTire, LocalDate startDate, LocalDate endDate, LocalDate currentDate, String temporaryIndicator, Pageable pageable);

	@Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
    Long SGK();

	EMSIngateAllocation findByTimsId(Long timsId);

	@Query("SELECT ems.numberIngated from EMSIngateAllocation ems where ems.timsId = :timsId")
	Integer getNumberIngated(Long timsId);

	boolean existsByTimsId(Long timsId);
}

