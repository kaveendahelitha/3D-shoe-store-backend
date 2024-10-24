package com.shoe.shoemanagement.repository;


import com.shoe.shoemanagement.entity.Task;
import com.shoe.shoemanagement.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    //@EntityGraph(attributePaths = {"employee"})
   //List<Task> findAll();  // This will fetch tasks with employee details


    public List<Task> findByUser(User user);

    public List<Task> findByStatus(String status);


}
