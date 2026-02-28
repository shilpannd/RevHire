package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.UsersDAO;
import com.RevHire.RevHire1.dao.implementation.UsersDAOImpl;
import com.RevHire.RevHire1.models.Users;

public class UsersService {
    
    // Initialize the DAO implementation
    private UsersDAO userDAO = new UsersDAOImpl();

    /**
     * Authenticates a user during login.
     */
    public Users login(String email, String password) {
        return userDAO.loginUser(email, password);
    }

    /**
     * Registers a new user with security details for password recovery.
     */
    public String registerUser(String email, String password, String role, String securityQuestion, String securityAnswer) {
        // Create a user object to pass to the DAO
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setSecurityQuestion(securityQuestion);
        user.setSecurityAnswer(securityAnswer);

        // We use the new DAO method that handles profile creation alongside user registration
        boolean success = userDAO.saveUserWithProfile(user);
        
        return success ? "✅ Registration successful!" : "❌ Registration failed. Email might already exist.";
    }

    /**
     * Fetches full user details by email. 
     * This is the method that was causing your "Undefined" error in Main.
     */
    public Users getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    /**
     * Updates the password in the database.
     */
    public boolean resetPassword(String email, String newPassword) {
        return userDAO.updatePassword(email, newPassword);
    }

    /**
     * Checks if an email exists before attempting reset.
     */
    public boolean doesEmailExist(String email) {
        return userDAO.emailExists(email);
    }
}