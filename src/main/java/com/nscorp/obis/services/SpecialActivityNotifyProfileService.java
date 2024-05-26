package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.domain.ActivityNotifyProfile;
import com.nscorp.obis.domain.SpecialActivityNotifyProfile;
import com.nscorp.obis.dto.ActivityNotifyProfileDTO;
import com.nscorp.obis.dto.SpecialActivityNotifyProfileDTO;

public interface SpecialActivityNotifyProfileService {
    
    public List<SpecialActivityNotifyProfile> fetchActivityDetails() throws SQLException;
    public List<SpecialActivityNotifyProfile> fetchActivityProfiles(@Valid Integer activityId) throws SQLException;
    public ActivityNotifyProfileDTO addActivityNotifyProfile(ActivityNotifyProfileDTO activityNotifyProfileDTO,Map<String, String> headers);
    public SpecialActivityNotifyProfile updateActivityNotifyProfile(SpecialActivityNotifyProfileDTO specialActivityNotifyProfileDTO,Map<String, String> headers) throws SQLException;
    public void deleteActivityNotifyProfile(@Valid @NotNull ActivityNotifyProfile activityNotifyProfile);
}
