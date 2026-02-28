package com.RevHire.RevHire1.dao.implementation;

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.models.EmployerProfile;

import java.sql.*;

public class EmployerProfileDAOImpl {

	public EmployerProfile getProfileByUserId(int userId) {
	    // Check if your table is called EMPLOYER_PROFILE or USR_REV.EMPLOYER_PROFILE
	    String sql = "SELECT * FROM USR_REV.EMPLOYER_PROFILE WHERE user_id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            EmployerProfile ep = new EmployerProfile();
	            ep.setUserId(rs.getInt("user_id"));
	            ep.setCompanyName(rs.getString("company_name"));
	            ep.setLocation(rs.getString("location"));
	            ep.setIndustry(rs.getString("industry"));
	            // ... set other fields
	            return ep;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null; // This causes the "null" display if no row is found
	}
    // ADDED: This method resolves the "undefined" error in your Service
    public boolean updateEmployerProfile(EmployerProfile profile) {
        String sql = "UPDATE employer_profile SET company_name = ?, industry = ?, company_size = ?, " +
                     "description = ?, website = ?, location = ? WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, profile.getCompanyName());
            pstmt.setString(2, profile.getIndustry());
            pstmt.setString(3, profile.getCompanySize());
            pstmt.setString(4, profile.getDescription());
            pstmt.setString(5, profile.getWebsite());
            pstmt.setString(6, profile.getLocation());
            pstmt.setInt(7, profile.getUserId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}