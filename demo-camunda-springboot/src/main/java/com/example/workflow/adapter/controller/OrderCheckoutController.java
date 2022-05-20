package com.example.workflow.adapter.controller;

import com.example.workflow.model.Order;
import com.example.workflow.model.OrderResponse;
import com.example.workflow.service.CamundaStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderCheckoutController {
    @Autowired
    CamundaStartService camundaStartService;

    @RequestMapping(value="/message",method= RequestMethod.POST)
    public OrderResponse messageEvent(@RequestBody Order order)
    {
        return camundaStartService.startProcessByMessage(order.getOrderid(),order);
    }
}
