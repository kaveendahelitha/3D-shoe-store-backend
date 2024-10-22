package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.OrderDetail;
import com.shoe.shoemanagement.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
    public List<OrderDetail> findByUser(User user);

    public List<OrderDetail> findByOrderStatus(String status);
}
