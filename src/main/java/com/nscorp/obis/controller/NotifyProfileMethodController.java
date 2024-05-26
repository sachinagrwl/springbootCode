package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.NotifyProfileMethod;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.dto.mapper.NotifyProfileMethodMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.NotifyProfileMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/")
public class NotifyProfileMethodController {

	@Autowired(required = true)
	NotifyProfileMethodService notifyProfileMethodService;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NotifyProfileMethodController.class);

	@GetMapping(value = ControllerConstants.NOTIFY_PROFILE_METHOD)
	public  ResponseEntity<APIResponse<List<NotifyProfileMethodDTO>>> getNotifyProfileMethod(
			@Valid @RequestParam(name = "notificationName",  required = false) String notificationName,
			@RequestParam(name = "notificationMethod",  required = false) String notificationMethod,
			@RequestParam(name = "notificationType",  required = false) String notificationType,
			@RequestParam(name = "ediBox", required = false) String editBox,
			@RequestParam(name = "notifyAreaCode", required = false) Integer notifyAreaCode,
			@RequestParam(name = "notifyPrefix",  required = false) Integer notifyPrefix, 
			@RequestParam(name = "notifySuffix",  required = false) Integer notifySuffix,
			@RequestParam(name = "notifyPhoneExtension", required = false) Integer notifyPhoneExtension,
			@RequestParam(name = "notificationOrder",  required = false) String notificationOrder,
			@RequestParam(name = "microwaveInd",  required = false) Character microwaveInd,
			@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) throws SQLException {

		try {
			List<NotifyProfileMethod> notifyProfileMethods = notifyProfileMethodService
					.fetchNotifyProfileMethod(notificationName, notificationMethod, notificationType,
							editBox, notifyAreaCode, notifyPrefix, notifySuffix, notifyPhoneExtension, notificationOrder,
							microwaveInd, pageNo, pageSize);
			//List<NotifyProfileMethod> result = notifyProfileMethods.getContent();
			List<NotifyProfileMethodDTO> notifyProfileMethodDTOs = notifyProfileMethods.stream().map(NotifyProfileMethodMapper
					.INSTANCE::notifyProfileMethodToNotifyProfileMethodDTO).collect(Collectors.toList());
			APIResponse<List<NotifyProfileMethodDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieve data!"),notifyProfileMethodDTOs, 
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<NotifyProfileMethodDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e) {
			APIResponse<List<NotifyProfileMethodDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE.getStatusCode(), ResponseStatusCode.FAILURE.toString());
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<List<NotifyProfileMethodDTO>> responseObj= new APIResponse<>(Arrays.asList(e.getMessage()),ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<List<NotifyProfileMethodDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<NotifyProfileMethodDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
	/*This method is to add values*/
	@PostMapping(value = ControllerConstants.NOTIFY_PROFILE_METHOD)
	public ResponseEntity<APIResponse<NotifyProfileMethodDTO>> addNotifyProfileMethod(@Valid @RequestBody NotifyProfileMethodDTO notifyProfileMethodDTO, 
			@RequestHeader Map<String,String> headers) throws SQLException{
		try {
			NotifyProfileMethod notifyProfileMethod = NotifyProfileMethodMapper.INSTANCE.notifyProfileMethodDTOToNotifyProfileMethod(notifyProfileMethodDTO);
			NotifyProfileMethod notifyProfileMethodAdded = notifyProfileMethodService.insertNotifyProfileMethod(notifyProfileMethod,notifyProfileMethodDTO, headers);
			NotifyProfileMethodDTO addedNotifyProfileMethodDTO = NotifyProfileMethodMapper.INSTANCE.notifyProfileMethodToNotifyProfileMethodDTO(notifyProfileMethodAdded);
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), addedNotifyProfileMethodDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e){
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
		
	}
	
	/* This method is used to update values */
	@PutMapping(value = ControllerConstants.NOTIFY_PROFILE_METHOD)
	public ResponseEntity<APIResponse<NotifyProfileMethodDTO>> updateNotifyProfileMethod(@Valid @RequestBody
			NotifyProfileMethodDTO notifyProfileMethodDTO, @RequestHeader Map<String, String> headers) throws SQLException {
		try {
			NotifyProfileMethod notifyProfileMethod = notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, headers);
			NotifyProfileMethodDTO profileMethodDTO = NotifyProfileMethodMapper.INSTANCE.notifyProfileMethodToNotifyProfileMethodDTO
					(notifyProfileMethod);
			APIResponse<NotifyProfileMethodDTO> responseObject = new APIResponse<>(Arrays.asList("Successfully Data Updated!"), profileMethodDTO,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObject);
		} catch (NoRecordsFoundException e) {
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e) {
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE.getStatusCode(), ResponseStatusCode.FAILURE.toString());
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e) {
			APIResponse<NotifyProfileMethodDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
