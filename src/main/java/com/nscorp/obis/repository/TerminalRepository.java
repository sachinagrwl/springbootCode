package com.nscorp.obis.repository;

import java.util.List;

import com.nscorp.obis.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.Terminal;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal,Long>{

	boolean existsByTerminalId(Long terminalId);

	List<Terminal> findAllByOrderByTerminalNameAsc();

	@Query("SELECT terminal.stnXrfId from Terminal terminal where terminal.terminalId = :terminalId AND terminal.expiredDate is null")
	Long getStationXrfId(Long terminalId);

	boolean existsByTerminalIdAndExpiredDateIsNull(Long terminalId);

	@Query(value = "SELECT new com.nscorp.obis.domain.Terminal(terminal.terminalId, "+
	"terminal.terminalName, terminal.stnXrfId, terminal.expiredDate) from Terminal terminal where (terminal.expiredDate is null) "
	+ "order by terminal.terminalName")
	List<Terminal> fetchAllActiveTerminals();

	Terminal findByTerminalId(Long terminalId);
	
	@Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
    Long SGK();

	boolean existsByTerminalName(String terminalName);


	boolean existsByStnXrfId(Long stnXrfId);
}
