package com.example.workflow.config;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@RequiredArgsConstructor
public class MessageSourceConfig {
  private static final String CAMUNDA_DEMO_PROPERTY_FILE = "camunda-demo-messages";

  private final ResourceBundleMessageSource messageSource;

  @PostConstruct
  public ResourceBundleMessageSource messageSource() {
    messageSource.addBasenames(CAMUNDA_DEMO_PROPERTY_FILE);
    return messageSource;
  }
}
