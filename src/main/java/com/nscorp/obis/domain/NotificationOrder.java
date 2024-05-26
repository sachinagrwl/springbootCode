package com.nscorp.obis.domain;

import java.util.Arrays;
import java.util.List;

public enum NotificationOrder {
    PRIMARY("P"), SECONDARY("S");

    private final String code;

    private NotificationOrder(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public static NotificationOrder of(String value) {
	NotificationOrder[] order = NotificationOrder.values();
	List<NotificationOrder> orderList =Arrays.asList(order);
	for(NotificationOrder notification :orderList) {
		if(notification.getCode().equals(value)) {
			return notification;
			
		}
	}
	return null;
	
}
}

