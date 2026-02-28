package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.Experience;
import java.util.List;

public interface ExperienceDAO {
    boolean addExperience(Experience exp);
    List<Experience> getExperienceByResumeId(int resumeId); // Must match exactly!
    boolean updateExperience(Experience exp);
    boolean deleteExperience(int expId);
    List<Experience> getExperienceByUserId(int userId);
}