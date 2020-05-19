package com.example.apigateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RequestLoggingInterceptor implements ClientHttpRequestInterceptor {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                      ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
    logRequest(httpRequest, bytes);
    ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
    return response;
  }

  private void logRequest(HttpRequest request, byte[] body) throws IOException {
    logger.debug("===========================request begin================================================");
    logger.debug("URI         : {}", request.getURI());
    logger.debug("Method      : {}", request.getMethod());
    logger.debug("Headers     : {}", request.getHeaders());
    logger.debug("Request body: {}", new String(body, "UTF-8"));
    logger.debug("==========================request end================================================");
  }
}
