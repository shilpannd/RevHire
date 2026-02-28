//package com.RevHire.RevHire1.dao.implementation;
//
//import java.sql.Connection;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.RevHire.RevHire1.config.DBConnection;
//import com.RevHire.RevHire1.dao.EducationDAO;
//import com.RevHire.RevHire1.models.Education;
//
//
//public class EducationDAOImpl implements EducationDAO {
//
//    @Override
//    public boolean addEducation(Education edu) {
//        String sql = "INSERT INTO education (user_id, institution, degree, field_of_study, graduation_year) VALUES (?, ?, ?, ?, ?)";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            
//            ps.setInt(1, edu.getUserId());
//            ps.setString(2, edu.getInstitution());
//            ps.setString(3, edu.getDegree());
//            ps.setString(4, edu.getFieldOfStudy());
//            ps.setInt(5, edu.getGraduationYear());
//            
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public List<Education> getEducationByUserId(int userId) {
//        List<Education> educationList = new ArrayList<>();
//        String sql = "SELECT * FROM education WHERE user_id = ?";
//        
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            
//            ps.setInt(1, userId);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Education edu = new Education();
//                    edu.setEducationId(rs.getInt("edu_id")); // Primary Key of the Education table
//                    edu.setUserId(rs.getInt("user_id"));
//                    edu.setInstitution(rs.getString("institution"));
//                    edu.setDegree(rs.getString("degree"));
//                    edu.setFieldOfStudy(rs.getString("field_of_study"));
//                    edu.setGraduationYear(rs.getInt("graduation_year"));
//                    educationList.add(edu);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return educationList;
//    }
//
//    @Override
//    public boolean updateEducation(Education edu) {
//        String sql = "UPDATE education SET institution=?, degree=?, field_of_study=?, graduation_year=? WHERE edu_id=?";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            
//            ps.setString(1, edu.getInstitution());
//            ps.setString(2, edu.getDegree());
//            ps.setString(3, edu.getFieldOfStudy());
//            ps.setInt(4, edu.getGraduationYear());
//            ps.setInt(5, edu.getEducationId());
//            
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean deleteEducation(int eduId) {
//        String sql = "DELETE FROM education WHERE edu_id = ?";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, eduId);
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//	@Override
//	public boolean addEducation(Education edu) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean updateEducation(Education edu) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//}





package com.RevHire.RevHire1.dao.implementation;

import java.sql.*;
import java.util.List;
import java.util.ArrayList; // Added this

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.EducationDAO;
import com.RevHire.RevHire1.models.Education;

public class EducationDAOImpl implements EducationDAO {

	// Inside EducationDAOImpl.java
	@Override
	public boolean addEducation(Education edu) {
	    // Double check: are your column names exactly these?
	    String sql = "INSERT INTO education (resume_id, degree, institution, start_year, end_year) VALUES (?, ?, ?, ?, ?)";
	    
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, edu.getResumeId());
	        ps.setString(2, edu.getDegree());
	        ps.setString(3, edu.getInstitution());
	        ps.setInt(4, edu.getStartYear());
	        ps.setInt(5, edu.getEndYear());

	        System.out.println("Debug: Attempting to insert for Resume ID: " + edu.getResumeId());

	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        // THIS LINE IS CRUCIAL: It will tell you if it's a FK error or Column error
	        System.err.println("DATABASE ERROR: " + e.getMessage()); 
	        return false;
	    }
	}
    @Override
    public List<Education> getEducationByUserId(int userId) {
        List<Education> educationList = new ArrayList<>();
        // Join Education with Resume to filter by User ID
        String sql = "SELECT e.* FROM education e " +
                     "JOIN resume r ON e.resume_id = r.resume_id " +
                     "WHERE r.user_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Education edu = new Education();
                edu.setEducationId(rs.getInt("education_id"));
                edu.setResumeId(rs.getInt("resume_id"));
                edu.setDegree(rs.getString("degree"));
                edu.setInstitution(rs.getString("institution"));
                edu.setStartYear(rs.getInt("start_year"));
                edu.setEndYear(rs.getInt("end_year"));
                educationList.add(edu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return educationList;
    } 
    @Override
    public boolean updateEducation(Education edu) {
        // Matches your schema: education_id, institution, degree, start_year, end_year
        String sql = "UPDATE education SET institution=?, degree=?, start_year=?, end_year=? WHERE education_id=?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, edu.getInstitution());
            ps.setString(2, edu.getDegree());
            ps.setInt(3, edu.getStartYear());
            ps.setInt(4, edu.getEndYear());
            ps.setInt(5, edu.getEducationId()); // Using the PK from your schema
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Update Error: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean deleteEducation(int eduId) {
        String sql = "DELETE FROM education WHERE edu_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eduId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // DUPLICATE METHODS REMOVED FROM HERE
}