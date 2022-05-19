package demo.customermanagement.core.exception.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import demo.customermanagement.core.exception.BaseException;
import demo.customermanagement.core.exception.handler.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
