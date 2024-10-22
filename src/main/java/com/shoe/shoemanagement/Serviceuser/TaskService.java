package com.shoe.shoemanagement.Serviceuser;

import com.shoe.shoemanagement.config.JWTAuthFilter;
import com.shoe.shoemanagement.entity.Employee;


import com.shoe.shoemanagement.entity.Task;
import com.shoe.shoemanagement.entity.User;
import com.shoe.shoemanagement.exceptions.ResourceNotFoundException;
import com.shoe.shoemanagement.repository.EmployeeRepository;
import com.shoe.shoemanagement.repository.TaskRepository;
import com.shoe.shoemanagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public Task createTask(Task task) {
        Employee employee = employeeRepository.findById(task.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + task.getEmployee().getId()));

        task.setEmployee(employee);

        // Link the user to the task via the employee
        User user = userRepo.findById(employee.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + employee.getUser().getId()));

        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getTaskDetails() {
        String currentUser = JWTAuthFilter.CURRENT_USER;
        User user = userRepo.findByEmail(currentUser).get();

        return taskRepository.findByUser(user);
    }


    public List<Task> getAllTaskDetails(String status) {
       List<Task> taskDetails=new ArrayList<>();

     if(status.equals("All")) {
           taskRepository.findAll().forEach(
                   x -> taskDetails.add(x)
           );
       } else {
         taskRepository.findByStatus(status).forEach(
                 x -> taskDetails.add(x)
           );
        }


        return taskDetails;
    }

    public void markTaskAsProcessing(Long id) {
        Task task = taskRepository.findById(id).get();

        if(task != null) {
            task.setStatus("Processing");
            taskRepository.save(task);
        }

    }



    public void markTaskAsCompleted(Long id) {
        Task task = taskRepository.findById(id).get();

        if(task != null) {
            task.setStatus("Completed");
            taskRepository.save(task);
        }

    }




    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        // Update the task fields
        task.setTaskName(taskDetails.getTaskName());
        task.setStatus(taskDetails.getStatus());
        task.setCreatedDate(taskDetails.getCreatedDate());
        task.setCompletedDate(taskDetails.getCompletedDate());

        // Set the employee if an employeeId is present
        if (taskDetails.getEmployee() != null) {
            Employee employee = employeeRepository.findById(taskDetails.getEmployee().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + taskDetails.getEmployee().getId()));
            task.setEmployee(employee);
        }

        // Optionally update the associated user (if required)
        if (taskDetails.getEmployee() != null && taskDetails.getEmployee().getUser() != null) {
            Optional<User> userOptional = userRepo.findById(taskDetails.getEmployee().getUser().getId());
            if (userOptional.isPresent()) {
                task.setUser(userOptional.get());
            }
        }

        return taskRepository.save(task); // Save the updated task
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
    }
}
