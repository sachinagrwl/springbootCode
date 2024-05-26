package com.nscorp.obis.filter;

import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RequestLoggingFilterTest {
    
    String value = "TEST";
    
    String transactionId = "TRANSACTION_ID";
    
    @Test
    public void testDoFilter() throws IOException, ServletException{
    
    	RequestLoggingFilter filter = new RequestLoggingFilter();

        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);

        ServletContext servletContext = Mockito.mock(ServletContext.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        FilterConfig mockFilterConfig = Mockito.mock(FilterConfig.class);

        Mockito.when(mockReq.getMethod()).thenReturn("/");
        Mockito.when(mockReq.getServletPath()).thenReturn("/");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        Mockito.when(mockReq.getQueryString()).thenReturn("/");
        Mockito.when(mockReq.getHeader("userid")).thenReturn("/");
        Mockito.when(mockReq.getHeader(null)).thenReturn("/");

        filter.init(mockFilterConfig);
        filter.doFilter(mockReq, mockResp, mockFilterChain);
        filter.destroy();
        
    }
    
    @Test
    public void testDoFilterNegativeFlow() throws IOException, ServletException{
    
    	RequestLoggingFilter filter = new RequestLoggingFilter();

        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);

        ServletContext servletContext = Mockito.mock(ServletContext.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

        Mockito.when(mockReq.getMethod()).thenReturn(null);
        Mockito.when(mockReq.getServletPath()).thenReturn(null);
        when(mockReq.getServletContext()).thenReturn(servletContext);
        Mockito.when(mockReq.getQueryString()).thenReturn(null);
        Mockito.when(mockReq.getHeader(Mockito.any())).thenReturn(null);

        filter.doFilter(mockReq, mockResp, mockFilterChain);
        
    }

}
