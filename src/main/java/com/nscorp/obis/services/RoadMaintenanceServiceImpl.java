package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DestinationSetting;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.BlockRepository;
import com.nscorp.obis.repository.DestinationSettingRepository;
import com.nscorp.obis.repository.StationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoadMaintenanceServiceImpl implements RoadMaintenanceService {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private DestinationSettingRepository destinationSettingRepository;
    private static int count = 0;


    @Override
    public DestinationSetting addRoadMaintenance(DestinationSetting destinationSettingObj, Map<String, String> headers) {
        UserId.headerUserID(headers);
        validateDestinationSetting(destinationSettingObj);
        destinationSettingObj.setDestId(destinationSettingRepository.SGK().doubleValue());
        destinationSettingObj.setUversion("!");
        destinationSettingObj.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        destinationSettingObj.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
        destinationSettingObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        destinationSettingObj.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        destinationSettingObj.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        DestinationSetting destinationSetting = destinationSettingRepository.save(destinationSettingObj);
        if(destinationSetting!=null)
            return destinationSettingObj;
        else
            throw new RecordNotAddedException("Record not added in DB!");
    }

    private void validateDestinationSetting(DestinationSetting destinationSetting) {
        count = 0;
        List<String> roadList =new ArrayList<>();
        List<String> juncList = new ArrayList<>();

        if (StringUtils.isEmpty(destinationSetting.getTransitDays()))
            throw new InvalidDataException("Transit Days cannot be null!");

        if (!blockRepository.existsByBlockId(Double.valueOf(destinationSetting.getBlockId()).longValue()))
            throw new InvalidDataException("Invalid Block Id!");

        if(destinationSetting.getRoute()!=null && !destinationSetting.getRoute().isEmpty()){
            List<String> roadJnList = destinationSetting.getRoute();
            for (String s : roadJnList) {
                String[] data = s.split("   ");
                roadList.add(data[0]);
                if(data.length>1)
                    juncList.add(data[1]);
                if (data[0].equalsIgnoreCase("NS"))
                    count++;
            }
        }
        else{
            throw new InvalidDataException("Please provide Route Information!");
        }

        int roadCount=roadList.size();
        int juncCount= juncList.size();
        if(juncCount>1){
            if(destinationSetting.getOfflineDest()==null || !stationRepository.existsByTermId(destinationSetting.getOfflineDest().getTermId().longValue()))
                throw new InvalidDataException("Please provide Offline Destination!");
            else{
                if(destinationSetting.getOfflineMileage()==null)
                    throw new InvalidDataException("Please provide Offline Destination!");
            }
        }

        if (count == 0)
            throw new InvalidDataException("Please provide atleast one NS route!");

        if (destinationSetting.getNsDestTermId() == null || (count == 1 && !stationRepository.existsByTermId(destinationSetting.getNsDestTermId().getTermId().longValue())))
            throw new InvalidDataException("NsDestTermId doesn't exist in station_xrf");
        else if(destinationSetting.getOnlineMileage()==null)
            throw new InvalidDataException("Please provide Online mileage!");

        if (count > 1)
            throw new InvalidDataException("NS ROAD cannot be repeated more than one time");

        if(roadCount <= juncCount)
            throw new InvalidDataException("Please provide another Road Information!");

        for(int i=0;i<juncCount;i++){
            if(roadCount>=i+1){
                if(!stationRepository.getJunctionListByRoadName(roadList.get(i)).contains(juncList.get(i)))
                    throw new InvalidDataException("Invalid Junction for " + juncList.get(i) + " Junction!");
                if(!stationRepository.getRoadNameByJunctionList(juncList.get(i)).contains(roadList.get(i+1)))
                    throw new InvalidDataException("Invalid Road for "+ roadList.get(i+1) + " Road!");
            }
        }
    }

    @Override
    public DestinationSetting updateRoadMaintenance(DestinationSetting destinationSettingObj, Map<String, String> headers) {
        UserId.headerUserID(headers);
        if(destinationSettingObj.getDestId()==null)
            throw new InvalidDataException("Please provide destinationId");
        if(!destinationSettingRepository.existsById(destinationSettingObj.getDestId()))
            throw new NoRecordsFoundException("Record Not Found!");
        validateDestinationSetting(destinationSettingObj);
        if(StringUtils.isNotEmpty(destinationSettingObj.getUversion())) {
            destinationSettingObj.setUversion(
                    Character.toString((char) ((((int)destinationSettingObj.getUversion().charAt(0) - 32) % 94) + 33)));
        }
        destinationSettingObj.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        destinationSettingObj.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
        destinationSettingObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        DestinationSetting destinationSetting = destinationSettingRepository.save(destinationSettingObj);
        if(destinationSetting!=null)
            return destinationSettingObj;
        else
            throw new RecordNotAddedException("Record not updated in DB!");
    }

    @Override
    public DestinationSetting deleteRoadMaintenance(DestinationSetting roadObj) {
        if(!destinationSettingRepository.existsById(roadObj.getDestId()))
            throw new NoRecordsFoundException("Record Not Found!");
        if(roadObj.getRoute()!=null && !roadObj.getRoute().isEmpty()){
            if(roadObj.getRoute().size()==1){
                destinationSettingRepository.delete(roadObj);
                return roadObj;
            }
            else
                throw new InvalidDataException("Data contains more than one Road");
        }
        else
            throw  new InvalidDataException("Please provide route Information");
    }
}
