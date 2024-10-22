package com.shoe.shoemanagement.Serviceuser;


import com.shoe.shoemanagement.entity.Employee;
import com.shoe.shoemanagement.entity.Item;
import com.shoe.shoemanagement.entity.Product;
import com.shoe.shoemanagement.entity.User;
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

    //public Employee getEmployeeById(long id) {
    //    return employeeRepository.findById(id).orElse(null);
    //}
//
    //public List<Item> getProductsByEmployeeId(long employeeId) {
       // return itemRepository.findByEmployeeId(employeeId);
    //}

    public Employee createEmployee(User user, String firstName, String lastName, String emailId) {
        if (user.isEmployee()) {
            Employee employee = new Employee(firstName, lastName, emailId, user);
            return employeeRepository.save(employee);  // Save employee with user link
        } else {
            throw new IllegalArgumentException("User must have role EMPLOYEE to be assigned as an Employee.");
        }
    }

}
