package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Dtos.FeedbackDto;
import com.OnbaordWeb.Exceptions.UserNotFoundException;
import com.OnbaordWeb.Models.Feedback;
import com.OnbaordWeb.Models.User;
import com.OnbaordWeb.Repositories.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    private Feedback feedback;
    private FeedbackDto feedbackDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a Feedback object and a FeedbackDto object for testing
        feedback = new Feedback();
        feedback.setFeedbackId(1L);
        feedback.setAttemptId(1L);
        feedback.setUserId(1L);
        feedback.setRating(4);
        feedback.setDescription("Great job!");

        feedbackDto = new FeedbackDto();
        feedbackDto.setFeedbackId(1L);
        feedbackDto.setAttemptId(1L);
        feedbackDto.setUserId(1L);
        feedbackDto.setRating(4);
        feedbackDto.setDescription("Great job!");

        // Initialize a User object for testing
        user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
    }

    @Test
    void testSaveFeedback() {
        // Arrange
        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        // Act
        Feedback savedFeedback = feedbackService.save(feedback);

        // Assert
        assertNotNull(savedFeedback);
        assertEquals(feedback.getRating(), savedFeedback.getRating());
        assertEquals(feedback.getDescription(), savedFeedback.getDescription());
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void testAllFeedbackDtos() {
        // Arrange
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackList.add(feedback);
        when(feedbackRepository.findAll()).thenReturn(feedbackList);

        when(userServiceImpl.findById(feedback.getUserId())).thenReturn(Optional.of(user));

        // Act
        List<FeedbackDto> feedbackDtoList = feedbackService.allFeedbackDtos();

        // Assert
        assertNotNull(feedbackDtoList);
        assertFalse(feedbackDtoList.isEmpty());
        assertEquals(1, feedbackDtoList.size());

        FeedbackDto retrievedFeedbackDto = feedbackDtoList.get(0);
        assertEquals(feedback.getRating(), retrievedFeedbackDto.getRating());
        assertEquals(feedback.getDescription(), retrievedFeedbackDto.getDescription());
        assertEquals("John Doe", retrievedFeedbackDto.getFullName());
    }

    @Test
    void testSaveFeedBackByDto() {
        // Arrange
        when(userServiceImpl.findById(feedbackDto.getUserId())).thenReturn(Optional.of(user));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        // Act
        Feedback savedFeedback = feedbackService.saveFeedBackByDto(feedbackDto);

        // Assert
        assertNotNull(savedFeedback);
        assertEquals(feedbackDto.getRating(), savedFeedback.getRating());
        assertEquals(feedbackDto.getDescription(), savedFeedback.getDescription());
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testSaveFeedBackByDto_UserNotFound() {
        // Arrange
        when(userServiceImpl.findById(feedbackDto.getUserId())).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            feedbackService.saveFeedBackByDto(feedbackDto);
        });
        assertEquals("User not Found!", exception.getMessage());
        verify(feedbackRepository, never()).save(any(Feedback.class));
    }
}
