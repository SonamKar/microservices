package com.example.demo.exception.filter;

import com.example.demo.exception.BaseException;
import com.example.demo.exception.handler.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
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
    } catch(ServletException e) {
        e.printStackTrace();
    }
    }
}
