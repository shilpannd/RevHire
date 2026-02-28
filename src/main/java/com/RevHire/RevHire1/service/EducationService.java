package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.EducationDAO;
import com.RevHire.RevHire1.dao.implementation.EducationDAOImpl;
import com.RevHire.RevHire1.models.Education;
import java.util.List;

public class EducationService {
    private EducationDAO eduDAO = new EducationDAOImpl();

    public boolean addEducation(Education edu) {
        // 1. Validation Logic
        if (edu.getStartYear() > edu.getEndYear()) {
            System.out.println("Error: Start year cannot be after end year.");
            return false;
        }

        if (edu.getStartYear() < 1950 || edu.getEndYear() > 2030) {
            System.out.println("Error: Please enter a valid year range.");
            return false;
        }

        // 2. Critical Fix: Call the DAO and return its result!
        return eduDAO.addEducation(edu); 
    }

    public List<Education> getEducationByUserId(int userId) {
        // Note: Ensure your DAO implementation uses the correct SQL for this
        return eduDAO.getEducationByUserId(userId);
    }

    public boolean removeEducation(int eduId) {
        return eduDAO.deleteEducation(eduId);
    }
    
    public boolean updateEducation(Education edu) {
        return eduDAO.updateEducation(edu);
    }
}


//private EducationDAO eduDAO = new EducationDAOImpl();
//
//// Ensure this name matches what you call in View.java
//public List<Education> getEducationByUserId(int userId) {
//    return eduDAO.getEducationByUserId(userId);
//}
//
//public boolean addEducation(Education edu) {
//    return eduDAO.addEducation(edu);
//}
//}