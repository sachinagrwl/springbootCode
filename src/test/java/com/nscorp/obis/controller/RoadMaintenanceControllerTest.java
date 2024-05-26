package com.nscorp.obis.controller;

import com.nscorp.obis.domain.DestinationSetting;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.DestinationSettingDTO;
import com.nscorp.obis.dto.mapper.DestinationSettingMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.RoadMaintenanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.mockito.Mockito.*;

class RoadMaintenanceControllerTest {
    @Mock
    RoadMaintenanceService roadMaintenanceService;
    @InjectMocks
    RoadMaintenanceController roadMaintenanceController;
    DestinationSetting destinationSetting;
    DestinationSettingDTO destinationSettingDTO;
    Station station;
    List<String> route;
    Map<String, String> header;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        route = new ArrayList<>();
        route.add("NS   BSCL");
        route.add("JVCL");
        station=new Station();
        station.setTermId(1L);
        station.setRoadNumber("ABCD");
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
        destinationSettingDTO = new DestinationSettingDTO();
        destinationSettingDTO = DestinationSettingMapper.INSTANCE.destinationsettingToDestinationSettingDTO(destinationSetting);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @Test
    void testAddRoad() {
        when(roadMaintenanceService.addRoadMaintenance(any(), any())).thenReturn(destinationSetting);
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.addRoad(destinationSettingDTO,header);
    }
    @Test
    void testAddRoadInvalidDataException() {
        when(roadMaintenanceService.addRoadMaintenance(any(), any())).thenThrow(new InvalidDataException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.addRoad(destinationSettingDTO,header);
    }

    @Test
    void testAddRoadRecordNotAddedException() {
        when(roadMaintenanceService.addRoadMaintenance(any(), any())).thenThrow(new RecordNotAddedException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.addRoad(destinationSettingDTO,header);
    }

    @Test
    void testAddRoadException() {
        when(roadMaintenanceService.addRoadMaintenance(any(), any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.addRoad(destinationSettingDTO,header);
    }

    @Test
    void testAddRoadRecordAlreadyExistsException() {
        when(roadMaintenanceService.addRoadMaintenance(any(), any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.addRoad(destinationSettingDTO,header);
    }

    @Test
    void testUpdateRoad() {
        when(roadMaintenanceService.updateRoadMaintenance(any(), any())).thenReturn(destinationSetting);
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.updateRoad(destinationSettingDTO,header);
    }

    @Test
    void testUpdateRoadInvalidDataException() {
        when(roadMaintenanceService.updateRoadMaintenance(any(), any())).thenThrow(new InvalidDataException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.updateRoad(destinationSettingDTO,header);
    }

    @Test
    void testUpdateRoadRecordNotAddedException() {
        when(roadMaintenanceService.updateRoadMaintenance(any(), any())).thenThrow(new RecordNotAddedException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.updateRoad(destinationSettingDTO,header);
    }

    @Test
    void testUpdateRoadException() {
        when(roadMaintenanceService.updateRoadMaintenance(any(), any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.updateRoad(destinationSettingDTO,header);
    }

    @Test
    void testDeleteRoadMaintenance(){
        when(roadMaintenanceService.deleteRoadMaintenance(any())).thenReturn(destinationSetting);
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.deleteRoad(destinationSettingDTO);
    }

    @Test
    void testDeleteRoadMaintenanceNoRecordFoundException(){
        when(roadMaintenanceService.deleteRoadMaintenance(any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.deleteRoad(destinationSettingDTO);

        when(roadMaintenanceService.updateRoadMaintenance(any(),any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result1 = roadMaintenanceController.updateRoad(destinationSettingDTO,header);
    }

    @Test
    void testDeleteRoadMaintenanceInValidDataException(){
        when(roadMaintenanceService.deleteRoadMaintenance(any())).thenThrow(new InvalidDataException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.deleteRoad(destinationSettingDTO);
    }

    @Test
    void testDeleteRoadMaintenanceException(){
        when(roadMaintenanceService.deleteRoadMaintenance(any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DestinationSettingDTO>> result = roadMaintenanceController.deleteRoad(destinationSettingDTO);
    }
}
