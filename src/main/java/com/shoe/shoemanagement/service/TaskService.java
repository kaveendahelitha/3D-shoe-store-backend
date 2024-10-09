package com.shoe.shoemanagement.service;

import com.shoe.shoemanagement.entity.Employee;
import com.shoe.shoemanagement.entity.Task;
import com.shoe.shoemanagement.exceptions.ResourceNotFoundException;
import com.shoe.shoemanagement.repository.EmployeeRepository;
import com.shoe.shoemanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Task createTask(Task task) {
        Employee employee = employeeRepository.findById(task.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + task.getEmployee().getId()));

        task.setEmployee(employee); // Ensure the task is associated with an employee
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        task.setTaskName(taskDetails.getTaskName());
        task.setStatus(taskDetails.getStatus());
        task.setCreatedDate(taskDetails.getCreatedDate());
        task.setCompletedDate(taskDetails.getCompletedDate());

        if (taskDetails.getEmployee() != null) {
            Employee employee = employeeRepository.findById(taskDetails.getEmployee().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + taskDetails.getEmployee().getId()));
            task.setEmployee(employee);
        }

        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
    }
}
