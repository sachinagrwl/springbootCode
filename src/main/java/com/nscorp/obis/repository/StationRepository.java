package com.nscorp.obis.repository;

import com.nscorp.obis.domain.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {

    @Query(value = "SELECT new com.nscorp.obis.domain.Station(station.uversion AS uversion, " +
            "station.createUserId AS createUserId, " +
            "station.createDateTime AS createDateTime, " +
            "station.updateUserId AS updateUserId, " +
            "station.updateDateTime AS updateDateTime, " +
            "station.updateExtensionSchema AS updateExtensionSchema, " +
            "station.stationName AS stationName, " +
            "station.roadNumber AS roadNumber, " +
            "station.termId AS termId, " +
            "station.FSAC AS FSAC, " +
            "station.state AS state, " +
            "station.billAtFsac AS billAtFsac, " +
            "station.roadName AS roadName, " +
            "station.operationStation AS operationStation, " +
            "station.splc AS splc, " +
            "station.rule260Station AS rule260Station, " +
            "station.intermodalIndicator AS intermodalIndicator, " +
            "station.char5Spell AS char5Spell, " +
            "station.char5Alias AS char5Alias, " +
            "station.char8Spell AS char8Spell, " +
            "station.division AS division, " +
            "station.topPick AS topPick, " +
            "station.bottomPick AS bottomPick, " +
            "station.expirationDate AS expirationDate) " +
            "from Station as station " +
            "where (station.stationName like CONCAT('%',upper(:stationName),'%') or :stationName is null) " +
            "AND (station.roadNumber like CONCAT('%',upper(:roadNumber),'%') or :roadNumber is null) " +
            "AND (station.state like CONCAT('%',upper(:state),'%') or :state is null) " +
            "AND (station.FSAC like CONCAT('%',upper(:FSAC),'%') or :FSAC is null) " +
            "AND (station.billAtFsac like CONCAT('%',upper(:billAtFsac),'%') or :billAtFsac is null) " +
            "AND (station.roadName like CONCAT('%',upper(:roadName),'%') or :roadName is null) " +
            "AND (station.operationStation like CONCAT('%',upper(:operationStation),'%') or :operationStation is null) " +
            "AND (station.splc like CONCAT('%',upper(:splc),'%') or :splc is null) " +
            "AND (station.rule260Station like CONCAT('%',upper(:rule260Station),'%') or :rule260Station is null) " +
            "AND (station.intermodalIndicator like CONCAT('%',upper(:intermodalIndicator),'%') or :intermodalIndicator is null) " +
            "AND (station.char5Spell like CONCAT('%',upper(:char5Spell),'%') or :char5Spell is null) " +
            "AND (station.char5Alias like CONCAT('%',upper(:char5Alias),'%') or :char5Alias is null) " +
            "AND (station.char8Spell like CONCAT('%',upper(:char8Spell),'%') or :char8Spell is null) " +
            "AND (station.division like CONCAT('%',upper(:division),'%') or :division is null) " +
            "AND (station.expirationDate like :expirationDate or :expirationDate is null) " +
            "AND (station.expiredDate is null) " +
            "Order By station.stationName ASC, station.state ASC"
    )
    Page<Station> searchAll(@Valid String stationName, String roadNumber, String FSAC, String state, String billAtFsac, String roadName, String operationStation, String splc,
                            String rule260Station, String intermodalIndicator, String char5Spell, String char5Alias, String char8Spell, String division, Date expirationDate,
                            Pageable pageable);

	boolean existsByTermId(Long termId);

	Station getByTermId(Long termId);

	boolean existsByTermIdAndExpiredDateIsNull(Long termId);

    boolean existsByRoadNumber(String roadNumber);

	Station findByTermId(Long termId);

    List<Station> findByChar8Spell(String char8Spell);

    @Query(value = "select station.rule260Station from Station station where station.expiredDate is null And station.roadName =:roadName And station.rule260Station Is Not NULL")
    List<String> getJunctionListByRoadName(String roadName);

    @Query(value = "select trim(station.roadName) from Station station where station.expiredDate is null And station.rule260Station=:juncName")
    List<String> getRoadNameByJunctionList(String juncName);
}
