package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.TerminalFunction;
import com.nscorp.obis.domain.TerminalFunctionComposite;

@Repository
public interface TerminalFunctionRepository extends JpaRepository<TerminalFunction, TerminalFunctionComposite> {

	boolean existsByTerminalId(Long terminalId);

	boolean existsByFunctionName(String functionName);

	List<TerminalFunction> findByTerminalIdOrFunctionName(Long terminalId, String functionName);

	boolean existsByTerminalIdAndFunctionName(Long terminalId, String functionName);

	TerminalFunction findByTerminalIdAndFunctionName(Long terminalId, String functionName);

	TerminalFunction findByTerminalId(Long terminalId);

}
