package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.PlacardType;

@Repository
public interface PlacardTypeRepository extends JpaRepository<PlacardType, String>{

	PlacardType findByPlacardCd(String placardCd);


	boolean existsByPlacardCd(String placardCd);

    List<PlacardType> findAllByOrderByPlacardCdAsc();
	
	boolean existsByPlacardCdAndUversion(String placardCd, String uversion);
	
	List<PlacardType> findByPlacardCdAndUversion(String placardCd, String uversion);
	
	void deleteByPlacardCdAndUversion(String placardCd, String uversion);
}
