package com.example.additionalmovieinfo.core.filter;


import com.example.additionalmovieinfo.exception.util.MyExceptionBuilder;
import java.io.IOException;
import java.util.stream.Stream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements Filter {

  private static final String[] PATHS_TO_EXCLUDE = {
      "/h2-console"
  };
  private final MyExceptionBuilder myExceptionBuilder;
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) servletRequest;
    final HttpServletResponse res = (HttpServletResponse) servletResponse;

    if (Stream.of(PATHS_TO_EXCLUDE).noneMatch(req.getRequestURI()::startsWith)) {
      final String idType = req.getHeader("idType");
      // req.getParameter("phoneNumber");
      final String id = req.getRequestURI().split("/")[2];

      validateIdType(res, idType, id);
    }
    filterChain.doFilter(req, res);
  }
  public void validateIdType(HttpServletResponse res,String idType,String id){
    boolean valid= Integer.parseInt(id) > 2 && idType.equalsIgnoreCase("special") ||
        Integer.parseInt(id) <= 2 && idType.equalsIgnoreCase("normal");

    if(!valid){
      res.setStatus(HttpStatus.FORBIDDEN.value());
//      throw new RuntimeException("Invalid id type");
     myExceptionBuilder.throwAuthorizationException("123",
         "ERR-123",
         "Authorization Failure",
         "Invalid id type"
         );
    }
  }
}
