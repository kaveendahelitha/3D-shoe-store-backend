package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.Serviceuser.OrderDetailService;
import com.shoe.shoemanagement.entity.OrderDetail;
import com.shoe.shoemanagement.entity.OrderInput;
import com.shoe.shoemanagement.entity.TransactionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;




    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping({"/place-order/{isSingleProductCheckout}"})
    public void placeOrder(@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
                           @RequestBody OrderInput orderInput) {
        orderDetailService.placeOrder(orderInput,isSingleProductCheckout);
    }

    @GetMapping({"/getOrderDetails"})
    public List<OrderDetail> getOrderDetails() {
        return orderDetailService.getOrderDetails();
    }

    @GetMapping({"/getAllOrderDetails/{status}"})
    public List<OrderDetail> getAllOrderDetails(@PathVariable(name = "status") String status) {
        return orderDetailService.getAllOrderDetails(status);
    }

    @GetMapping({"/markOrderAsDelivered/{orderId}"})
    public void markOrderAsDelivered(@PathVariable(name = "orderId") Integer orderId) {
        orderDetailService.markOrderAsDelivered(orderId);
    }
    @DeleteMapping({"/deleteOrder/{orderId}"})
    public void deleteOrder(@PathVariable(name = "orderId") Integer orderId) {
        orderDetailService.deleteOrderItem(orderId);
    }

    @GetMapping({"/createTransaction/{amount}"})
    public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount) {


        return orderDetailService.createTransaction(amount);
    }


}
