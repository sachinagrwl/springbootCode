package com.nscorp.obis.repository;

import com.nscorp.obis.domain.TerminalNotifyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;

@Repository
public interface TerminalNotifyProfileRepository extends JpaRepository<TerminalNotifyProfile,Double>{

	List<TerminalNotifyProfile> findByTerminalId(@Valid Long termId);

	TerminalNotifyProfile findByNotifyProfileId(@Valid Long notifyProfileId);

	boolean existsByTerminalIdAndNotifyProfileId(Long terminalId, long notifyProfileId);

	TerminalNotifyProfile findByTerminalIdAndNotifyProfileId(Long terminalId, long notifyProfileId);

	void deleteByNotifyProfileId(long notifyProfileId);

	boolean existsByNotifyProfileId(Long notifyProfileId);


	
}
