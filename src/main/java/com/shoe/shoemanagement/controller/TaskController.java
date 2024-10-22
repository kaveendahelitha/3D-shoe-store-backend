package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.Serviceuser.TaskService;
import com.shoe.shoemanagement.entity.Employee;

import com.shoe.shoemanagement.entity.Task;
import com.shoe.shoemanagement.entity.User;
import com.shoe.shoemanagement.exceptions.ResourceNotFoundException;
import com.shoe.shoemanagement.repository.EmployeeRepository;
import com.shoe.shoemanagement.repository.TaskRepository;
import com.shoe.shoemanagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepo userRepo;

    // Get all tasks
    //@GetMapping("/tasks")
   // public List<Task> getAllTasks() {
    //    return taskRepository.findAll(); // fetch all tasks including associated employee
   // }
    @GetMapping({"/getAllTaskDetails/{status}"})
    public List<Task> getAllTaskDetails(@PathVariable(name = "status") String status) {
       return taskService.getAllTaskDetails(status);
   }
    @GetMapping({"/getTaskDetails"})
    public List<Task> getTaskDetails() {
        return taskService.getTaskDetails();
    }

    @GetMapping({"/markTaskAsCompleted/{id}"})
    public void markTaskAsCompleted(@PathVariable(name = "id") Long id) {
        taskService.markTaskAsCompleted(id);
    }

    @GetMapping({"/markTaskAsProcessing/{id}"})
    public void markTaskAsProcessing(@PathVariable(name = "id") Long id) {
        taskService.markTaskAsProcessing(id);
    }


    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            LocalDate today = LocalDate.now();

            // Validation: createdDate must be today or earlier
            if (task.getCreatedDate() == null || task.getCreatedDate().isAfter(today)) {
                return ResponseEntity.badRequest().body("Error: Created date cannot be in the future.");
            }

            // Validation: completedDate must not be before createdDate
            if (task.getCompletedDate() != null && task.getCompletedDate().isBefore(task.getCreatedDate())) {
                return ResponseEntity.badRequest().body("Error: Completed date cannot be before the created date.");
            }

            // Fetch the employee by ID and ensure it exists
            Long employeeId = task.getEmployee() != null ? task.getEmployee().getId() : null;
            if (employeeId != null) {
                Employee employee = employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
                task.setEmployee(employee);

                // Associate the user (from Employee) with the task
                Optional<User> userOptional = userRepo.findById(employee.getUser().getId());
                if (!userOptional.isPresent()) {
                    return ResponseEntity.badRequest().body("Error: User not found for the employee.");
                }
                task.setUser(userOptional.get());
            }

            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(201).body(createdTask);
        } catch (Exception e) {
            e.printStackTrace();
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
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        try {
            // Call the TaskService to update the task
            Task updatedTask = taskService.updateTask(id, taskDetails);

            // Return the updated task as the response
            return ResponseEntity.ok(updatedTask);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while updating the task: " + e.getMessage());
        }
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
