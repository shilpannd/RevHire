package com.RevHire.RevHire1.dao;

import java.util.List;




public interface ResumeSkillsDAO {
    boolean addSkillToResume(int resumeId, int skillId);
    List<Integer> getSkillIdsByResume(int resumeId);
    
    // Adding this line will fix your "must override" error!
    List<String> getSkillNamesByResume(int resumeId);
}