package com.nscorp.obis.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nscorp.obis.domain.MoneyReceived;

import java.util.Map;

public interface MoneyReceivedService {
    Page<MoneyReceived> getMoneyReceived(Long termId,Long customerId,String equipInit,Integer equipNbr,String  termChkInd,String moneyChkInd,Pageable pageable);
    MoneyReceived addPayment(MoneyReceived moneyReceived, Map<String, String> headers);
    MoneyReceived updatePayment(MoneyReceived moneyReceived, Map<String, String> headers);
}
