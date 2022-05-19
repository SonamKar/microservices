package demo.customermanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class CustomerManagementConfig {
    private static final String CUSTOMER_MANAGEMENT_APP_PROPERTY_FILE =
            "customer-management-messages";

    private final ResourceBundleMessageSource messageSource;

    @PostConstruct
    public ResourceBundleMessageSource productInventoryMessageSource() {
        messageSource.addBasenames(CUSTOMER_MANAGEMENT_APP_PROPERTY_FILE);
        return messageSource;
    }
}
