package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.JobSkillsDAO;
import com.RevHire.RevHire1.dao.implementation.JobSkillsDAOImpl;
import com.RevHire.RevHire1.models.Skills;
import java.util.List;

public class JobSkillsService {
    private JobSkillsDAO jobSkillsDAO = new JobSkillsDAOImpl();

    // This fixes the "undefined" error for getSkillsByJobId
    public List<Skills> getSkillsByJobId(int jobId) {
        return jobSkillsDAO.getSkillsByJobId(jobId);
    }

    // Ensure this matches the (int, String) signature we created earlier
    public boolean addSkillToJob(int jobId, String skillName) {
        return jobSkillsDAO.addSkillToJob(jobId, skillName);
    }
}