package com.RevHire.RevHire1.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.ResumeSkillsDAO;

public class ResumeSkillsDAOImpl implements ResumeSkillsDAO {

    @Override
    public boolean addSkillToResume(int resumeId, int skillId) {
        String sql = "INSERT INTO resume_skills (resume_id, skill_id) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, resumeId);
            ps.setInt(2, skillId);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            // Check for ORA-00001 (Unique constraint violation) in case skill is already added
            if (e.getErrorCode() == 1) { 
                System.out.println("⚠️ This skill is already attached to this resume.");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Integer> getSkillIdsByResume(int resumeId) {
        List<Integer> skillIds = new ArrayList<>();
        String sql = "SELECT skill_id FROM resume_skills WHERE resume_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, resumeId);
            var rs = ps.executeQuery();
            while (rs.next()) {
                skillIds.add(rs.getInt("skill_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillIds;
    }
    @Override
    public List<String> getSkillNamesByResume(int resumeId) {
        List<String> skillNames = new ArrayList<>();
        // SQL JOIN to get names from the skills table using the link in resume_skills
        String sql = "SELECT s.skill_name FROM skills s " +
                     "JOIN resume_skills rs ON s.skill_id = rs.skill_id " +
                     "WHERE rs.resume_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, resumeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                skillNames.add(rs.getString("skill_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skillNames;
    }
}