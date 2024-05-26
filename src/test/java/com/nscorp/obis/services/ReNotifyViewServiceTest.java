package com.nscorp.obis.services;

import com.nscorp.obis.domain.ReNotifyView;
import com.nscorp.obis.dto.ReNotifyViewDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.NotifyQueueRepository;
import com.nscorp.obis.repository.ReNotifyViewRepository;
import com.nscorp.obis.response.data.PaginationWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ReNotifyViewServiceTest {
    @Mock
    ReNotifyViewRepository reNotifyViewRepository;
    @Mock
    NotifyQueueRepository notifyQueueRepository;
    @InjectMocks
    ReNotifyViewServiceImpl reNotifyViewServiceImpl;
    List<ReNotifyView> reNotifyViews;
    List<ReNotifyView> reNotifyViewList;
    ReNotifyViewDTO reNotifyViewDTO;
    NoRecordsFoundException exception;
    ReNotifyView reNotifyView;
    double termId;
    Date date;
    String status;
    Pageable pageable;
    int pageNumber;
    int pageSize;
    PaginationWrapper paginationWrapper;
    Page<ReNotifyView> reNotifyViewPage;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reNotifyViews = Collections.emptyList();
        reNotifyViewList = new ArrayList<>();
        reNotifyView = new ReNotifyView();
        reNotifyViewList.add(reNotifyView);
        reNotifyViewDTO = new ReNotifyViewDTO();
        termId=0d;
        status="SENT";
        date= new Date();
        pageNumber = 1;
        pageSize = 10;
        pageable = PageRequest.of(pageNumber - 1, pageSize);
        reNotifyViewPage = new PageImpl<>(reNotifyViewList, PageRequest.of(0, 10), 100L);
        paginationWrapper = new PaginationWrapper(reNotifyViewList, reNotifyViewPage.getNumber() + 1,
                reNotifyViewPage.getTotalPages(), reNotifyViewPage.getTotalElements());
        reNotifyViewDTO.setReNotifyViewList(paginationWrapper);
        reNotifyViewDTO.setCountNotifyState(104);
    }
    @AfterEach
    void tearDown() throws Exception {
        reNotifyViewDTO = null;
        reNotifyViews=null;
        reNotifyView = null;
    }
    @Test
    void testGetNotifications() {
        when(reNotifyViewRepository.searchAll(termId,"EV",628,"EV",pageable)).thenReturn(reNotifyViewPage);
        Page<ReNotifyView> result = reNotifyViewServiceImpl.getNotifications(termId, pageable,"EV",628,"EV");
        int countResult = reNotifyViewServiceImpl.getCount(termId,status);
        int countDelayResult = reNotifyViewServiceImpl.getDelayCount(termId,"EV");
        Assertions.assertNotNull(result.getContent());
        Assertions.assertNotNull(countResult);
        Assertions.assertNotNull(countDelayResult);
    }
    @Test
    void testGetNotificationsNoRecordFound() {
        termId=5.18;
        when(reNotifyViewRepository.searchAll(termId,"EV",628,"EV",pageable)).thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0L));
        exception = assertThrows(NoRecordsFoundException.class,
                () -> when(reNotifyViewServiceImpl.getNotifications(termId, pageable,"EV",628,"EV")));
        assertEquals("No Records Found", exception.getMessage());
    }
}