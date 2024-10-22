package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.Feedback;
import com.shoe.shoemanagement.entity.Product;
import com.shoe.shoemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProduct(Product product);          // Retrieve feedback for a specific product
    Optional<Feedback> findByProductAndUser(Product product, User user);  // Check if a user already gave feedback
}
