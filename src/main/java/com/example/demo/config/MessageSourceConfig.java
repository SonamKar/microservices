package com.example.demo.config;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@RequiredArgsConstructor
public class MessageSourceConfig {
  private static final String DEMO_PROPERTY_FILE = "demo-messages";

  private final ResourceBundleMessageSource messageSource;

  @PostConstruct
  public ResourceBundleMessageSource messageSource() {
    messageSource.addBasenames(DEMO_PROPERTY_FILE);
    return messageSource;
  }
}
