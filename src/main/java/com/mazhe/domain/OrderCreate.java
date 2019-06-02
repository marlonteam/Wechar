package com.mazhe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreate implements Serializable {

    private OrderBase orderBase;

    private List<OrderDetail> orderDetail;
}
