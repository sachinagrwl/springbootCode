package com.nscorp.obis.services;

import com.nscorp.obis.domain.DestinationSetting;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.DestinationSettingDTO;
import com.nscorp.obis.dto.mapper.DestinationSettingMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.BlockRepository;
import com.nscorp.obis.repository.DestinationSettingRepository;
import com.nscorp.obis.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RoadMaintenanceServiceTest {
    @Mock
    StationRepository stationRepository;
    @Mock
    BlockRepository blockRepository;
    @Mock
    DestinationSettingRepository destinationSettingRepository;
    @InjectMocks
    RoadMaintenanceServiceImpl roadMaintenanceServiceImpl;

    DestinationSetting destinationSetting;
    Station station;
    DestinationSettingDTO destinationSettingDTO;
    List<String> route;
    Map<String, String> header;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        station = new Station();
        station.setTermId(1L);
        station.setRoadNumber("ABCD");
        route = new ArrayList<>();
        route.add("NS   BSCL");
        route.add("JVCL");
        destinationSetting = new DestinationSetting();
        destinationSetting.setBlockId(1L);
        destinationSetting.setDestId(1d);
        destinationSetting.setNsDestTermId(station);
        destinationSetting.setOfflineDest(station);
        destinationSetting.setIncludeExclude("I");
        destinationSetting.setCofcAllowedInd("Y");
        destinationSetting.setOnlineMileage(1);
        destinationSetting.setOfflineMileage(1);
        destinationSetting.setRoute(route);
        destinationSetting.setTransitDays("12");
        destinationSetting.setUversion("!");
        destinationSettingDTO = new DestinationSettingDTO();
        destinationSettingDTO = DestinationSettingMapper.INSTANCE.destinationsettingToDestinationSettingDTO(destinationSetting);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @Test
    void testAddUpdateRoadMaintenance() {
        when(blockRepository.existsByBlockId(anyLong())).thenReturn(true);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        when(stationRepository.getJunctionListByRoadName(anyString())).thenReturn(Arrays.<String>asList("BSCL"));
        when(stationRepository.getRoadNameByJunctionList(anyString())).thenReturn(Arrays.<String>asList("NS","JVCL"));
        when(destinationSettingRepository.save(Mockito.any())).thenReturn(destinationSetting);
        DestinationSetting result = roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header);
        DestinationSetting result1 = roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header);

    }

    @Test
    void testAddUpdateRoadMaintenanceRecordNotAddedException() {
        when(blockRepository.existsByBlockId(anyLong())).thenReturn(true);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        when(stationRepository.getJunctionListByRoadName(anyString())).thenReturn(Arrays.<String>asList("BSCL"));
        when(stationRepository.getRoadNameByJunctionList(anyString())).thenReturn(Arrays.<String>asList("NS","JVCL"));
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));
    }

    @Test
    void testUpdateRoadMaintenanceNoRecordsFoundException() {
        when(destinationSettingRepository.existsById(any())).thenReturn(false);
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting, header));
    }

    @Test
    void testUpdateRoadMaintenanceInvalidDataException() {
        destinationSetting.setDestId(null);
        InvalidDataException exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setDestId(1d);
        destinationSetting.setTransitDays("");
        when(destinationSettingRepository.existsById(any())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setTransitDays("12");
        when(blockRepository.existsByBlockId(anyLong())).thenReturn(false);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setRoute(Collections.emptyList());
        when(blockRepository.existsByBlockId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setRoute(null);
        when(blockRepository.existsByBlockId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        route=new ArrayList<>();
        route.add("NS   JVCL");
        route.add("BS   JVCD");
        route.add("CS   JVCA");
        route.add("ES");
        destinationSetting.setRoute(route);
        destinationSetting.setOfflineDest(null);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setOfflineDest(station);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(false);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setOfflineMileage(null);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setOfflineMileage(1);
        destinationSetting.setNsDestTermId(null);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setNsDestTermId(station);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(false);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setOnlineMileage(null);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        destinationSetting.setOnlineMileage(1);
        route=new ArrayList<>();
        route.add("DS   JVCL");
        route.add("BS   JVCD");
        route.add("CS   JVCA");
        route.add("ES");
        destinationSetting.setRoute(route);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        route=new ArrayList<>();
        route.add("NS   JVCL");
        route.add("NS   JVCD");
        route.add("CS   JVCA");
        route.add("ES");
        destinationSetting.setRoute(route);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        route=new ArrayList<>();
        route.add("NS   JVCL");
        route.add("BS   JVCD");
        route.add("CS   JVCA");
        destinationSetting.setRoute(route);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        route=new ArrayList<>();
        route.add("NS   JVCL");
        route.add("BS   JVCD");
        route.add("CS");
        destinationSetting.setRoute(route);
        when(stationRepository.getJunctionListByRoadName(anyString())).thenReturn(Arrays.<String>asList("A"));
        when(stationRepository.getRoadNameByJunctionList(anyString())).thenReturn(Arrays.<String>asList("NS","BS","CS"));
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));

        when(stationRepository.getRoadNameByJunctionList(anyString())).thenReturn(Arrays.<String>asList("A"));
        when(stationRepository.getJunctionListByRoadName(anyString())).thenReturn(Arrays.<String>asList("JVCL","JVCD"));
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.updateRoadMaintenance(destinationSetting,header));
    }

    @Test
    void testAddRoadMaintenanceInvalidDataException() {
        destinationSetting.setTransitDays("");
        InvalidDataException exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        destinationSetting.setTransitDays("12");
        when(blockRepository.existsByBlockId(anyLong())).thenReturn(false);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        destinationSetting.setRoute(Collections.emptyList());
        when(blockRepository.existsByBlockId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        destinationSetting.setRoute(null);
        when(blockRepository.existsByBlockId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        route=new ArrayList<>();
        route.add("NS   JVCL");
        route.add("BS   JVCD");
        route.add("CS   JVCA");
        route.add("ES");
        destinationSetting.setRoute(route);
        destinationSetting.setOfflineDest(null);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        destinationSetting.setOfflineDest(station);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(false);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        destinationSetting.setOfflineMileage(null);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        destinationSetting.setOfflineMileage(1);
        destinationSetting.setNsDestTermId(null);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        destinationSetting.setNsDestTermId(station);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(false);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        destinationSetting.setOnlineMileage(null);
        when(stationRepository.existsByTermId(anyLong())).thenReturn(true);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        destinationSetting.setOnlineMileage(1);
        route=new ArrayList<>();
        route.add("DS   JVCL");
        route.add("BS   JVCD");
        route.add("CS   JVCA");
        route.add("ES");
        destinationSetting.setRoute(route);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        route=new ArrayList<>();
        route.add("NS   JVCL");
        route.add("NS   JVCD");
        route.add("CS   JVCA");
        route.add("ES");
        destinationSetting.setRoute(route);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        route=new ArrayList<>();
        route.add("NS   JVCL");
        route.add("BS   JVCD");
        route.add("CS   JVCA");
        destinationSetting.setRoute(route);
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        route=new ArrayList<>();
        route.add("NS   JVCL");
        route.add("BS   JVCD");
        route.add("CS");
        destinationSetting.setRoute(route);
        when(stationRepository.getJunctionListByRoadName(anyString())).thenReturn(Arrays.<String>asList("A"));
        when(stationRepository.getRoadNameByJunctionList(anyString())).thenReturn(Arrays.<String>asList("NS","BS","CS"));
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));

        when(stationRepository.getRoadNameByJunctionList(anyString())).thenReturn(Arrays.<String>asList("A"));
        when(stationRepository.getJunctionListByRoadName(anyString())).thenReturn(Arrays.<String>asList("JVCL","JVCD"));
        exception= assertThrows(InvalidDataException.class,
                () ->  roadMaintenanceServiceImpl.addRoadMaintenance(destinationSetting,header));
    }

    @Test
    void testDeleteRoadMaintenance(){
        when(destinationSettingRepository.existsById(Mockito.any())).thenReturn(true);
        route=new ArrayList<>();
        route.add("NS   JVCL");
        destinationSetting.setRoute(route);
        DestinationSetting result = roadMaintenanceServiceImpl.deleteRoadMaintenance(destinationSetting);
    }

    @Test
    void testDeleteRoadMaintenanceNoRecordFound(){
        when(destinationSettingRepository.existsById(Mockito.any())).thenReturn(false);
        NoRecordsFoundException exception= assertThrows(NoRecordsFoundException.class,
                () ->  when(roadMaintenanceServiceImpl.deleteRoadMaintenance(destinationSetting)));
    }

    @Test
    void testDeleteRoadMaintenanceInvalidDataException(){
        when(destinationSettingRepository.existsById(Mockito.any())).thenReturn(true);
        destinationSetting.setRoute(Collections.emptyList());
        InvalidDataException exception= assertThrows(InvalidDataException.class,
                () ->  when(roadMaintenanceServiceImpl.deleteRoadMaintenance(destinationSetting)));

        destinationSetting.setRoute(null);
        exception= assertThrows(InvalidDataException.class,
                () ->  when(roadMaintenanceServiceImpl.deleteRoadMaintenance(destinationSetting)));

        route = new ArrayList<>();
        route.add("NS   JVCL");
        route.add("BS   JVCD");
        destinationSetting.setRoute(route);
        exception= assertThrows(InvalidDataException.class,
                () ->  when(roadMaintenanceServiceImpl.deleteRoadMaintenance(destinationSetting)));
    }
}
