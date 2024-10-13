package com.shoe.shoemanagement.Serviceuser;


import com.shoe.shoemanagement.config.JWTAuthFilter;
import com.shoe.shoemanagement.dao.OrderDetailDao;
import com.shoe.shoemanagement.entity.*;
import com.shoe.shoemanagement.repository.CartRepository;
import com.shoe.shoemanagement.repository.ProductRepo;
import com.shoe.shoemanagement.repository.TransactionDetailsRepository;
import com.shoe.shoemanagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Service
public class OrderDetailService{

    private static final String ORDER_PLACED = "Placed";
    private String merchantSecret = "NzU5Mjk3MjQ4MjA5MjM3NzE0NzIyODMwMzgxMDczNTU3ODU3MzIx";
    @Autowired
    private ProductRepo productRepo;


    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;


    public List<OrderDetail> getOrderDetails() {
        String currentUser = JWTAuthFilter.CURRENT_USER;
        User user = userRepo.findByEmail(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        if(status.equals("All")) {
            orderDetailDao.findAll().forEach(
                    x -> orderDetails.add(x)
            );
        } else {
            orderDetailDao.findByOrderStatus(status).forEach(
                    x -> orderDetails.add(x)
            );
        }


        return orderDetails;
    }

    public void markOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).get();

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }

    }

    public TransactionDetails createTransaction(Double amount) {
        String merchantID = "1228411";
        String orderID = Long.toString(System.currentTimeMillis());
        String currency = "LKR";
        DecimalFormat df = new DecimalFormat("0.00");
        String amountFormatted = df.format(amount);
        String hash = getMd5(merchantID + orderID + amountFormatted + currency + getMd5(merchantSecret));

        // Create and save TransactionDetails
        TransactionDetails transactionDetails = new TransactionDetails(orderID, currency, amount.intValue(), hash);
        transactionDetailsRepository.save(transactionDetails);

        return transactionDetails;
    }


    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }




    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout){
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
            if(!isSingleProductCheckout) {
                List<Cart> carts = cartRepository.findByUser(user);
                carts.stream().forEach(x -> cartRepository.deleteById(x.getCartId()));
            }
            orderDetailDao.save(orderDetail);
        }
    }

    public void deleteOrderItem(Integer orderId) {
        orderDetailDao.deleteById(orderId);
    }

}
