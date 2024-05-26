package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.DestinationSetting;
import com.nscorp.obis.dto.DestinationSettingDTO;
import com.nscorp.obis.dto.mapper.DestinationSettingMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.RoadMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Map;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/")
public class RoadMaintenanceController {

    @Autowired
    public RoadMaintenanceService roadMaintenanceService;

    @PostMapping(value = ControllerConstants.ROAD_MAINTENANCE)
    public ResponseEntity<APIResponse<DestinationSettingDTO>> addRoad(@Valid @NotNull @RequestBody DestinationSettingDTO destinationSettingDTOObj,
                                                                      @RequestHeader Map<String, String> headers) {
        try {
            DestinationSetting roadObj = DestinationSettingMapper.INSTANCE.destinationsettingDTOToDestinationSetting(destinationSettingDTOObj);
            DestinationSetting addStation = roadMaintenanceService.addRoadMaintenance(roadObj, headers);
            DestinationSettingDTO addedStationDto = DestinationSettingMapper.INSTANCE.destinationsettingToDestinationSettingDTO(addStation);
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), addedStationDto,
                    ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (InvalidDataException e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (RecordNotAddedException e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (RecordAlreadyExistsException e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (Exception e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

    @PutMapping(value = ControllerConstants.ROAD_MAINTENANCE)
    public ResponseEntity<APIResponse<DestinationSettingDTO>> updateRoad(@Valid @NotNull @RequestBody DestinationSettingDTO destinationSettingDTOObj,
                                                                      @RequestHeader Map<String, String> headers) {
        try {
            DestinationSetting roadObj = DestinationSettingMapper.INSTANCE.destinationsettingDTOToDestinationSetting(destinationSettingDTOObj);
            DestinationSetting addStation = roadMaintenanceService.updateRoadMaintenance(roadObj, headers);
            DestinationSettingDTO addedStationDto = DestinationSettingMapper.INSTANCE.destinationsettingToDestinationSettingDTO(addStation);
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"), addedStationDto,
                    ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (InvalidDataException e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordNotAddedException e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (Exception e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping(value = ControllerConstants.ROAD_MAINTENANCE)
    public ResponseEntity<APIResponse<DestinationSettingDTO>> deleteRoad(@Valid @NotNull @RequestBody DestinationSettingDTO destinationSettingDTOObj) {
        try {
            DestinationSetting roadObj = DestinationSettingMapper.INSTANCE.destinationsettingDTOToDestinationSetting(destinationSettingDTOObj);
            DestinationSetting deleteStation = roadMaintenanceService.deleteRoadMaintenance(roadObj);
            DestinationSettingDTO deleteStationDto = DestinationSettingMapper.INSTANCE.destinationsettingToDestinationSettingDTO(deleteStation);
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully deleted data!"), deleteStationDto,
                    ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (InvalidDataException e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<DestinationSettingDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

}

