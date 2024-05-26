package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CorporateCustomerDetailRepository;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.InterChangePartyRepository;

@Service
public class CorporateCustomerMaintenanceServiceImpl implements CorporateCustomerMaintenanceService {

    @Autowired
    CorporateCustomerRepository corporateCustomerMaintenanceRepository;

    @Autowired
    InterChangePartyRepository interChangePartyRepo;

    @Autowired
    CorporateCustomerDetailRepository corporateCustDetailRepo;

    @Autowired
    CustomerRepository customerRepo;



    public List<CorporateCustomer> getCorporateCustomerData() {
        List<CorporateCustomer> corporateCustomerMaintenances = corporateCustomerMaintenanceRepository.findAll();
        return corporateCustomerMaintenances;
    }

    @Override
    public CorporateCustomer updateCorporateCustomer(CorporateCustomer corporateCustomerObj,
                                                     Map<String, String> headers) {
        // TODO Auto-generated method stub
        UserId.headerUserID(headers);
        if (corporateCustomerMaintenanceRepository
                .existsByCorporateCustomerId(corporateCustomerObj.getCorporateCustomerId())) {
            CorporateCustomer corporateCustomer = corporateCustomerMaintenanceRepository
                    .findByCorporateCustomerId(corporateCustomerObj.getCorporateCustomerId());
            corporateCustomer.setCreateDateTime(corporateCustomerObj.getCreateDateTime());
            corporateCustomer.setCreateUserId(corporateCustomerObj.getCreateUserId());
            corporateCustomer.setUpdateUserId(headers.get(CommonConstants.USER_ID));
            corporateCustomer.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            corporateCustomer.setUversion("!");

            if (corporateCustomerObj.getCorporateLongName() == null) {
                throw new NoRecordsFoundException("W-CORP_LONG_NM Required");
            } else {
                if (corporateCustomer.getCorporateLongName().equals(corporateCustomerObj.getCorporateLongName())) {
                    corporateCustomer.setCorporateLongName(corporateCustomerObj.getCorporateLongName());

                } else if (corporateCustomerMaintenanceRepository
                        .existsByCorporateLongName(corporateCustomerObj.getCorporateLongName())) {
                    throw new RecordAlreadyExistsException("Long Name already exists: "+corporateCustomerObj.getCorporateLongName());
                } else
                    corporateCustomer.setCorporateLongName(corporateCustomerObj.getCorporateLongName());
            }

            if (corporateCustomerObj.getCorporateShortName() == null) {
                throw new NoRecordsFoundException("W-CORP_SHORT_NM Required");
            } else {
                if (corporateCustomer.getCorporateShortName().equals(corporateCustomerObj.getCorporateShortName())) {
                    corporateCustomer.setCorporateShortName(corporateCustomerObj.getCorporateShortName());

                } else if (corporateCustomerMaintenanceRepository
                        .existsByCorporateShortName(corporateCustomerObj.getCorporateShortName())) {
                    throw new RecordAlreadyExistsException("Short Name already exists: "+corporateCustomerObj.getCorporateShortName());
                } else
                    corporateCustomer.setCorporateShortName(corporateCustomerObj.getCorporateShortName());

            }
            corporateCustomer.setCustomerId(corporateCustomerObj.getCustomerId());
            if (corporateCustomerObj.getIcghCd() != null) {
                if (interChangePartyRepo.existsByIchgCode(corporateCustomerObj.getIcghCd())) {
                    corporateCustomer.setIcghCd(corporateCustomerObj.getIcghCd());
                } else
                    throw new NoRecordsFoundException("Interchange party not found, reenter");

            } else {
                corporateCustomer.setIcghCd(corporateCustomerObj.getIcghCd());
            }

            corporateCustomer.setAccountManager(corporateCustomerObj.getAccountManager());
            corporateCustomer.setScac(corporateCustomerObj.getScac());
            corporateCustomer.setSecondaryLob(corporateCustomerObj.getSecondaryLob());
            corporateCustomer.setPrimaryLob(corporateCustomerObj.getPrimaryLob());

            corporateCustomer.setTerminalFeedEnabled(corporateCustomerObj.getTerminalFeedEnabled());

            CorporateCustomer corpCust = corporateCustomerMaintenanceRepository.save(corporateCustomer);
            return corpCust;
        } else
            throw new NoRecordsFoundException("No Record Found");
    }

    @Override
    public CorporateCustomer deleteCorporateCustomerData(CorporateCustomer object) {

        if (corporateCustomerMaintenanceRepository.existsByCorporateCustomerId(object.getCorporateCustomerId())) {
            CorporateCustomer cc = corporateCustomerMaintenanceRepository
                    .findByCorporateCustomerId(object.getCorporateCustomerId());
            corporateCustomerMaintenanceRepository.deleteById(object.getCorporateCustomerId());
            return cc;

        } else {
            throw new RecordNotDeletedException("Record Not Found!");
        }

    }

    @Override
    public CorporateCustomer addCorporateCustomer(CorporateCustomer corporateCustomerObj, Map<String, String> headers) {
        UserId.headerUserID(headers);
		CorporateCustomer corpCust = new CorporateCustomer();
        if (corporateCustomerObj.getCorporateLongName() == null) {
            throw new NoRecordsFoundException("W-CORP_LONG_NM Required");
        } else {
            if (corporateCustomerMaintenanceRepository
                    .existsByCorporateLongName(corporateCustomerObj.getCorporateLongName())) {
                throw new RecordAlreadyExistsException("Long Name already exists: "+corporateCustomerObj.getCorporateLongName());
            } else {
                corporateCustomerObj.setCorporateLongName(corporateCustomerObj.getCorporateLongName());
            }
        }
        if (corporateCustomerObj.getCorporateShortName() == null) {
            throw new NoRecordsFoundException("W-CORP_SHORT_NM Required");
        } else {
            if (corporateCustomerMaintenanceRepository
                    .existsByCorporateShortName(corporateCustomerObj.getCorporateShortName())) {
                throw new RecordAlreadyExistsException("Short Name already exists: "+corporateCustomerObj.getCorporateShortName());
            } else {
                corporateCustomerObj.setCorporateShortName(corporateCustomerObj.getCorporateShortName());
            }
        }
        if (corporateCustomerObj.getCustomerId() != null
                && customerRepo.existsByCustomerId(corporateCustomerObj.getCustomerId())) {
            corporateCustomerObj.setCustomerId(corporateCustomerObj.getCustomerId());
        } else {
            throw new NoRecordsFoundException("Invalid Customer Details");
        }
        Long corporateCustomerId = corporateCustomerMaintenanceRepository.SGK();
        corporateCustomerObj.setCorporateCustomerId(corporateCustomerId);
        System.out.println("corp cust Id is" + corporateCustomerObj.getCorporateCustomerId());
        corporateCustomerObj.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        corporateCustomerObj.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        corporateCustomerObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
        corporateCustomerObj.setUversion("!");
        if (corporateCustomerObj.getIcghCd() != null
                && interChangePartyRepo.existsByIchgCode(corporateCustomerObj.getIcghCd())) {
            corporateCustomerObj.setIcghCd(corporateCustomerObj.getIcghCd());
        }
        List<CorporateCustomerDetail> corporateCustomerDetailList = new ArrayList<>();
        List<CorporateCustomerDetail> corporateCustomerDetailList1 = new ArrayList<>();
        if (corporateCustomerObj.getCorporateCustomerDetail() != null
                && !corporateCustomerObj.getCorporateCustomerDetail().isEmpty()
                && corporateCustomerObj.getCorporateCustomerDetail().size() >= 1) {
            for (CorporateCustomerDetail obj : corporateCustomerObj.getCorporateCustomerDetail()) {
                CorporateCustomerDetail detail = new CorporateCustomerDetail();
                if (obj.getCorpCust6() == null && corporateCustomerObj.getCorporateCustomerDetail().size() == 1) {
                    throw new NoRecordsFoundException("Corporate Customer must have at least one Primary Six");
                }
                if(corporateCustDetailRepo.existsByCorpCust6(obj.getCorpCust6()))
                    throw new NoRecordsFoundException("Primary Six Customer already set for another Corporate Customer");
                        detail.setCorpCustId(corporateCustomerId);
                        detail.setCorpCust6(obj.getCorpCust6());
                        detail.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
                        detail.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
                        detail.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
                        detail.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
                        detail.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
                        detail.setUversion("!");
                        corporateCustomerDetailList.add(detail);
            }

            if (corporateCustomerMaintenanceRepository.existsByCustomerId(corporateCustomerObj.getCustomerId())) {
                throw new RecordAlreadyExistsException("Customer Name/Number already set for another Corporate Customer");
            } else {
                corpCust = corporateCustomerMaintenanceRepository.save(corporateCustomerObj);
            }
            corporateCustomerDetailList1 = corporateCustDetailRepo.saveAll(corporateCustomerDetailList);
        }
        corpCust.setCorporateCustomerDetail(corporateCustomerDetailList1);
        return corpCust;
    }
}
