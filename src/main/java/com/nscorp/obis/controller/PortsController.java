package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.Ports;
import com.nscorp.obis.dto.PortsDTO;
import com.nscorp.obis.dto.mapper.PortsMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PortsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController
@RequestMapping(value = ControllerConstants.PORTS)
@Validated
public class PortsController {
    @Autowired
    PortsService portsService;

    @GetMapping
    public ResponseEntity<APIResponse<List<PortsDTO>>> getAllPorts() {
        try {
            List<PortsDTO> portsDTOList = Collections.emptyList();
            List<Ports> portsList = portsService.getAllPorts();
            if (!CollectionUtils.isEmpty(portsList)) {
                portsDTOList = portsList.stream().map(PortsMapper.INSTANCE::PortsToPortsDTO).collect(Collectors.toList());
            }
            APIResponse<List<PortsDTO>> listAPIResponse = new APIResponse<>(
                    Arrays.asList("Successfully retrieved data!"), portsDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<PortsDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<PortsDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<PortsDTO>> addPorts(@Valid @NotNull @RequestBody PortsDTO portsDTO,
                                                        @RequestHeader Map<String, String> headers) {

        try {
            Ports portsAdded = portsService.addPorts(PortsMapper.INSTANCE.PortsDTOToPorts(portsDTO), headers);
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
                    PortsMapper.INSTANCE.PortsToPortsDTO(portsAdded), ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NullPointerException e) {
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e) {
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
    @PutMapping
    public ResponseEntity<APIResponse<PortsDTO>> updatePorts(@Valid @NotNull @RequestBody PortsDTO portsDTO, @RequestHeader Map<String,String> headers) {
        try {
            Ports portsUpdated = portsService.updatePorts(PortsMapper.INSTANCE.PortsDTOToPorts(portsDTO), headers);
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
                    PortsMapper.INSTANCE.PortsToPortsDTO(portsUpdated), ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NullPointerException e) {
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping
    public ResponseEntity<APIResponse<PortsDTO>> deletePorts(@Valid @NotNull @RequestBody PortsDTO portsDTO, @RequestHeader Map<String,String> headers){
        try {
            Ports portsUpdated = portsService.deletePorts(PortsMapper.INSTANCE.PortsDTOToPorts(portsDTO), headers);
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
                    PortsMapper.INSTANCE.PortsToPortsDTO(portsUpdated), ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        }
        catch (NullPointerException e) {
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<PortsDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
}
