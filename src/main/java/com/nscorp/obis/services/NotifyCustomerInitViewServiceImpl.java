package com.nscorp.obis.services;

import com.nscorp.obis.domain.CodeTableSelection;
import com.nscorp.obis.domain.NotifyCustomerInitView;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.NotifyCustomerInitViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyCustomerInitViewServiceImpl implements NotifyCustomerInitViewService{

    @Autowired
    NotifyCustomerInitViewRepository notifyCustomerInitViewRepo;

    @Override
    public List<NotifyCustomerInitView> getAllCustomerInitialsView() {
        List<NotifyCustomerInitView> custInitView = notifyCustomerInitViewRepo.findAllByOrderByCustomerNameAsc();
        if(custInitView.isEmpty()) {
            throw new NoRecordsFoundException("No Records are found!");
        }
        return custInitView;
    }
}
