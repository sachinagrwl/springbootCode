package com.nscorp.obis.services;

import com.nscorp.obis.domain.CIFExcpView;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CashInFistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CashFistServiceImplTest {

    @InjectMocks
    CashInFistServiceImpl cashInFistServiceimpl;

    @Mock
    CashInFistRepository cashInFistRepository;

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
    void testGetAllCash() {
        when(cashInFistRepository.findAllByCustomerNameIsNotNullOrderByCustomerNameAsc()).thenReturn(cashList);
        List<CIFExcpView> cashListEx = cashInFistServiceimpl.getCashData();
        assertEquals(cashList,cashListEx);
    }

    @Test
    void testGetAllCashException() {
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(cashInFistServiceimpl.getCashData()));
        Assertions.assertEquals("No Records Found For Cash Exceptions!", exception.getMessage());
    }
}
