package com.shoe.shoemanagement.service;

import com.shoe.shoemanagement.config.JWTAuthFilter;
import com.shoe.shoemanagement.entity.Feedback;
import com.shoe.shoemanagement.entity.Product;
import com.shoe.shoemanagement.entity.User;
import com.shoe.shoemanagement.repository.FeedbackRepository;
import com.shoe.shoemanagement.repository.ProductRepo;
import com.shoe.shoemanagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    // Constructor-based injection
    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, ProductRepo productRepo, UserRepo userRepo) {
        this.feedbackRepository = feedbackRepository;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    // Fetch all feedback for a product
    public List<Feedback> getFeedbackForProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return feedbackRepository.findByProduct(product);
    }

    // Add or update feedback
    public Feedback addOrUpdateFeedback(Long productId, String feedbackText, int rating) {
        // Get the current user from JWT
        String username = JWTAuthFilter.CURRENT_USER;

        if (username == null) {
            throw new RuntimeException("Current user is not authenticated");
        }

        // Fetch the product and user
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<User> userOptional = userRepo.findByEmail(username);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Feedback> existingFeedback = feedbackRepository.findByProductAndUser(product, user);
        Feedback feedback;

        if (existingFeedback.isPresent()) {
            feedback = existingFeedback.get();
            feedback.setFeedbackText(feedbackText);
            feedback.setRating(rating);
        } else {
            feedback = new Feedback();
            feedback.setProduct(product);
            feedback.setUser(user);
            feedback.setFeedbackText(feedbackText);
            feedback.setRating(rating);
            feedback.setUserFirstName(user.getUserFirstname());
            feedback.setCreatedDate(LocalDateTime.now()); // Set the createdDate for new feedback
        }

        return feedbackRepository.save(feedback);
    }

    // Remove feedback by ID
    public void removeFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        // Get current user from JWTAuthFilter
        String username = JWTAuthFilter.CURRENT_USER;
        if (username == null) {
            throw new RuntimeException("Current user is not authenticated");
        }

        Optional<User> userOptional = userRepo.findByEmail(username);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));

        // Compare user IDs correctly
        if (feedback.getUser().getId() != user.getId()) {
            throw new RuntimeException("You can only remove your own feedback.");
        }

        feedbackRepository.delete(feedback);
    }
}
