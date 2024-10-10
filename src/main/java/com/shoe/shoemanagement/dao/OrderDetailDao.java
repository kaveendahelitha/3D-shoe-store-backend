package com.shoe.shoemanagement.dao;

import com.shoe.shoemanagement.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {

}
