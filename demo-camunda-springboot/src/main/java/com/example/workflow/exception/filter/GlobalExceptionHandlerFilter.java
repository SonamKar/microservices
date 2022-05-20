package com.example.workflow.exception.filter;

import com.example.workflow.exception.BaseException;
import com.example.workflow.exception.handler.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class GlobalExceptionHandlerFilter implements Filter {
  @Autowired
  private GlobalExceptionHandler exceptionHandler;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException {
    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (BaseException e) {
      e.printStackTrace();
      servletResponse.setContentType("application/json");
      servletResponse
          .getWriter()
          .print(
              objectMapper.writeValueAsString(
                  exceptionHandler.handleBaseException(e).getBody()));
      servletResponse.getWriter().flush();
    } catch(Exception e) {

    }
    }
}
