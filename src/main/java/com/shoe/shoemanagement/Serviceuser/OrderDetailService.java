package com.shoe.shoemanagement.Serviceuser;


import com.shoe.shoemanagement.config.JWTAuthFilter;
import com.shoe.shoemanagement.dao.OrderDetailDao;
import com.shoe.shoemanagement.entity.*;
import com.shoe.shoemanagement.repository.ProductRepo;
import com.shoe.shoemanagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class OrderDetailService{

    private static final String ORDER_PLACED = "Placed";

    @Autowired
    private ProductRepo productRepo;


    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private UserRepo userRepo;
    public void placeOrder(OrderInput orderInput){
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o: productQuantityList) {
            Product product=productRepo.findById(o.getProductId()).get();
            String currentUser=JWTAuthFilter.CURRENT_USER;
            User user =userRepo.findByEmail(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductPrice()*o.getQuantity(),
                    product,
                    user
            );
            orderDetailDao.save(orderDetail);
        }
    }

}
