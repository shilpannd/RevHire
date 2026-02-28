package com.RevHire.RevHire1.dao.implementation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.ExperienceDAO;
import com.RevHire.RevHire1.models.Experience;

public class ExperienceDAOImpl implements ExperienceDAO {

    @Override
    public boolean addExperience(Experience exp) {
        // Use USR_REV prefix if required by your Oracle setup
        String sql = "INSERT INTO experience (resume_id, company, job_role, start_date, end_date, description) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, exp.getResumeId());
            ps.setString(2, exp.getCompany());
            ps.setString(3, exp.getJobRole());
            ps.setDate(4, exp.getStartDate());
            ps.setDate(5, exp.getEndDate()); // Handles null naturally if model is set
            ps.setString(6, exp.getDescription());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Experience> getExperienceByResumeId(int resumeId) {
        List<Experience> list = new ArrayList<>();
        String sql = "SELECT * FROM experience WHERE resume_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, resumeId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Experience e = new Experience();
                e.setExperienceId(rs.getInt("experience_id"));
                e.setResumeId(rs.getInt("resume_id"));
                e.setCompany(rs.getString("company"));
                e.setJobRole(rs.getString("job_role"));
                e.setStartDate(rs.getDate("start_date"));
                e.setEndDate(rs.getDate("end_date"));
                e.setDescription(rs.getString("description"));
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Implementing these to satisfy the Interface contract
    @Override
    public boolean deleteExperience(int expId) {
        String sql = "DELETE FROM experience WHERE experience_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, expId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    @Override public boolean updateExperience(Experience exp) { return false; }
    @Override public List<Experience> getExperienceByUserId(int userId) { return null; }
}