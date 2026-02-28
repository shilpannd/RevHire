package com.RevHire.RevHire1.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.SkillsDAO;
import com.RevHire.RevHire1.models.Skills;

public class SkillsDAOImpl implements SkillsDAO {

    @Override
    public int getOrInsertSkill(String skillName) {
        // Query to check if skill already exists (case-insensitive)
        String selectSql = "SELECT skill_id FROM skills WHERE UPPER(skill_name) = UPPER(?)";
        // Query to insert new skill
        String insertSql = "INSERT INTO skills (skill_name) VALUES (?)";

        try (Connection conn = DBConnection.getConnection()) {
            // 1. Check if the skill already exists in the master list
            try (PreparedStatement psSelect = conn.prepareStatement(selectSql)) {
                psSelect.setString(1, skillName.trim());
                try (ResultSet rs = psSelect.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("skill_id"); // Return existing ID
                    }
                }
            }

            // 2. If it doesn't exist, insert it
            // We use Statement.RETURN_GENERATED_KEYS to get the ID created by the IDENTITY column
            try (PreparedStatement psInsert = conn.prepareStatement(insertSql, new String[]{"SKILL_ID"})) {
                psInsert.setString(1, skillName.trim());
                int affectedRows = psInsert.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = psInsert.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1); // Return the new ID
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in getOrInsertSkill: " + e.getMessage());
        }
        return -1; // Return -1 if something went wrong
    }

    @Override
    public List<Skills> getAllSkills() {
        List<Skills> skillsList = new ArrayList<>();
        String sql = "SELECT skill_id, skill_name FROM skills ORDER BY skill_name ASC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Skills skill = new Skills();
                skill.setSkillId(rs.getInt("skill_id"));
                skill.setSkillName(rs.getString("skill_name"));
                skillsList.add(skill);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching skills: " + e.getMessage());
        }
        return skillsList;
    }
}