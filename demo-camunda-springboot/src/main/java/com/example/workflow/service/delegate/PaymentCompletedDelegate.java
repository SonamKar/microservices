package com.example.workflow.service.delegate;

import com.example.workflow.model.Order;
import com.example.workflow.model.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Service;

@Service
public class PaymentCompletedDelegate implements JavaDelegate {

  public void execute(DelegateExecution delegateExecution) throws Exception {
    ObjectValue order = delegateExecution.getVariableTyped("response");
    String orderStr = order.getValueSerialized();
    Order orderDetails=new ObjectMapper().readValue(orderStr,Order.class);
    OrderResponse orderResponse=enhanceResponse(orderDetails);
    ObjectValue typedOrderResponse =
        Variables.objectValue(orderResponse).serializationDataFormat("application/json").create();
    delegateExecution.setVariable("lastResponse", typedOrderResponse);
    delegateExecution.setVariable("output","payment done");
  }
  public OrderResponse enhanceResponse(Order order){
    return OrderResponse.builder()
        .order(order)
        .paymentDetails("done")
        .build();
  }
}
