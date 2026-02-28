package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.Jobs;
import java.util.List;

public interface JobsDAO {
    List<Jobs> getAllJobs();
    List<Jobs> searchJobsByKeyword(String keyword);
    List<Jobs> getJobsByEmployerId(int employerId);
    Jobs getJobById(int jobId);
    
    // Use this name consistently
    boolean addJob(Jobs job); 
    
    boolean updateJob(Jobs job);
    boolean deleteJob(int jobId);

    void viewApplicationsByEmployer(int employerId);
    boolean applyForJob(int userId, int jobId, String coverLetter);
}