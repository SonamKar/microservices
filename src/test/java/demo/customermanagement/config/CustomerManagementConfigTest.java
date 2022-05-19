package demo.customermanagement.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@TestConfiguration
public class CustomerManagementConfigTest {
  private static final String CUSTOMER_MANAGEMENT_APP_PROPERTY_FILE =
      "customer-management-messages";
  private static final String FRAMEWORK_APP_PROPERTY_FILE = "messages";

  @Bean
  public ResourceBundleMessageSource messageSource() {
    final ResourceBundleMessageSource resourceBundleMessageSource =
        new ResourceBundleMessageSource();
    resourceBundleMessageSource.addBasenames(CUSTOMER_MANAGEMENT_APP_PROPERTY_FILE);
    resourceBundleMessageSource.addBasenames(FRAMEWORK_APP_PROPERTY_FILE);
    return resourceBundleMessageSource;
  }
}
