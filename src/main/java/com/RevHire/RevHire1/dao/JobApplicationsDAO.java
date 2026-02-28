package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.JobApplications;
import java.util.List;

public interface JobApplicationsDAO {
    List<JobApplications> getApplicationsByUserId(int userId);
    boolean submitApplication(int userId, int jobId);
    
    // ADD THESE TWO LINES:
    List<JobApplications> getApplicationsByEmployerId(int employerId);
    boolean updateApplicationStatus(int applicationId, String status);
}