package com.nscorp.obis.repository;

import com.nscorp.obis.domain.TermFreeDay;
import com.nscorp.obis.domain.TermFreeDayPrimaryKeys;
import com.nscorp.obis.domain.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TermFreeDayRepository extends JpaRepository<TermFreeDay, TermFreeDayPrimaryKeys> {

    @Query(value = "SELECT termFreeDay " +
            "from TermFreeDay termFreeDay " +
            "where (:nullListCheck = null OR termFreeDay.termId IN (:termId)) " +
            "AND (termFreeDay.closeDate like :closeDate or :closeDate is null) " +
            "AND (termFreeDay.closeRsnCd like CONCAT('%',upper(:closeCode),'%') or :closeCode is null) " +
            "AND (termFreeDay.closeFromTime like :closeFromTime or :closeFromTime is null) "+
            "Order By termFreeDay.closeDate DESC, terminal.terminalName ASC "
    )
    List<TermFreeDay> findAll(List<Long> termId, LocalDate closeDate, String closeCode, LocalTime closeFromTime, String nullListCheck);

    Boolean existsByTermIdAndCloseDateAndCloseFromTime(Long termId, LocalDate closeDate, LocalTime closeFromTime);

    void deleteByTermIdAndCloseDateAndCloseFromTime(Long termId, LocalDate closeDate, LocalTime closeFromTime);

	TermFreeDay findByTermIdAndCloseDateAndCloseFromTime(Long termId, LocalDate closeDate, LocalTime closeFromTime);

    @Query(value = "SELECT DISTINCT termFreeDay.closeRsnDesc " +
            "from TermFreeDay termFreeDay where termFreeDay.closeRsnDesc is not null")
    List<String> findByDistinctReasonDesc();
}
