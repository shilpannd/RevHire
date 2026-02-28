package com.RevHire.RevHire1.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.UsersDAO;
import com.RevHire.RevHire1.models.Users;



public class UsersDAOImpl implements UsersDAO 
{

	@Override
	public boolean emailExists(String email) {
	    String sql = "SELECT 1 FROM users WHERE email = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, email);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            return rs.next(); // True if email is found
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	@Override
	public boolean saveUserWithProfile(Users user) {
	    String userSql = "INSERT INTO users (email, password, role, security_question, security_answer) VALUES (?, ?, ?, ?, ?)";
	    Connection conn = null;

	    try {
	        conn = DBConnection.getConnection();
	        conn.setAutoCommit(false); // Start transaction

	        // 1. Insert User and get the generated ID
	        try (PreparedStatement pstmt = conn.prepareStatement(userSql, new String[]{"user_id"})) {
	            pstmt.setString(1, user.getEmail());
	            pstmt.setString(2, user.getPassword());
	            pstmt.setString(3, user.getRole());
	            pstmt.setString(4, user.getSecurityQuestion());
	            pstmt.setString(5, user.getSecurityAnswer());
	            pstmt.executeUpdate();

	            try (ResultSet rs = pstmt.getGeneratedKeys()) {
	                if (rs.next()) {
	                    int userId = rs.getInt(1);

	                    // 2. Insert into the appropriate profile table
	                    String profileSql = user.getRole().equalsIgnoreCase("JOB_SEEKER") 
	                        ? "INSERT INTO job_seeker_profile (user_id) VALUES (?)" 
	                        : "INSERT INTO employer_profile (user_id) VALUES (?)";

	                    try (PreparedStatement profileStmt = conn.prepareStatement(profileSql)) {
	                        profileStmt.setInt(1, userId);
	                        profileStmt.executeUpdate();
	                    }
	                }
	            }
	        }
	        conn.commit(); // Save both changes
	        return true;
	    } catch (Exception e) {
	        if (conn != null) {
	            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	        }
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (conn != null) {
	            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	}

	@Override
	public Users loginUser(String email, String password) {
		String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
		        
//		db connection
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        
//        executeQuery returns a ResultSet, a temporary table held in memory that contains the results of your search.
	        try (ResultSet rs = pstmt.executeQuery()) 
	        {
//	        to know who logged in the user details are stored in object User	
//	        the ResultSet when created, sits above the first row and on calling .next() moves the cursor down to first row of data. Till the method return true or null it moves.
	        	if (rs.next()) {
	        	    Users user = new Users();
	        	    user.setUserId(rs.getInt("user_id"));
	        	    user.setEmail(rs.getString("email"));
	        	    user.setRole(rs.getString("role")); // <--- CRITICAL: Is this line here?
	        	    return user;
	        	}
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
	        return null; // Login failed
	}
	
//	Forgot password

	@Override
	public String getSecurityQuestion(String email) 
	{
		String sql = "SELECT security_question FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next()) 
                {
                    return rs.getString("security_question");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
//        either email doesn't exist or sql exception occured.
        
	}

	@Override
	public boolean validateSecurityAnswer(String email, String answer) 
	{
		String sql = "SELECT 1 FROM users WHERE email = ? AND UPPER(security_answer) = UPPER(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            
            pstmt.setString(1, email);
            pstmt.setString(2, answer);
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                return rs.next(); // Returns true if a match is found
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
		return false;
        }
	}

	@Override
	public Users getUserByEmail(String email) {
	    // The table name is USR_REV.USERS based on your ERD
	    String sql = "SELECT user_id, email, password, role, security_question, security_answer FROM USR_REV.USERS WHERE email = ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            Users user = new Users();
	            user.setUserId(rs.getInt("user_id"));
	            user.setEmail(rs.getString("email"));
	            user.setPassword(rs.getString("password"));
	            user.setRole(rs.getString("role"));
	            // Populating these so your Forgot Password logic can verify them
	            user.setSecurityQuestion(rs.getString("security_question"));
	            user.setSecurityAnswer(rs.getString("security_answer"));
	            return user;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // Return null if user isn't found
	}
	@Override
	public boolean updatePassword(String email, String newPassword) {
	    String sql = "UPDATE users SET password = ? WHERE email = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, newPassword);
	        ps.setString(2, email);
	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


}