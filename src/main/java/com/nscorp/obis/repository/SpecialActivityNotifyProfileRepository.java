package com.nscorp.obis.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.SpecialActivityNotifyProfile;

@Repository
public interface SpecialActivityNotifyProfileRepository extends JpaRepository<SpecialActivityNotifyProfile, Integer>{
    
    List<SpecialActivityNotifyProfile> findByActivityId(@Valid Integer activityId);

}
