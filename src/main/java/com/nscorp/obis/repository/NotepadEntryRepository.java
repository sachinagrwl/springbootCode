package com.nscorp.obis.repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.NotepadEntry;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotepadEntryRepository extends JpaRepository<NotepadEntry, Long>, CommonKeyGenerator {

    List<NotepadEntry> findAll(Specification<NotepadEntry> specification);

	List<NotepadEntry> findBySvcIdAndCreateDateTimeLessThanOrderByCreateDateTime(Long svcId, Timestamp currentDateTime);
}
