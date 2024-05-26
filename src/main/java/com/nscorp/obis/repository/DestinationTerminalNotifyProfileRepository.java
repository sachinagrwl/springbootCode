package com.nscorp.obis.repository;

import com.nscorp.obis.domain.DestinationTerminalNotifyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;

@Repository
public interface DestinationTerminalNotifyProfileRepository extends JpaRepository<DestinationTerminalNotifyProfile,Double>{

	List<DestinationTerminalNotifyProfile> findByTerminalId(@Valid Long termId);
	
	DestinationTerminalNotifyProfile findByNotifyProfileId(@Valid Long profileId);

	boolean existsByNotifyProfileId(@Valid Long profileId);
	void deleteByNotifyProfileId(Long notifyProfileId);


}
