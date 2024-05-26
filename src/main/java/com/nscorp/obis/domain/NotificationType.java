package com.nscorp.obis.domain;

import java.util.Arrays;
import java.util.List;

public enum NotificationType {
    IMMEDIATE("II"), DELAY_DETAIL("DD"), DELAY_SUMMARY("DS"), DEFERRED_DETAIL("FD"), DEFERRED_SUMMARY("FS");

    private final String code;

    private NotificationType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public static NotificationType of(String value) {
    	NotificationType[] type = NotificationType.values();
    	List<NotificationType> tupeList =Arrays.asList(type);
    	for(NotificationType notificationType :tupeList) {
    		if(notificationType.getCode().equals(value)) {
    			return notificationType;
    			
    		}
    	}
    	return null;
    }
}

