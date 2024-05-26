package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.Road;

@Repository
public interface RoadRepository extends JpaRepository <Road, String>  {
	
	boolean existsByRoadName(String roadName);

	
	  @Query(value = "SELECT road from Road road " +
	  "where (road.roadNumber like CONCAT(upper(:roadNumber),'%') or :roadNumber is null) "
	  +
	  "AND (road.roadName like CONCAT(upper(:roadName),'%') or :roadName is null) "
	  +
	  "AND (road.roadFullName like CONCAT(upper(:roadFullName),'%') or :roadFullName is null) "
	  +
	  "AND (road.roadType like CONCAT(upper(:roadType),'%') or :roadType is null) "
	  +
	  "Order By road.roadNumber ASC, road.roadName ASC, road.roadFullName ASC, road.roadType ASC"
	  )
	List<Road> findAll(String roadNumber, String roadName, String roadFullName, String roadType);

    Road findByRoadNumber(String roadNumber);

	boolean existsByRoadNumberAndUversion(String roadNumber, String uversion);
}
