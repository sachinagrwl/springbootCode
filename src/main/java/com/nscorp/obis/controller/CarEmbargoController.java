package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CarEmbargo;
import com.nscorp.obis.dto.CarEmbargoDTO;
import com.nscorp.obis.dto.mapper.CarEmbargoMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CarEmbargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = ControllerConstants.CAR_EMBARGO)
@Validated
public class CarEmbargoController {

    @Autowired
    CarEmbargoService carEmbargoService;

    @GetMapping
    public ResponseEntity<APIResponse<List<CarEmbargoDTO>>> getAllCarEmbargos(){

        try{
            List<CarEmbargoDTO> carEmbargoDTOList = Collections.emptyList();
            List<CarEmbargo> carEmbargoList = carEmbargoService.getAllCarEmbargo();
            if (carEmbargoList != null && !carEmbargoList.isEmpty()) {
                carEmbargoDTOList = carEmbargoList.stream().map(CarEmbargoMapper.INSTANCE::carEmbargoToCarEmbargoDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<CarEmbargoDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
                    carEmbargoDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<List<CarEmbargoDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<CarEmbargoDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

    @PostMapping
    public ResponseEntity<APIResponse<CarEmbargoDTO>> insertCarEmbargos(@Valid @RequestBody CarEmbargoDTO carEmbargoDTO, @RequestHeader Map<String, String> headers){
        try {
            CarEmbargo carEmbargo = CarEmbargoMapper.INSTANCE.carEmbargoDTOToCarEmbargo(carEmbargoDTO);
            CarEmbargo addCarEmbargo = carEmbargoService.insertCarEmbargo(carEmbargo,headers);
            CarEmbargoDTO addcarEmbargoDTO = CarEmbargoMapper.INSTANCE.carEmbargoToCarEmbargoDTO(addCarEmbargo);

            APIResponse<CarEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
                    addcarEmbargoDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        }catch (NoRecordsFoundException e) {
            APIResponse<CarEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordAlreadyExistsException e) {
            APIResponse<CarEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (Exception e) {
            APIResponse<CarEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping
    public ResponseEntity<APIResponse<CarEmbargoDTO>> updateCarEmbargo(@Valid @NotNull @RequestBody CarEmbargoDTO carEmbargoDTO, @RequestHeader Map<String,String> headers) {
        try {
            CarEmbargo carEmbargo = CarEmbargoMapper.INSTANCE.carEmbargoDTOToCarEmbargo(carEmbargoDTO);
            CarEmbargo carEmbargoUpdate = carEmbargoService.updateCarEmbargo(carEmbargo, headers);
            CarEmbargoDTO updateCarEmbargo = CarEmbargoMapper.INSTANCE.carEmbargoToCarEmbargoDTO(carEmbargoUpdate);
            APIResponse<CarEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateCarEmbargo, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<CarEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (InvalidDataException  | RecordNotAddedException e) {
            APIResponse<CarEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        }  catch (NullPointerException e){
            APIResponse<CarEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<CarEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping
    public ResponseEntity<List<APIResponse<CarEmbargoDTO>>> deleteCarEmbargo(@Valid @NotNull @RequestBody List<CarEmbargoDTO> carEmbargoDTOList){
        List<APIResponse<CarEmbargoDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (carEmbargoDTOList != null && !carEmbargoDTOList.isEmpty()) {
            response = carEmbargoDTOList.stream().map(carEmbargoObjDto -> {
                        APIResponse<CarEmbargoDTO> singleDtoDelResponse;
                        try {
                            carEmbargoService.deleteCarEmbargo(CarEmbargoMapper.INSTANCE.carEmbargoDTOToCarEmbargo(carEmbargoObjDto));
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),carEmbargoObjDto, ResponseStatusCode.SUCCESS);
                        } catch (Exception e) {
                            errorCount.incrementAndGet();
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                        }
                        return singleDtoDelResponse;
                    })
                    .collect(Collectors.toList());
        } else {
            response = Collections.emptyList();
        }

        if (errorCount.get() == 0 && response.size() > 0) { // No errors and atleast 1 success
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (response.size() > errorCount.get()) { // Partial success
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
