package com.shoe.shoemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 1000)  // Assuming feedback text can be long
    private String feedbackText;  // The feedback text provided by the user

    @Column(nullable = false)  // Ensures rating is required
    private int rating;           // Rating between, 1 to 5

    @ManyToOne(optional = false)  // Enforces that a feedback must have a product
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false)  // Enforces that feedback must have a user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)  // Ensure userFirstName is required
    private String userFirstName;  // Capturing the user's first name for display

    @Column(nullable = false)
    private LocalDateTime createdDate;  // Date when the feedback was created
}
