package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.entity.Employee;
import com.shoe.shoemanagement.entity.Task;
import com.shoe.shoemanagement.exceptions.ResourceNotFoundException;
import com.shoe.shoemanagement.repository.EmployeeRepository;
import com.shoe.shoemanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all tasks
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // fetch all tasks including associated employee
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            // Fetch employee by ID if provided
            Long employeeId = task.getEmployee() != null ? task.getEmployee().getId() : null;
            if (employeeId != null) {
                Employee employee = employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
                task.setEmployee(employee);
            }

            Task createdTask = taskRepository.save(task);
            return ResponseEntity.status(201).body(createdTask);
        } catch (Exception e) {
            // Log the full error message and stack trace for debugging
            e.printStackTrace(); // or log using a logger
            return ResponseEntity.status(500).body("An error occurred while saving the task: " + e.getMessage());
        }
    }
    // Get task by ID REST API
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not exist with id: " + id));
        return ResponseEntity.ok(task);
    }

    // Update task REST API
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not exist with id: " + id));

        // Update task fields
        task.setTaskName(taskDetails.getTaskName());
        task.setStatus(taskDetails.getStatus());
        task.setCreatedDate(taskDetails.getCreatedDate());
        task.setCompletedDate(taskDetails.getCompletedDate());

        // Set the employee if an employeeId is present
        Long employeeId = taskDetails.getEmployee() != null ? taskDetails.getEmployee().getId() : null;

        if (employeeId != null) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
            task.setEmployee(employee); // Associate the employee with the task
        }

        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    // Delete task REST API
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not exist with id: " + id));

        taskRepository.delete(task);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
