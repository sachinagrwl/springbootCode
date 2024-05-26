package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.Map;

import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.dto.EquipmentCustomerRangeDTO;
import com.nscorp.obis.dto.StorageRatesDTO;
import com.nscorp.obis.response.data.PaginatedResponse;

import javax.validation.Valid;

public interface StorageRatesService {

    public PaginatedResponse<EquipmentCustomerRangeDTO> fetchEquipmentCustomerRange( Integer pageSize, Integer pageNumber) throws SQLException;
    public PaginatedResponse<StorageRatesDTO> fetchStorageRates(String selectRateType, String incExpDate, String shipPrimSix, String customerPrimSix, 
            String bnfPrimSix, String[] termId, String[] equipInit, String equipLgth, Integer pageSize, Integer pageNumber, String[] sort, String[] filter) throws SQLException ;
    
    public StorageRatesDTO addStorageRates(String selectRateType, String forceAdd, StorageRatesDTO storageRatesDTO, Map<String, String> headers);

    StorageRatesDTO updateStorageRate(@Valid StorageRatesDTO storageRatesDTO, Map<String, String> headers);
}

