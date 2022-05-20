package com.example.workflow.service.delegate;

import com.example.workflow.adapter.restclient.ProductOrderRestClient;
import com.example.workflow.model.Order;
import com.example.workflow.model.OrderResponse;
import com.example.workflow.model.restresponse.RestOrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException.FeignClientException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;

@Service
@EnableFeignClients
@RequiredArgsConstructor
public class GetPOStatusDelegate implements JavaDelegate {
  private final ProductOrderRestClient productOrderRestClient;

  public void execute(DelegateExecution delegateExecution) throws Exception {
    try {
      ObjectValue orderObjVal = delegateExecution.getVariableTyped("order");
      String orderStr = orderObjVal.getValueSerialized();
      Order orderDetails = new ObjectMapper().readValue(orderStr, Order.class);
      productOrderRestClient.createProductOrder(
          RestOrderResponse.builder()
              .orderid(orderDetails.getOrderid())
              .ordername(orderDetails.getOrdername())
              .paymentDetails("done")
              .build());

      OrderResponse orderResponse = enhanceResponse(orderDetails);
      ObjectValue typedOrderResponse =
          Variables.objectValue(orderResponse).serializationDataFormat("application/json").create();
      delegateExecution.setVariable("lastResponse", typedOrderResponse);
    } catch (FeignClientException ex) {
      System.out.println("Throw Functional error");
      delegateExecution.setVariable("errorType", "Functional");
      delegateExecution.setVariable("exceptionType","FeignClientException");
      throw new BpmnError("err-order-id");

    } catch (RetryableException ex) {
      System.out.println("Throw Technical error :: Retryable");
      delegateExecution.setVariable("errorType", "Technical");
      delegateExecution.setVariable("technicalType", "NotTimedOut");
      delegateExecution.setVariable("exceptionType","RetryableException");
      throw new BpmnError("err-order-id");

    } catch (Exception ex) {

        System.out.println("Throw Technical error");
        System.out.println(ex.getClass());
        delegateExecution.setVariable("errorType", "Technical");
        delegateExecution.setVariable("technicalType", "NotTimedOut");
        delegateExecution.setVariable("exceptionType","Exception");
        throw new BpmnError("err-order-id");
    }
    }
    public OrderResponse enhanceResponse(Order order){
      return OrderResponse.builder()
          .order(order)
          .paymentDetails("done")
          .build();
    }
}
