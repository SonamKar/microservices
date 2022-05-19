package demo.customermanagement.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class CreateCustomerFeignBasicAuthConfig {

  @Value("${esb.password}")
  private String password;

  @Value("${esb.username}")
  private String username;

  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInt() {
    return new BasicAuthRequestInterceptor(username, password);
  }
}
