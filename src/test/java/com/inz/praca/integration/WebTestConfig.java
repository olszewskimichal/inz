package com.inz.praca.integration;

import java.util.Properties;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public final class WebTestConfig {

  private static final String VIEW_BASE_PATH = "/WEB-INF/templates";
  private static final String VIEW_FILENAME_SUFFIX = ".html";
  private static final String VIEW_NAME_ERROR_VIEW = "error/error";
  private static final String VIEW_NAME_NOT_FOUND_VIEW = "error/404";
  private static final String HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR = "500";
  private static final String HTTP_STATUS_CODE_NOT_FOUND = "404";

  public static ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

    viewResolver.setPrefix(WebTestConfig.VIEW_BASE_PATH);
    viewResolver.setSuffix(WebTestConfig.VIEW_FILENAME_SUFFIX);

    return viewResolver;
  }

  public static SimpleMappingExceptionResolver exceptionResolver() {
    SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

    Properties exceptionMappings = new Properties();

    exceptionMappings.put("com.inz.praca.products.ProductNotFoundException", WebTestConfig.VIEW_NAME_NOT_FOUND_VIEW);
    exceptionMappings.put("java.lang.Exception", WebTestConfig.VIEW_NAME_ERROR_VIEW);
    exceptionMappings.put("java.lang.RuntimeException", WebTestConfig.VIEW_NAME_ERROR_VIEW);

    exceptionResolver.setExceptionMappings(exceptionMappings);

    Properties statusCodes = new Properties();

    statusCodes.put(WebTestConfig.VIEW_NAME_NOT_FOUND_VIEW, WebTestConfig.HTTP_STATUS_CODE_NOT_FOUND);
    statusCodes.put(WebTestConfig.VIEW_NAME_ERROR_VIEW, WebTestConfig.HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR);

    exceptionResolver.setStatusCodes(statusCodes);

    return exceptionResolver;
  }

}
