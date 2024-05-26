package com.nscorp.obis.repository;

import com.nscorp.obis.domain.GenericCodeUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenericCodeUpdateRepository extends JpaRepository <GenericCodeUpdate,String> {

	List<GenericCodeUpdate> findByGenericTable(String tableName);
	
    List<GenericCodeUpdate> findByGenericTableIgnoreCase(String tableName);

	GenericCodeUpdate findByGenericTableAndGenericTableCodeIgnoreCase(String genericTable, String genericTableCode);

	GenericCodeUpdate findByGenericTableAndGenericTableCode(String genericTable, String genericTableCode);
	
	boolean existsByGenericTableAndGenericTableCodeIgnoreCase(String genericTable, String genericTableCode);
	
	void deleteByGenericTableAndGenericTableCodeIgnoreCase(String genericTable, String genericTableCode);
	
	boolean existsByGenericTableIgnoreCase(String genericTable);

	boolean existsByGenericTableCodeIgnoreCase(String genericTable);

	boolean existsByGenericTableAndGenericTableCode(String genericTable, String genericTableCode);
	
	void deleteByGenericTableAndGenericTableCode(String genericTable, String genericTableCode);
	
	boolean existsByGenericTable(String genericTable);

	//for termFreeDay 1052//
	boolean existsByGenericTableAndGenericTableCodeAndGenericShortDescriptionIgnoreCase(String tableName, String closeRsnCd, String closeRsnDesc);


	GenericCodeUpdate findByGenericTableCodeAndGenericTable(String chrgTp, String string);

//	boolean existsByGenericTableCodeIgnoreCase(String closeRsnCd);

}
