package com.nscorp.obis.services;

import com.nscorp.obis.domain.CIFExcpView;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CashInFistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CashInFistServiceImpl implements CashInFistService{

    @Autowired
    CashInFistRepository cashInFistRepository;
    @Override
    public List<CIFExcpView> getCashData() {
        List<CIFExcpView> cashList = cashInFistRepository.findAllByCustomerNameIsNotNullOrderByCustomerNameAsc();
        if(cashList.isEmpty()){
            throw new NoRecordsFoundException("No Records Found For Cash Exceptions!");
        }
        return cashList;
    }
}
