package com.nscorp.obis.domain;

import java.util.Arrays;
import java.util.List;

public enum NotificationMethod {
    EDI("EDI"), EMAIL("EMAIL"), FAX("FAX"), VOICE("VOICE");

    private final String code;

    private NotificationMethod(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public static NotificationMethod of(String value) {
	NotificationMethod[] method = NotificationMethod.values();
	List<NotificationMethod> methodList =Arrays.asList(method);
	for(NotificationMethod notificationMethod :methodList) {
		if(notificationMethod.getCode().equals(value)) {
			return notificationMethod;
			
		}
	}
	return null;
	
}
}

