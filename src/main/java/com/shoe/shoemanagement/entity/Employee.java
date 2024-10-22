package com.shoe.shoemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    // One-to-One relationship with User, only for users with role EMPLOYEE
    @OneToOne(cascade = CascadeType.ALL)  // Add the cascade type here
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public Employee() {
    }

    public Employee(String firstName, String lastName, String emailId, User user) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.user = user;
    }

}
