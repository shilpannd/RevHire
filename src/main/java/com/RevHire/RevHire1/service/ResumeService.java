package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.ResumeDAO;
import com.RevHire.RevHire1.dao.implementation.ResumeDAOImpl;
import com.RevHire.RevHire1.models.Resume;

public class ResumeService {
    private ResumeDAO resumeDAO = new ResumeDAOImpl();

    // The View class specifically calls this method name
    public int getResumeIdByUserId(int userId) {
        int rid = resumeDAO.getResumeIdByUserId(userId);
        return rid;
    }

    // The View class specifically calls this method name
    public int createResume(int userId) {
        return resumeDAO.createResume(userId); 
    }

    public Resume getProfile(int userId) {
        return resumeDAO.getResumeByUserId(userId);
    }

    public boolean updateSummary(int resumeId, String summary) {
        return resumeDAO.updateSummary(resumeId, summary);
    }
    
}