package com.example.workflow.adapter;

import com.example.workflow.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;

public class AnalyzePaymentDetails implements JavaDelegate {

  public void execute(DelegateExecution delegateExecution) throws Exception {
    ObjectValue order = delegateExecution.getVariableTyped("response");
    String orderStr = order.getValueSerialized();
    Order orderDetails=new ObjectMapper().readValue(orderStr,Order.class);
    System.out.println("order details ::"+orderDetails);
    if((orderDetails.getOrdername()).equals("Prepaid")){
      System.out.println("inside if");
      delegateExecution.setVariable("paymentStatus","done");
    }else{
      System.out.println("inside else");
      delegateExecution.setVariable("paymentStatus","pending");
    }
  }
}
