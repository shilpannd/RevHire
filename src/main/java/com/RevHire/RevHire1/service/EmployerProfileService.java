package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.implementation.EmployerProfileDAOImpl;
import com.RevHire.RevHire1.models.EmployerProfile;

public class EmployerProfileService {
    
    // Initialize the DAO
    private EmployerProfileDAOImpl employerDAO = new EmployerProfileDAOImpl();

    public EmployerProfile getProfile(int userId) {
        // This will call the ORA-fixed method in your DAO
        return employerDAO.getProfileByUserId(userId);
    }
    
    // You might need this for your "Manage Profile" option
    public boolean updateProfile(EmployerProfile profile) {
        return employerDAO.updateEmployerProfile(profile);
    }
}