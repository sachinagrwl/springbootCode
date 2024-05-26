package com.nscorp.obis.repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.ContainerChassisAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerChassisAssociationRepository
		extends JpaRepository<ContainerChassisAssociation, Long>, CommonKeyGenerator {

	@Query("select association from ContainerChassisAssociation association where association.expiredDateTime is null")
	List<ContainerChassisAssociation> getAllAssociations();

	boolean existsByContainerInitAndChassisInitAndContainerLengthAndChassisLength(String containerInit,
			String chassisInit, Integer containerLength, Integer chassisLength);

	List<ContainerChassisAssociation> findByContainerInitAndChassisInitAndContainerLengthAndChassisLength(
			String containerInit, String chassisInit, Integer containerLength, Integer chassisLength);

	boolean existsByAssociationIdAndUversion(Long associationId, String uversion);

	@Query("select association from ContainerChassisAssociation association order by association.containerInit, "
			+ "association.containerLowNumber, association.containerHighNumber, association.containerLength, "
			+ "association.chassisInit, association.chassisLowNumber, association.chassisHighNumber, "
			+ "association.chassisLength")
	List<ContainerChassisAssociation> findAll();
}
