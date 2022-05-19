package demo.customermanagement.core.constant;

public class MessageKeys {
  public static final String ERROR_CONNECTING_CREATE_CUSTOMER_FEIGN = "CM-ERR-1000";
  public static final String ERROR_CREATING_CUSTOMER = "CM-ERR-1001";
  public static final String NO_RESPONSE_PAYLOAD_FROM_CREATE_CUSTOMER_FEIGN = "CM-ERR-1002";
  public static final String INPUT_HEADERS_MISSING = "CM-ERR-1003";


  private MessageKeys() {
    // Utility class should not be instantiated
  }
}

