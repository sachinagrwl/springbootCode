package com.nscorp.obis.services;

import com.nscorp.obis.domain.DestinationSetting;
import java.util.Map;

public interface RoadMaintenanceService {

    DestinationSetting addRoadMaintenance(DestinationSetting destinationSettingObj, Map<String, String> headers);
    DestinationSetting updateRoadMaintenance(DestinationSetting destinationSettingObj, Map<String, String> headers);
    DestinationSetting deleteRoadMaintenance(DestinationSetting roadObj);
}
