package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.Users;

public interface UsersDAO {
    // Existing methods (login, etc.)
    Users loginUser(String email, String password);
    String getSecurityQuestion(String email);
    boolean validateSecurityAnswer(String email, String answer);
    boolean updatePassword(String email, String newPassword);
    boolean emailExists(String email); 
    boolean saveUserWithProfile(Users user);
    Users getUserByEmail(String email);
}