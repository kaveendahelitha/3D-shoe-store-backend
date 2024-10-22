package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.entity.Feedback;
import com.shoe.shoemanagement.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Get all feedback for a product
    @GetMapping("/product/{productId}")
    public List<Feedback> getFeedbackForProduct(@PathVariable Long productId) {
        return feedbackService.getFeedbackForProduct(productId);
    }

    // Add or update feedback for a product
    @PostMapping("/product/{productId}")
    public Feedback addOrUpdateFeedback(
            @PathVariable Long productId,
            @RequestBody Feedback feedback) {   // Updated to @RequestBody to properly pass the feedback object
        return feedbackService.addOrUpdateFeedback(productId, feedback.getFeedbackText(), feedback.getRating());
    }

    // Delete feedback
    @DeleteMapping("/{feedbackId}")
    public void deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.removeFeedback(feedbackId);
    }
}
