package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.ResumeSkillsDAO;
import com.RevHire.RevHire1.dao.implementation.ResumeSkillsDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class ResumeSkillsService {
    private ResumeSkillsDAO resumeSkillsDAO = new ResumeSkillsDAOImpl();

    /**
     * Links a skill to a resume.
     */
    public boolean addSkillToResume(int resumeId, int skillId) {
        if (resumeId <= 0 || skillId <= 0) {
            return false;
        }
        return resumeSkillsDAO.addSkillToResume(resumeId, skillId);
    }

    /**
     * Fetches all skill IDs for a specific resume.
     */
    public List<Integer> getSkillsByResume(int resumeId) {
        return resumeSkillsDAO.getSkillIdsByResume(resumeId);
    }
    public List<String> getSkillNamesByResume(int resumeId) {
        if (resumeId <= 0) return new ArrayList<>();
        return resumeSkillsDAO.getSkillNamesByResume(resumeId);
    }
}