package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.Education;
import java.util.List;

public interface EducationDAO {
    boolean addEducation(Education edu);
    List<Education> getEducationByUserId(int userId);
    
    // MAKE SURE THIS LINE EXISTS:
    boolean updateEducation(Education edu); 
    
    boolean deleteEducation(int eduId);
}