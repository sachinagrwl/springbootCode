package com.nscorp.obis.controller;

import com.nscorp.obis.domain.ReNotifyView;

import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.ReNotifyViewDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginationWrapper;
import com.nscorp.obis.services.ReNotifyViewService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ReNotifyViewControllerTest {
    @Mock
    ReNotifyViewService reNotifyViewService;
    @InjectMocks
    ReNotifyViewController reNotifyViewController;
    ReNotifyViewDTO dtoList;
    Page<ReNotifyView> reNotifyViewPage;
    List<ReNotifyView> reNotifyViewList;
    ReNotifyView reNotifyView;
    int count;
    Double termId;
    String state;
    Pageable pageable;
    int pageNumber;
    int pageSize;
    PaginationWrapper paginationWrapper;
    String[] sort;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        count=0;
        termId=8.15453;
        state="SENT";
        sort = new String[]{"notifyCustomerName"};
        dtoList=new ReNotifyViewDTO();
        reNotifyViewList = new ArrayList<>();
        reNotifyView = new ReNotifyView();
        reNotifyView.setNotifyStat("CONF");
        reNotifyViewList.add(reNotifyView);
        pageNumber = 1;
        pageSize = 10;
        reNotifyViewPage = new PageImpl<>(reNotifyViewList, PageRequest.of(0, 10), 100L);
        pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(SortFilter.sortOrder(sort)));
        paginationWrapper = new PaginationWrapper(reNotifyViewList, reNotifyViewPage.getNumber() + 1,
                reNotifyViewPage.getTotalPages(), reNotifyViewPage.getTotalElements());
        dtoList.setReNotifyViewList(paginationWrapper);
        dtoList.setCountNotifyState(count);
    }
    @AfterEach
    void tearDown() throws Exception {
        dtoList=null;
        reNotifyViewList = null;
        reNotifyView=null;
        termId = null;
        state = null;
    }
    @Test
    void testGetReNotifyView() {
        when(reNotifyViewService.getNotifications(termId,pageable,"EM",628,"EV")).thenReturn(reNotifyViewPage);
        when(reNotifyViewService.getCount(termId, "CONF")).thenReturn(1);
        when(reNotifyViewService.getDelayCount(termId, "EV")).thenReturn(1);
        ResponseEntity<APIResponse<ReNotifyViewDTO>> result = reNotifyViewController.getReNotifyViewByTermId(termId, state,"EM",628,"EV",pageNumber,pageSize,sort);
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
    }
    @Test
    void testGetReNotifyViewNoRecordFoundException() {
        when(reNotifyViewController.getReNotifyViewByTermId(termId, state,"EM",628,"EV",pageNumber,pageSize,sort)).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<ReNotifyViewDTO>> result = reNotifyViewController.getReNotifyViewByTermId(termId, state,"EM",628,"EV",pageNumber,pageSize,sort);
        Assertions.assertEquals(result.getStatusCodeValue(), 404);
    }

    @Test
    void testGetRenotifyViewException() {
        when(reNotifyViewController.getReNotifyViewByTermId(termId, state,"EM",628,"EV",pageNumber,pageSize,sort)).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<ReNotifyViewDTO>> result = reNotifyViewController.getReNotifyViewByTermId(termId, state,"EM",628,"EV",pageNumber,pageSize,sort);
        Assertions.assertEquals(result.getStatusCodeValue(), 500);
    }
}