package com.example.workflow.adapter;

import com.example.workflow.model.Order;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Service;

@Service
public class RetrievePaymentDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Order order=(Order)delegateExecution.getVariable("order");
        ObjectValue typedOrderValue =
                Variables.objectValue(order).serializationDataFormat("application/json").create();
        delegateExecution.setVariable("response", typedOrderValue);
    }
}
