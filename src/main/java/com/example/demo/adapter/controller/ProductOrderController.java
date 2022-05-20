package com.example.demo.adapter.controller;

import com.example.demo.contant.MessageKeys;
import com.example.demo.exception.util.MyExceptionBuilder;
import com.example.demo.repository.ProductOrderJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.core.dbentity.ProductOrder;

@RestController
@RequiredArgsConstructor
public class ProductOrderController {
  private final ProductOrderJPARepository productOrderJPARepository;
  private final MyExceptionBuilder exceptionBuilder;

  @PostMapping("/createOrder")
  public String createMovieDetail(@RequestBody ProductOrder productOrder) {
    System.out.println("inside controller :: "+productOrder);
    System.out.println("inside controller :: "+productOrder
        .getOrderid());
    System.out.println("inside controller :: "+productOrder.getPaymentDetails());
    if(Integer.parseInt(productOrder.getOrderid())>15){
       System.out.println("inside if where order id >15");
//       return productOrderJPARepository.save(productOrder).getOrderid();
      throw new ArithmeticException();
     }else{
       System.out.println("inside else where order id >15");
       exceptionBuilder.throwFunctionalException("Runtime Error",
           MessageKeys.ORDER_ID_INVALID,
           "Order id should be > 15",
           "Order id should be > 15"
           );
     }
     return null;
  }
}
