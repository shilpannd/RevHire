package com.RevHire.RevHire1.dao;

import java.util.List;
import com.RevHire.RevHire1.models.Skills;

public interface JobSkillsDAO {
    // Changed second parameter from int to String
    boolean addSkillToJob(int jobId, String skillName); 
    List<Skills> getSkillsByJobId(int jobId);
    boolean removeSkillFromJob(int jobId, int skillId);
    int getOrCreateSkill(String skillName);
}