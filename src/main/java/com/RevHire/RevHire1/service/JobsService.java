package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.JobsDAO;
import com.RevHire.RevHire1.dao.implementation.JobsDAOImpl;
import com.RevHire.RevHire1.models.Jobs;
import java.util.List;

public class JobsService {
    
    private JobsDAO jobDAO = new JobsDAOImpl();

    public List<Jobs> getAllOpenJobs() {
        return jobDAO.getAllJobs();
    }

    public List<Jobs> searchJobs(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return jobDAO.getAllJobs(); 
        }
        return jobDAO.searchJobsByKeyword(keyword);
    }

    public List<Jobs> getJobsByEmployer(int employerId) {
        return jobDAO.getJobsByEmployerId(employerId);
    }

    public Jobs getJobById(int jobId) {
        return jobDAO.getJobById(jobId);
    }

    public boolean postJob(Jobs job) {
        // Business Logic: default status if not set
        if (job.getStatus() == null) {
            job.setStatus("OPEN");
        }
        return jobDAO.addJob(job); 
    }

    public boolean updateJob(Jobs job) {
        return jobDAO.updateJob(job);
    }

    public boolean deleteJob(int jobId) {
        // This now performs a soft delete via the DAO
        return jobDAO.deleteJob(jobId);
    }
    
}