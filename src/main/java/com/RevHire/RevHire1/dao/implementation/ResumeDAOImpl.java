package com.RevHire.RevHire1.dao.implementation;

import java.sql.*;
import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.ResumeDAO;
import com.RevHire.RevHire1.models.Resume;

public class ResumeDAOImpl implements ResumeDAO {

    @Override
    public int createResume(int userId) {
        String sql = "INSERT INTO resume (user_id) VALUES (?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, userId);
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); 
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; 
    }

    @Override
    public Resume getResumeByUserId(int userId) {
        String sql = "SELECT resume_id, user_id FROM resume WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Resume resume = new Resume();
                    resume.setResumeId(rs.getInt("resume_id"));
                    resume.setUserId(rs.getInt("user_id"));
                    return resume;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return null;
    }

    // FIXED METHOD: This was returning 0, causing your ORA-00001 error
    @Override
    public int getResumeIdByUserId(int userId) {
        String sql = "SELECT resume_id FROM resume WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("resume_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Returns 0 only if no record is found in the DB
    }

    @Override
    public boolean updateSummary(int resumeId, String summary) {
        String sql = "UPDATE resume SET summary = ? WHERE resume_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, summary);
            ps.setInt(2, resumeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteResume(int resumeId) {
        String sql = "DELETE FROM resume WHERE resume_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, resumeId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}