package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByEmployeeId(long employeeId);
}