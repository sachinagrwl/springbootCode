package com.nscorp.obis.repository;

import com.nscorp.obis.domain.CodeTableSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CodeTableSelectionRepository extends JpaRepository <CodeTableSelection,String> {
	
	CodeTableSelection findByGenericTable(String tableName);

	CodeTableSelection findByGenericTableIgnoreCase(String tableName);
	
	/* Get code size */
	@Query(value = "select cts.genCdFldSize AS genCdFldSize from CodeTableSelection cts where cts.genericTable=?1")
	Short findBySize(String tableName);

	boolean existsByGenericTable(String genericTable);
	
	void deleteByGenericTable(String genericTable);
	
	boolean existsByGenericTableIgnoreCase(String genericTable);

	List<CodeTableSelection> findAllByOrderByGenericTableAsc();


}
