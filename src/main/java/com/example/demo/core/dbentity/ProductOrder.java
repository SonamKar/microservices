package com.example.demo.core.dbentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOrder {
  @Id
  @Column
  private String orderid;
  @Column
  private String ordername;
  @Column
  private String paymentDetails;
}
