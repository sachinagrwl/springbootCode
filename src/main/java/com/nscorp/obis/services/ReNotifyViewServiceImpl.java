package com.nscorp.obis.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nscorp.obis.domain.ReNotifyView;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.NotifyQueueRepository;
import com.nscorp.obis.repository.ReNotifyViewRepository;

@Service
public class ReNotifyViewServiceImpl implements ReNotifyViewService{
    private static final Logger logger = LoggerFactory.getLogger(ReNotifyViewServiceImpl.class);
    @Autowired
    ReNotifyViewRepository reNotifyViewRepository;
    @Autowired
    NotifyQueueRepository notifyQueueRepository;
    @Override
    public Page<ReNotifyView> getNotifications(double termId, Pageable pageable,String equipInit,Integer equipNbr,String notifyCustomerName){
        logger.info("termid : {} ,equipInit : {}, equipNbr : {}, Cn : {}",termId,equipInit,equipNbr,notifyCustomerName);
        Page<ReNotifyView> reNotifyViews = reNotifyViewRepository.searchAll(termId,equipInit,equipNbr,notifyCustomerName,pageable);
        if(reNotifyViews.getContent().isEmpty()){
            throw new NoRecordsFoundException("No Records Found");
        }
        return reNotifyViews;
    }
    public int getDelayCount(double termId, String custName){
        int countNotifyState = reNotifyViewRepository.getCountNotifyStateForTerminal(termId,custName);
        return countNotifyState;
    }
    public int getCount(double termId, String notifyState){
        Date updateTime = new Date(System.currentTimeMillis() - 3600 * 1000);
        int countNotifyState = notifyQueueRepository.getCountNotifyStateForTerminal(termId,notifyState,updateTime);
        return countNotifyState;
    }
}
