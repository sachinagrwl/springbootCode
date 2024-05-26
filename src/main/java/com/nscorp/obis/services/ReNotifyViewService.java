package com.nscorp.obis.services;

import com.nscorp.obis.domain.ReNotifyView;
import com.nscorp.obis.dto.ReNotifyViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReNotifyViewService {
    Page<ReNotifyView> getNotifications(double termId, Pageable pageable,String equipInit,Integer equipNbr,String notifyCustomerName);
    int getCount(double termId, String notifyState);
    int getDelayCount(double termId, String custName);
}