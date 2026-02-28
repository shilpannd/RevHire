package com.RevHire.RevHire1.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.RevHire.RevHire1.dao.ResumeDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ResumeServiceTest {

    @Mock
    private ResumeDAO resumeDAO; // Mock the DAO interface

    @InjectMocks
    private ResumeService resumeService; // Inject the mock into the service

    @Test
    void testGetResumeIdByUserId_ValidUser() {
        // Arrange: Setup mock behavior
        int userId = 1;
        int expectedResumeId = 101;
        when(resumeDAO.getResumeIdByUserId(userId)).thenReturn(expectedResumeId);

        // Act: Call the service method
        int actualResumeId = resumeService.getResumeIdByUserId(userId);

        // Assert: Check if results match
        assertEquals(expectedResumeId, actualResumeId, "The resume ID should match the mock return value");
        verify(resumeDAO).getResumeIdByUserId(userId); // Verify the DAO was actually called
    }

    @Test
    void testGetResumeIdByUserId_InvalidUser() {
        // Arrange
        int userId = 99;
        when(resumeDAO.getResumeIdByUserId(userId)).thenReturn(0);

        // Act
        int result = resumeService.getResumeIdByUserId(userId);

        // Assert
        assertEquals(0, result);
    }
}