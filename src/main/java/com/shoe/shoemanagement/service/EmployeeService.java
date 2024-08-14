package com.shoe.shoemanagement.service;


import com.shoe.shoemanagement.entity.Employee;
import com.shoe.shoemanagement.entity.Item;
import com.shoe.shoemanagement.entity.Product;
import com.shoe.shoemanagement.repository.EmployeeRepository;
import com.shoe.shoemanagement.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Item> getProductsByEmployeeId(long employeeId) {
        return itemRepository.findByEmployeeId(employeeId);
    }
}
