package com.shoe.shoemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "taskbula") // Ensure the table name is correct
public class Task {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;

    private String status;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    // Many-to-one relationship with Employee entity
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false) // Foreign key column linking to employee
    private Employee employee; // Employee associated with the task

    @ManyToOne
    @JoinColumn(name = "user_id") // assuming user_id is the column name
    private User user;

    public Task(String taskName, String status, LocalDate createdDate, LocalDate completedDate, Employee employee, User user) {
        this.taskName = taskName;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.employee = employee;
        this.user = user;
    }

    public Task() {
    }

}
