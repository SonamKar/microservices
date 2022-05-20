package com.example.demo.repository;

import com.example.demo.core.dbentity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderJPARepository extends JpaRepository<ProductOrder,String> {

}
