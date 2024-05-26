package com.nscorp.obis.exception;


//@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class RecordAlreadyExistsException extends RuntimeException {
	
    
	private static final long serialVersionUID = 1L;
		

        public RecordAlreadyExistsException(String message) {
            super(message);
        }
        public RecordAlreadyExistsException() {
        }
    }
  
        
    	


