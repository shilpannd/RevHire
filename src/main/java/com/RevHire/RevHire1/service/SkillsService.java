package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.SkillsDAO;
import com.RevHire.RevHire1.dao.implementation.SkillsDAOImpl;
import com.RevHire.RevHire1.models.Skills;

import java.util.List;

public class SkillsService {
    private SkillsDAO skillsDAO = new SkillsDAOImpl();

    /**
     * Gets the skill ID. If it doesn't exist, it creates it automatically.
     */
    public int getOrCreateSkill(String skillName) {
        if (skillName == null || skillName.trim().isEmpty()) {
            return -1;
        }
        // Normalize the string (trim and handle case consistency)
        return skillsDAO.getOrInsertSkill(skillName.trim());
    }

    public List<Skills> getAllAvailableSkills() {
        return skillsDAO.getAllSkills();
    }
    public List<Skills> getAllSkills() {
        // Assuming your DAO instance inside the service is named 'skillsDAO'
        return skillsDAO.getAllSkills();
    }
}