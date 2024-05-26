package com.nscorp.obis.filter;

//import com.oddblogger.springbootmdc.constant.Constants;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class RequestLoggingFilter implements Filter {

  private static final String QUERY_PARAMS = "QUERY_PARAMS";
  private static final String REQUEST_URI = "REQUEST_URI";
  private static final String HTTP_METHOD = "HTTP_METHOD";
  private static final String USERID = "userid";
  private static final String DEFAULT_USER = "OBIS_Default_User";
  private static final String DURATION = "DURATION";
  private static final String END_TIME = "END_TIME";
  private static final String USER_ID = "USER_ID";
  private static final String START_TIME = "START_TIME";
  private static final String TRANSACTION_ID = "TRANSACTION_ID";
  private static final String RESPONSE_STATUS = "RESPONSE_STATUS";
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    long startTime = System.currentTimeMillis();
    UUID uniqueId = UUID.randomUUID();
    MDC.put(TRANSACTION_ID, uniqueId.toString());
    MDC.put(START_TIME, new Timestamp(startTime).toString());

    if (servletRequest instanceof HttpServletRequest) {
      if (((HttpServletRequest) servletRequest).getMethod() != null) {
        MDC.put(HTTP_METHOD, ((HttpServletRequest) servletRequest).getMethod());
      }
      if (((HttpServletRequest) servletRequest).getServletPath() != null) {
        MDC.put(REQUEST_URI, ((HttpServletRequest) servletRequest).getServletPath());
      }
      if (((HttpServletRequest) servletRequest).getMethod() != null) {
        MDC.put(QUERY_PARAMS, ((HttpServletRequest) servletRequest).getQueryString());
      }
      if (((HttpServletRequest) servletRequest).getHeader(USERID) != null) {
        MDC.put(USER_ID, ((HttpServletRequest) servletRequest).getHeader(USERID));
      } else {
        MDC.put(USER_ID, DEFAULT_USER);
      }
    }

    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);
    servletRequest.getServletContext().getContextPath();
    responseWrapper.setHeader(TRANSACTION_ID, MDC.get(TRANSACTION_ID));
    responseWrapper.setHeader(USER_ID, MDC.get(USER_ID));
    responseWrapper.setHeader(START_TIME, MDC.get(START_TIME));
    logger.info("doFilter() Request Logging START");
    filterChain.doFilter(servletRequest, responseWrapper);
    long endTime = System.currentTimeMillis();
    MDC.put(END_TIME, new Timestamp(endTime).toString());
    MDC.put(DURATION, (endTime - startTime) + "");
    MDC.put(RESPONSE_STATUS, responseWrapper.getStatus() + "");
    responseWrapper.setHeader(END_TIME, MDC.get(END_TIME));
    responseWrapper.copyBodyToResponse();
    // log before MDC removals
    logger.info("doFilter() Request Logging END");
    removeMDCAdditions();
  }

  private void removeMDCAdditions() {
    MDC.remove(TRANSACTION_ID);
    MDC.remove(START_TIME);
    MDC.remove(HTTP_METHOD);
    MDC.remove(REQUEST_URI);
    MDC.remove(QUERY_PARAMS);
    MDC.remove(USER_ID);
    MDC.remove(END_TIME);
    MDC.remove(DURATION);
    MDC.remove(RESPONSE_STATUS);
  }

  @Override
  public void destroy() {
    logger.info("destroy() method invoked");
  }

}
