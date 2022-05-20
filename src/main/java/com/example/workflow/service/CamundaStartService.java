package com.example.workflow.service;

import com.example.workflow.adapter.PaymentCompletedDelegate;
import com.example.workflow.adapter.RetrievePaymentDelegate;
import com.example.workflow.model.Order;
import com.example.workflow.model.OrderResponse;
import java.util.List;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.camunda.bpm.engine.impl.variable.serializer.ValueFields;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.SerializationDataFormat;
import org.camunda.spin.plugin.impl.SpinObjectValueSerializer;
import org.camunda.spin.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamundaStartService {
	@Autowired
	ProcessEngine processEngine;
	@Autowired
	PaymentCompletedDelegate paymentCompletedDelegate;

	public OrderResponse startProcessByMessage(String orderid,Order order)
	{

		ProcessInstance processInstance=processEngine.getRuntimeService().startProcessInstanceByKey("demo_ms",
				Variables.putValue("orderid",orderid).putValue("order",order));

		List<HistoricVariableInstance> variables = processEngine.getHistoryService().createHistoricVariableInstanceQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
		System.out.println("Length is : "+variables.size());
		for(HistoricVariableInstance hvi : variables)
		{
			System.out.println(hvi.getValue());
		}

		while(true) {
			HistoricVariableInstance historicVariableInstance = processEngine.getHistoryService()
					.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getRootProcessInstanceId())
					.variableValueEquals("order","completed")
					.singleResult();

			HistoricVariableInstance historicErrorVariableInstance = processEngine.getHistoryService()
					.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getRootProcessInstanceId())
					.variableValueEquals("error","error occurred")
					.singleResult();

			HistoricVariableInstance var = processEngine.getHistoryService()
					.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getRootProcessInstanceId())
					.variableName("lastResponse")
					.singleResult();
			if (historicVariableInstance != null) {
				System.out.println("Hisstoric "+historicVariableInstance.getValue()+" "+historicVariableInstance);
				return (OrderResponse) var.getValue();
			} else if(historicErrorVariableInstance!=null){
				System.out.println("Historic Error :: "+historicErrorVariableInstance.getValue());
				throw new RuntimeException("Payment isn't completed") ;
			}else {
				continue;
			}
	}
}}
