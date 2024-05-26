package com.nscorp.obis.common;

import com.nscorp.obis.exception.SizeExceedException;

import java.util.Map;

public class UserId {
	
	 private UserId() {
		    throw new IllegalStateException("UserId class");
		  }

    public static void headerUserID(Map<String, String> headers){

    	String userId = headers.get("userid");
        if (userId == null || userId.isEmpty() || userId.trim().isEmpty()) {
            throw new NullPointerException("User Id should not be null, empty or blank.");
        }
        else if(userId.length() >= 9){
            throw new SizeExceedException("User Id size must be less than 9");
        }
    }
}
