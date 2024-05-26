package com.nscorp.obis.response.handler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.nscorp.obis.exception.Error;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.response.data.ResponseStatusCode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice()
public class GlobalResponseHandler {


	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Object handleContraintViolationException(ConstraintViolationException ex) {

		List<Error> errorList = new ArrayList<Error>();

		int errorDetail = 0;
		for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
			try {
				errorDetail = ErrorCodes.valueOf(error.toString().toUpperCase()).getStatusCode();

			} catch (IllegalArgumentException e) {
				errorDetail = ErrorCodes.SIZE.getStatusCode();
			}
			errorList.add(new Error(error.getPropertyPath().toString(), errorDetail, error.getMessage()));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(Arrays.asList("Validation Error!"), errorList, ResponseStatusCode.FAILURE));

	}


	@ExceptionHandler(RecordAlreadyExistsException.class)
	public Object RecordAlreadyExistsExceptionHandling(RecordAlreadyExistsException exception) {
		return new ResponseObject(Arrays.asList(exception.getMessage()), ResponseStatusCode.FAILURE);
	}

	@ResponseStatus(HttpStatus.LENGTH_REQUIRED)
	@ExceptionHandler(SizeExceedException.class)
	public Object RecordAlreadyExistsExceptionHandling(SizeExceedException sizeLimitExceedException) {
		return new ResponseObject(Arrays.asList(sizeLimitExceedException.getMessage()), ResponseStatusCode.FAILURE);
	}

	@ExceptionHandler(RecordNotAddedException.class)
	public Object RecordAlreadyExistsExceptionHandling(RecordNotAddedException recordNotAddedException) {
		return new ResponseObject(Arrays.asList(recordNotAddedException.getMessage()), ResponseStatusCode.FAILURE);
	}

	//	@ResponseStatus(value = HttpStatus.OK)
	@JsonIgnoreProperties(value = "true")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Object MethodArgumentNotValidExceptionhandling(MethodArgumentNotValidException ex) {

		List<Error> errorList = new ArrayList<Error>();

		int errorDetail = 0;
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			try {
				errorDetail = ErrorCodes.valueOf(error.getCode().toUpperCase()).getStatusCode();

			} catch (IllegalArgumentException e) {
				errorDetail = ErrorCodes.VALIDATIONERROR.getStatusCode();
			}
			if (error instanceof FieldError) {
				errorList.add(new Error(error.getObjectName() + "." + ((FieldError) error).getField(), errorDetail, error.getDefaultMessage()));
			} else {
				errorList.add(new Error(error.getObjectName(), errorDetail, error.getDefaultMessage()));
			}
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(Arrays.asList("Validation Error!"), errorList, ResponseStatusCode.FAILURE));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseObject handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		List<Error> errorList = new ArrayList<Error>();
		String errorDetails = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type ";
		if(ex.getRequiredType()==Long.class) {
			errorDetails+="Integer.";
		}
		if(ex.getRequiredType()==Double.class)
		{
			errorDetails+="Double.";
		}
		else {
			errorDetails+=ex.getRequiredType();
		}
		errorList.add(new Error(ErrorCodes.VALIDATIONERROR.getStatusCode(),errorDetails));
		return new ResponseObject(Arrays.asList("Validation Error!"),errorList, ResponseStatusCode.FAILURE);
	}


	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseObject handleMissingRequestParameter(MissingServletRequestParameterException ex) {
		return new ResponseObject(Arrays.asList(ex.getMessage()), ResponseStatusCode.FAILURE);
	}

	@ExceptionHandler(InvalidFormatException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseObject handleInvalidFormatException(InvalidFormatException ex) {
		List<Error> errorList = new ArrayList<Error>();
                    
		String errorDetails = null;

		if(ex.getTargetType().isEnum()) {
			errorDetails = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be"
					+ " one of: %s.",ex.getValue(),ex.getPath().get(0).getFieldName(),
					Arrays.toString(ex.getTargetType().getEnumConstants()));
		} else if(ex.getMessage().contains("aarWhyMadeCd")){
			errorDetails= "Invalid value for aarWhyMadeCd. It should be a numeric value.";
		}
		else {
			errorDetails = ex.getLocalizedMessage();
		}
		errorList.add(new Error(ErrorCodes.VALIDATIONERROR.getStatusCode(),errorDetails));
		return new ResponseObject(Arrays.asList("Validation Error!"),errorList, ResponseStatusCode.FAILURE);
	}

	@ExceptionHandler(MismatchedInputException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseObject handleMismatchedInputException(MismatchedInputException ex) {
		List<Error> errorList = new ArrayList<Error>();
		errorList.add(new Error(ErrorCodes.VALIDATIONERROR.getStatusCode(),ex.getMessage()));
		return new ResponseObject(Arrays.asList("Validation Error!"),errorList, ResponseStatusCode.FAILURE);
	}
	
	@ExceptionHandler(InvalidDataException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseObject> InvalidDataExceptionHandling(InvalidDataException exception) {
		List<Error> errorList = new ArrayList<Error>();
		errorList.add(new Error(ErrorCodes.VALIDATIONERROR.getStatusCode(),exception.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(Arrays.asList("Validation Error!"),errorList, ResponseStatusCode.FAILURE));
    }
}



