package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.JobApplicationsDAO;
import com.RevHire.RevHire1.dao.implementation.JobApplicationsDAOImpl;
import com.RevHire.RevHire1.models.JobApplications;
import java.util.List;

public class JobApplicationsService {
    
    private JobApplicationsDAO appDAO = new JobApplicationsDAOImpl();
    // Added to handle notifications when status changes
    private NotificationsService notificationService = new NotificationsService();

    public List<JobApplications> getApplicationsByUserId(int userId) {
        return appDAO.getApplicationsByUserId(userId);
    }

    public boolean applyForJob(int userId, int jobId) {
        return appDAO.submitApplication(userId, jobId);
    }

    /**
     * New method for Employer to see applications for their specific jobs
     */
    public List<JobApplications> getApplicationsForEmployer(int employerId) {
        return appDAO.getApplicationsByEmployerId(employerId);
    }

    /**
     * Handles Shortlisting/Rejection and notifies the seeker
     */
    public boolean updateApplicationStatus(int applicationId, String status, int seekerId, String jobTitle) {
        // 1. Update the status in the job_applications table
        boolean isUpdated = appDAO.updateApplicationStatus(applicationId, status);
        
        // 2. If update was successful, send a notification to the Job Seeker
        if (isUpdated) {
            String message = String.format("Update: Your application for '%s' has been %s.", 
                                            jobTitle, status.toLowerCase());
            notificationService.addNotification(seekerId, message);
        }
        
        return isUpdated;
    }
}