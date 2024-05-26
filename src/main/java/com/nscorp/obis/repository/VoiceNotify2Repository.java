package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.VoiceNotify2;

public interface VoiceNotify2Repository extends JpaRepository<VoiceNotify2,Long> {

	boolean existsBySvcId(Long svcId);

	VoiceNotify2 findBySvcId(Long svcId);
}
