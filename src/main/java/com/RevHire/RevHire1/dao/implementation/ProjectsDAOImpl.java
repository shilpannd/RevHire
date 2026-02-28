package com.RevHire.RevHire1.dao.implementation;

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.ProjectsDAO;
import com.RevHire.RevHire1.models.Projects;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectsDAOImpl implements ProjectsDAO {
    @Override
    public boolean addProject(Projects project) {
        String sql = "INSERT INTO projects (resume_id, title, description, tech_stack) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, project.getResumeId());
            ps.setString(2, project.getTitle());
            ps.setString(3, project.getDescription());
            ps.setString(4, project.getTechStack());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public List<Projects> getProjectsByResumeId(int resumeId) {
        List<Projects> list = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE resume_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, resumeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Projects p = new Projects();
                p.setProjectId(rs.getInt("project_id"));
                p.setTitle(rs.getString("title"));
                p.setDescription(rs.getString("description"));
                p.setTechStack(rs.getString("tech_stack"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

	@Override
	public boolean deleteProject(int projectId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProject(Projects project) {
		// TODO Auto-generated method stub
		return false;
	}
}