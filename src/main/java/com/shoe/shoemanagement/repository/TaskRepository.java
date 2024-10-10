package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @EntityGraph(attributePaths = {"employee"})
    List<Task> findAll();  // This will fetch tasks with employee details
}
