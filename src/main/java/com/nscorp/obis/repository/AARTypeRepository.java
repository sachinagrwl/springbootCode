package com.nscorp.obis.repository;

import com.nscorp.obis.domain.AARType;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AARTypeRepository extends JpaRepository <AARType, String> {

	List<AARType> findByAarTypeStartsWith(String c);

	//boolean existsByAarType(String upperCase);
	
	boolean existsByAarType(String aarType);
	AARType findByAarType(String aarType);
	List<AARType> findAllByOrderByAarType();
	List<AARType> findByAarTypeInAndAarDescriptionInAndAarCapacityInOrderByAarTypeAsc(List<String> aarType,List<String> aarDescription
			,List<Integer> aarCapacity);

	List<AARType> findByAarTypeInOrderByAarTypeAsc(List<String> type);

	List<AARType> findByAarTypeInAndAarDescriptionInOrderByAarTypeAsc(List<String> type, List<String> description);

	List<AARType> findByAarTypeInAndAarCapacityInOrderByAarTypeAsc(List<String> type,List<Integer> capacity);

	List<AARType> findByAarDescriptionInOrderByAarTypeAsc(List<String> desc);

	List<AARType> findByAarDescriptionInAndAarCapacityInOrderByAarTypeAsc( List<String> description, List<Integer> capacity);

	List<AARType> findByAarCapacityInOrderByAarTypeAsc(List<Integer> capacity);


	//	@Query(value = "SELECT aar FROM AARType aar "
//			+ "where (aar.aarType like CONCAT('%',upper(:aarType),'%') or :aarType is null) "
//			+ "AND (aar.aarDescription like CONCAT('%',upper(:aarDescription),'%') or :aarDescription is null) "
//			+ "AND (aar.aarCapacity = :aarCapacity or :aarCapacity is null) "
//			)
//	List<AARType> findByAarTypeAndAarDescriptionAndAarCapacityUpdated(String aarType,String aarDescription
//			,Integer aarCapacity);
//	void deleteByAarTypeId(String aarType);
	void deleteByAarType(String aarType);
	
	

}
