package com.nscorp.obis.controller;

import com.nscorp.obis.domain.CIFExcpView;
import com.nscorp.obis.dto.CIFExcpViewDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CashInFistServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CashInFistControllerTest {

    @Mock
    CashInFistServiceImpl cashInFistServiceimpl;

    @InjectMocks
    CashInFistController cashInFistController;

    CIFExcpView cash;

    List<CIFExcpView> cashList;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cash = new CIFExcpView();
        cash.setCustomerName("abc");
        cash.setPrimarySix("1234");
        cashList = new ArrayList<>();
        cashList.add(cash);
    }

    @AfterEach
    void tearDown() throws Exception{
        cash=null;
        cashList=null;
    }

    @Test
    void getAllCash() {
        when(cashInFistServiceimpl.getCashData()).thenReturn(cashList);
        ResponseEntity<APIResponse<List<CIFExcpViewDTO>>> getCashList = cashInFistController.getAllCashData();
        assertEquals(getCashList.getStatusCodeValue(),200);
    }

    @Test
    void getAllCashException() {
        when(cashInFistServiceimpl.getCashData()).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<CIFExcpViewDTO>>> getCash = cashInFistController.getAllCashData();
        assertEquals(getCash.getStatusCodeValue(),500);
    }

    @Test
    void getAllCashNoRecordsFoundException() {
        when(cashInFistServiceimpl.getCashData()).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<CIFExcpViewDTO>>> getCash = cashInFistController.getAllCashData();
        assertEquals(getCash.getStatusCodeValue(),404);
    }
}
