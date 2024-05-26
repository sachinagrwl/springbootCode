package com.nscorp.obis.services;

import com.nscorp.obis.domain.NotifyCustomerInit;

import java.util.List;
import java.util.Map;

public interface NotifyCustomerInitService {

	List<NotifyCustomerInit> getCustomerInitials(Long custId);

	NotifyCustomerInit addNotifyCustomerInit(NotifyCustomerInit notifyCustomerInit, Map<String, String> headers);

	NotifyCustomerInit deleteNotifyCustomerInit(NotifyCustomerInit notifyCustomerInit);

	List<NotifyCustomerInit> updateNotifyCustomerInit(NotifyCustomerInit notifyCustomerInit, Map<String, String> headers);
}
