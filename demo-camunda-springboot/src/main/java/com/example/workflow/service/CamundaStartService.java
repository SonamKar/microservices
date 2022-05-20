package com.example.workflow.service;

import com.example.workflow.contant.MessageKeys;
import com.example.workflow.exception.util.MyExceptionBuilder;
import com.example.workflow.model.Order;
import com.example.workflow.model.OrderResponse;
import java.util.List;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamundaStartService {
	@Autowired
	ProcessEngine processEngine;
	@Autowired
	MyExceptionBuilder exceptionBuilder;

	public OrderResponse startProcessByMessage(String orderid,Order order)
	{

		ProcessInstance processInstance=processEngine.getRuntimeService().startProcessInstanceByKey("demo_ms",
				Variables.putValue("orderid",orderid).putValue("order",order));



		List<HistoricVariableInstance> variables = processEngine.getHistoryService().createHistoricVariableInstanceQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
		System.out.println("Length is : "+variables.size());
		for(HistoricVariableInstance hvi : variables)
		{
			System.out.println(hvi.getName()+ " :: "+hvi.getValue());
		}
		while(true) {

			HistoricVariableInstance historicVariableInstance = processEngine.getHistoryService()
					.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getRootProcessInstanceId())
					.variableValueEquals("orderStatus","completed")
					.singleResult();

			HistoricVariableInstance historicTechnicalErrorVariableInstance = processEngine.getHistoryService()
					.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getRootProcessInstanceId())
					.variableValueEquals("error","Technical NotTimedOut error occurred")
					.singleResult();

			HistoricVariableInstance historicFunctionalErrorVariableInstance = processEngine.getHistoryService()
					.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getRootProcessInstanceId())
					.variableValueEquals("error","Functional error occurred")
					.singleResult();

			HistoricVariableInstance var = processEngine.getHistoryService()
					.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getRootProcessInstanceId())
					.variableName("lastResponse")
					.singleResult();
			if (historicVariableInstance != null) {

				System.out.println("Hisstoric "+historicVariableInstance.getValue()+" "+historicVariableInstance);
				return (OrderResponse) var.getValue();

			} else if(historicTechnicalErrorVariableInstance!=null){

				System.out.println("Historic Error :: "+historicTechnicalErrorVariableInstance.getValue());
				exceptionBuilder.throwTechnicalException("Runtime Error",
						MessageKeys.PAYMENT_NOT_COMPLETED,
						"Payment isn't completed :: Technical",
						"Payment isn't completed :: Technical");

			}else if(historicFunctionalErrorVariableInstance!=null){

				System.out.println("Historic Error :: "+historicFunctionalErrorVariableInstance.getValue());
				exceptionBuilder.throwFunctionalException("Runtime Error",
						MessageKeys.PAYMENT_NOT_COMPLETED,
						"Payment isn't completed :: Functional",
						"Payment isn't completed :: Functional");

			}else {
				continue;
			}
	}
}}
