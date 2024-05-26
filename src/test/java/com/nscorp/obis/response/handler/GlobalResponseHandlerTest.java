package com.nscorp.obis.response.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.response.data.ResponseStatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import javax.validation.*;
import javax.validation.metadata.ConstraintDescriptor;
import java.beans.PropertyEditor;
import java.util.*;

class GlobalResponseHandlerTest {

	@InjectMocks
	GlobalResponseHandler globalResponseHandler;

	@Mock
	ConstraintViolationException constraintViolationException;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp(){
		MockitoAnnotations.initMocks(this);
		globalResponseHandler = new GlobalResponseHandler();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testHandleContraintViolationException() {
		Set<ConstraintViolation<?>> violations = new HashSet<>();
		ConstraintViolation<?> violation =  new ConstraintViolation<Object>() {
			@Override
			public String getMessage() {
				return "Validation Error!";
			}

			@Override
			public String getMessageTemplate() {
				return null;
			}

			@Override
			public Object getRootBean() {
				return null;
			}

			@Override
			public Class<Object> getRootBeanClass() {
				return null;
			}

			@Override
			public Object getLeafBean() {
				return null;
			}

			@Override
			public Object[] getExecutableParameters() {
				return new Object[0];
			}

			@Override
			public Object getExecutableReturnValue() {
				return null;
			}

			@Override
			public Path getPropertyPath() {
				return new Path() {
					@Override
					public Iterator<Node> iterator() {
						return new Iterator<Node>() {
							@Override
							public boolean hasNext() {
								return true;
							}

							@Override
							public Node next() {
								return new Node() {
									@Override
									public String getName() {
										return "Error!";
									}

									@Override
									public boolean isInIterable() {
										return false;
									}

									@Override
									public Integer getIndex() {
										return null;
									}

									@Override
									public Object getKey() {
										return null;
									}

									@Override
									public ElementKind getKind() {
										return null;
									}

									@Override
									public <T extends Node> T as(Class<T> nodeType) {
										return null;
									}
								};
							}
						};
					}
				};
			}

			@Override
			public Object getInvalidValue() {
				return null;
			}

			@Override
			public ConstraintDescriptor<?> getConstraintDescriptor() {
				return null;
			}

			@Override
			public <U> U unwrap(Class<U> type) {
				return null;
			}
		};
		violations.add(violation);
		constraintViolationException = new ConstraintViolationException("Validation Error!",violations);
		ResponseEntity<Object> response = (ResponseEntity<Object>) globalResponseHandler.handleContraintViolationException(constraintViolationException);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(response.getStatusCodeValue(), 400);
	}

	@Test
	void testRecordAlreadyExistsExceptionHandlingRecordAlreadyExistsException() {
		RecordAlreadyExistsException mockException = mock(RecordAlreadyExistsException.class);
		when(mockException.getMessage()).thenReturn("Record already exists");
		ResponseObject response = (ResponseObject) globalResponseHandler.RecordAlreadyExistsExceptionHandling(mockException);
		assertEquals(ResponseStatusCode.FAILURE.getStatusCode(), response.getStatusCode());
	}

	@Test
	void testRecordAlreadyExistsExceptionHandlingSizeExceedException() {
		SizeExceedException mockException = mock(SizeExceedException.class);
		when(mockException.getMessage()).thenReturn("Size exceed exception");
		ResponseObject response = (ResponseObject) globalResponseHandler.RecordAlreadyExistsExceptionHandling(mockException);
		assertEquals(ResponseStatusCode.FAILURE.getStatusCode(), response.getStatusCode());
	}

	@Test
	void testRecordAlreadyExistsExceptionHandlingRecordNotAddedException() {
		RecordNotAddedException mockException = mock(RecordNotAddedException.class);
		when(mockException.getMessage()).thenReturn("Record not added");
		ResponseObject response = (ResponseObject) globalResponseHandler.RecordAlreadyExistsExceptionHandling(mockException);
		assertEquals(ResponseStatusCode.FAILURE.getStatusCode(), response.getStatusCode());
	}

	@Test
	void testMethodArgumentNotValidExceptionhandling() {
		int parameterIndex = 0;
		MethodParameter methodParameter = mock(MethodParameter.class);
		BindingResult bindingResult = new BindingResult() {
			@Override
			public Object getTarget() {
				return null;
			}

			@Override
			public Map<String, Object> getModel() {
				return null;
			}

			@Override
			public Object getRawFieldValue(String field) {
				return null;
			}

			@Override
			public PropertyEditor findEditor(String field, Class<?> valueType) {
				return null;
			}

			@Override
			public PropertyEditorRegistry getPropertyEditorRegistry() {
				return null;
			}

			@Override
			public String[] resolveMessageCodes(String errorCode) {
				return new String[0];
			}

			@Override
			public String[] resolveMessageCodes(String errorCode, String field) {
				return new String[0];
			}

			@Override
			public void addError(ObjectError error) {

			}

			@Override
			public String getObjectName() {
				return null;
			}

			@Override
			public void setNestedPath(String nestedPath) {

			}

			@Override
			public String getNestedPath() {
				return null;
			}

			@Override
			public void pushNestedPath(String subPath) {

			}

			@Override
			public void popNestedPath() throws IllegalStateException {

			}

			@Override
			public void reject(String errorCode) {

			}

			@Override
			public void reject(String errorCode, String defaultMessage) {

			}

			@Override
			public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

			}

			@Override
			public void rejectValue(String field, String errorCode) {

			}

			@Override
			public void rejectValue(String field, String errorCode, String defaultMessage) {

			}

			@Override
			public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

			}

			@Override
			public void addAllErrors(Errors errors) {

			}

			@Override
			public boolean hasErrors() {
				return false;
			}

			@Override
			public int getErrorCount() {
				return 0;
			}

			@Override
			public List<ObjectError> getAllErrors() {
				List<ObjectError> errors = new ArrayList<>();
				errors.add(new ObjectError("myObject",new String[]{"Userid should be present"},new Object[]{"userid"}, "400"));
				return errors;
			}

			@Override
			public boolean hasGlobalErrors() {
				return false;
			}

			@Override
			public int getGlobalErrorCount() {
				return 0;
			}

			@Override
			public List<ObjectError> getGlobalErrors() {
				return null;
			}

			@Override
			public ObjectError getGlobalError() {
				return null;
			}

			@Override
			public boolean hasFieldErrors() {
				return false;
			}

			@Override
			public int getFieldErrorCount() {
				return 0;
			}

			@Override
			public List<FieldError> getFieldErrors() {
				return null;
			}

			@Override
			public FieldError getFieldError() {
				return null;
			}

			@Override
			public boolean hasFieldErrors(String field) {
				return false;
			}

			@Override
			public int getFieldErrorCount(String field) {
				return 0;
			}

			@Override
			public List<FieldError> getFieldErrors(String field) {
				return null;
			}

			@Override
			public FieldError getFieldError(String field) {
				return null;
			}

			@Override
			public Object getFieldValue(String field) {
				return null;
			}

			@Override
			public Class<?> getFieldType(String field) {
				return null;
			}
		};
		MethodArgumentNotValidException mockException = new MethodArgumentNotValidException(methodParameter,bindingResult);
		ResponseEntity<Object> response = (ResponseEntity<Object>) globalResponseHandler.MethodArgumentNotValidExceptionhandling(mockException);
		assertEquals(400, response.getStatusCodeValue());
	}

	@Test
	void testHandleTypeMismatchException() {
		MethodArgumentTypeMismatchException mockException = mock(MethodArgumentTypeMismatchException.class);
		when(mockException.getMessage()).thenReturn("Type exceed exception");
		ResponseObject response = (ResponseObject) globalResponseHandler.handleTypeMismatchException(mockException);
		assertEquals(ResponseStatusCode.FAILURE.getStatusCode(), response.getStatusCode());
	}

	@Test
	void testHandleTypeMismatchExceptionRequiredTypeDouble() {
		String param ="Test";
		Object object = mock(Object.class);
		MethodArgumentTypeMismatchException mockException = new MethodArgumentTypeMismatchException(object,Double.class,"Test",mock(MethodParameter.class),mock(Throwable.class));
		when(mockException.getMessage()).thenReturn("Type exceed exception");
		ResponseObject response = (ResponseObject) globalResponseHandler.handleTypeMismatchException(mockException);
		assertEquals(ResponseStatusCode.FAILURE.getStatusCode(), response.getStatusCode());
	}

	@Test
	void testHandleTypeMismatchExceptionRequiredTypeLong() {
		String param ="Test";
		Object object = mock(Object.class);
		MethodArgumentTypeMismatchException mockException = new MethodArgumentTypeMismatchException(object,Long.class,"Test",mock(MethodParameter.class),mock(Throwable.class));
		when(mockException.getMessage()).thenReturn("Type exceed exception");
		ResponseObject response = (ResponseObject) globalResponseHandler.handleTypeMismatchException(mockException);
		assertEquals(ResponseStatusCode.FAILURE.getStatusCode(), response.getStatusCode());
	}

	@Test
	void testHandleMissingRequestParameter() {
		MissingServletRequestParameterException mockException = mock(MissingServletRequestParameterException.class);
		when(mockException.getMessage()).thenReturn("Missing Servlet Request parameter exception");
		ResponseObject response = (ResponseObject) globalResponseHandler.handleMissingRequestParameter(mockException);
		assertEquals(ResponseStatusCode.FAILURE.getStatusCode(), response.getStatusCode());
	}

	@Test
	void testHandleInvalidFormatException() {
		Object value = new Object();
		Class<?> targetType = Enum.class;
		InvalidFormatException mockException = new InvalidFormatException("Validation Error!",value, targetType);
		ResponseObject response = globalResponseHandler.handleInvalidFormatException(mockException);
		assertEquals(ResponseStatusCode.FAILURE.getStatusCode(), response.getStatusCode());
	}

	@Test
	void testHandleMismatchedInputException() {
		String json="{\"rollNumber\":21 , \"firstName\":\"Saurabh\" , \"lastName\":\"Gupta\",  \"dob\":\"12/13/1985\"}";
		Date date;
		ObjectMapper mapper = new ObjectMapper();
		try {
			date = mapper.readValue(json, Date.class);

		} catch (InvalidFormatException ex) {
			globalResponseHandler.handleInvalidFormatException(ex);
		} catch(MismatchedInputException e){
			globalResponseHandler.handleMismatchedInputException(e);
		} catch(Exception e){

		}
	}

	@Test
	void testInvalidDataExceptionHandling() {
		InvalidDataException mockException = mock(InvalidDataException.class);
		when(mockException.getMessage()).thenReturn("Invalid data exception");
		ResponseEntity<ResponseObject> response = globalResponseHandler.InvalidDataExceptionHandling(mockException);
		assertEquals(400, response.getStatusCodeValue());
	}

}
