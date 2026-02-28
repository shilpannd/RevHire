package com.RevHire.RevHire1.dao.implementation;

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.JobSkillsDAO;
import com.RevHire.RevHire1.models.Skills;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobSkillsDAOImpl implements JobSkillsDAO {

	public boolean addSkillToJob(int jobId, String skillName) {
	    // Step 1: Get the Skill ID (or create it if it doesn't exist)
	    int skillId = getOrCreateSkill(skillName);
	    
	    // Step 2: Link Job ID to Skill ID in the USR_REV.JOB_SKILLS table
	    String sql = "INSERT INTO USR_REV.JOB_SKILLS (job_id, skill_id) VALUES (?, ?)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, jobId);
	        ps.setInt(2, skillId);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        // Handle ORA-00001 (Unique constraint) if skill is already linked
	        return false;
	    }
	}

	@Override
	public int getOrCreateSkill(String skillName) {
	    
	    String selectSql = "SELECT skill_id FROM USR_REV.SKILLS WHERE UPPER(skill_name) = UPPER(?)";
	  
	    String insertSql = "INSERT INTO USR_REV.SKILLS (skill_id, skill_name) VALUES (skill_seq.NEXTVAL, ?)";
	    
	    try (Connection conn = DBConnection.getConnection()) {
	        PreparedStatement ps = conn.prepareStatement(selectSql);
	        ps.setString(1, skillName);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt("skill_id");
	        }

	        
	        String[] generatedColumns = {"skill_id"};
	        PreparedStatement insPs = conn.prepareStatement(insertSql, generatedColumns);
	        insPs.setString(1, skillName);
	        insPs.executeUpdate();
	        
	        ResultSet gRs = insPs.getGeneratedKeys();
	        if (gRs.next()) return gRs.getInt(1);
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1;
	}
    @Override
    public List<Skills> getSkillsByJobId(int jobId) {
        List<Skills> skillsList = new ArrayList<>();
        String sql = "SELECT s.skill_id, s.skill_name FROM skills s " +
                     "JOIN job_skills js ON s.skill_id = js.skill_id " +
                     "WHERE js.job_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Skills skill = new Skills();
                skill.setSkillId(rs.getInt("skill_id"));
                skill.setSkillName(rs.getString("skill_name"));
                skillsList.add(skill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillsList;
    }

    @Override
    public boolean removeSkillFromJob(int jobId, int skillId) {
        String sql = "DELETE FROM job_skills WHERE job_id = ? AND skill_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jobId);
            ps.setInt(2, skillId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}