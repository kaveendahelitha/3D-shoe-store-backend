package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.Cart;
import com.shoe.shoemanagement.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart,Long > {
    public List<Cart> findByUser(User user);
}