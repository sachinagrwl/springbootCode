package com.nscorp.obis.services;

import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.domain.MoneyReceived;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.MoneyReceivedRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Service
public class MoneyReceivedServiceImpl implements MoneyReceivedService {

    @Autowired
    MoneyReceivedRepository moneyReceivedRepo;
    @Autowired
    TerminalRepository terminalRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    GenericCodeUpdateRepository genericCodeUpdateRepo;

    @Override
    public Page<MoneyReceived> getMoneyReceived(Long termId, Long customerId,String equipInit,Integer equipNbr,String supressTerm,String supressFinal,  Pageable pageable) {
    if(supressTerm==null){
        supressTerm="N";
    }
        if(supressFinal==null){
            supressFinal="N";
        }
        List<String> termChkList = new ArrayList<>();
        List<String> finalChkList = new ArrayList<>();
        termChkList.add("Y");
        termChkList.add("N");

        finalChkList.add("Y");
        finalChkList.add("N");
        if(supressTerm.equalsIgnoreCase("N") &&
                supressFinal.equalsIgnoreCase("Y")){
            finalChkList.remove("Y");
        } else if(supressTerm.equalsIgnoreCase("Y") &&
                supressFinal.equalsIgnoreCase("N")){
            termChkList.remove("Y");
        } else if(supressTerm.equalsIgnoreCase("Y") &&
                supressFinal.equalsIgnoreCase("Y")){
            finalChkList.remove("Y");
            termChkList.remove("Y");
        }

        Page<MoneyReceived> moneyReceived = moneyReceivedRepo.searchAll(termId,customerId,equipInit,
                equipNbr,termChkList, finalChkList,pageable);

        if (moneyReceived.getContent().isEmpty()) {
            throw new NoRecordsFoundException("No Records Found");
        }

        return moneyReceived;
    }

    @Override
    public MoneyReceived addPayment(MoneyReceived moneyReceived, Map<String, String> headers) {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        UserId.headerUserID(headers);
        Long moneyReceivedId = moneyReceivedRepo.SGK();
        moneyReceived.setMoneyTdrId(moneyReceivedId);
        moneyReceived.setPaidDtTm(new Timestamp(System.currentTimeMillis()));
        moneyReceived.setUversion("!");
        moneyReceived.setUpdateExtensionSchema(headers.get("extensionschema"));
        moneyReceived.setCreateUserId(headers.get("userid"));
        moneyReceived.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        moneyReceived.setUpdateUserId(headers.get("userid"));
        moneyReceived.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
        validateMoneyReceived(moneyReceived);
        MoneyReceived moneyReceived1 = moneyReceivedRepo.save(moneyReceived);
        if(moneyReceived1.getCustomerId()!=null)
            moneyReceived1.setCustomer(customerRepo.findByCustomerId(moneyReceived1.getCustomerId()));
        if(moneyReceived1.getPaidByCustId()!=null)
            moneyReceived1.setPaidByCustomer(customerRepo.findByCustomerId(moneyReceived1.getPaidByCustId()));
        moneyReceived1.setPaidDtTmStr(moneyReceived1.getPaidDtTm().toString());
        if (moneyReceived1 == null)
            throw new RecordNotAddedException("Record Not added to Database");
        return moneyReceived1;
    }

    @Override
    public MoneyReceived updatePayment(MoneyReceived moneyReceived, Map<String, String> headers) {
        UserId.headerUserID(headers);
        if (moneyReceived.getMoneyTdrId() == null)
            throw new NoRecordsFoundException("MoneyTdrId should not be null");
        if (!moneyReceivedRepo.existsByMoneyTdrId(moneyReceived.getMoneyTdrId())) {
            throw new NoRecordsFoundException("No Record Found!");
        }
        moneyReceived.setUversion("!");
        moneyReceived.setUpdateExtensionSchema(headers.get("extensionschema"));
        moneyReceived.setUpdateUserId(headers.get("userid"));
        moneyReceived.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
        validateMoneyReceived(moneyReceived);
        MoneyReceived moneyReceived1 = moneyReceivedRepo.save(moneyReceived);
        if (moneyReceived1 == null)
            throw new RecordNotAddedException("Record Not updated to Database");
        return moneyReceived1;
    }

    private void validateMoneyReceived(MoneyReceived moneyReceived) {
        if (!terminalRepo.existsByTerminalId(moneyReceived.getTermId()))
            throw new InvalidDataException("Invalid TerminalId");
        if (!customerRepo.existsByCustomerId(moneyReceived.getCustomerId()))
            throw new InvalidDataException("Invalid CustomerId");
        if (moneyReceived.getPaidByCustId() != null)
            if (!customerRepo.existsByCustomerId(moneyReceived.getPaidByCustId()))
                throw new InvalidDataException("Invalid PaidBy CustomerId");
        List<GenericCodeUpdate> tpPayList = genericCodeUpdateRepo.findByGenericTable("TP_PAY");
        List<GenericCodeUpdate> tpSvcList = genericCodeUpdateRepo.findByGenericTable("TP_SVC");
        if (tpPayList != null && !checkGenericCodeValidation(tpPayList, moneyReceived.getTpPayment()))
            throw new InvalidDataException("Invalid TpPayment code, detail for list");
        if (tpSvcList != null && !checkGenericCodeValidation(tpSvcList, moneyReceived.getTpSvcCd()))
            throw new InvalidDataException("Invalid TpSvcCode code, detail for list");
    }

    private boolean checkGenericCodeValidation(List<GenericCodeUpdate> genericCodeUpdates, String code) {
        boolean flag = false;
        if (genericCodeUpdates != null) {
            for (GenericCodeUpdate genericCodeUpdate : genericCodeUpdates) {
                if (genericCodeUpdate.getGenericTableCode().equalsIgnoreCase(code))
                    flag = true;
            }
        }
        return flag;
    }
}
