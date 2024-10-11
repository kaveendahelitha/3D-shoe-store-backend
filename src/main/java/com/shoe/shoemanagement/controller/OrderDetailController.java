package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.Serviceuser.OrderDetailService;
import com.shoe.shoemanagement.entity.OrderInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping({"/place-order"})
    public void placeOrder(@RequestBody OrderInput orderInput){
      orderDetailService.placeOrder(orderInput);
    }
}
