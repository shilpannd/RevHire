package com.RevHire.RevHire1.dao.implementation;

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.JobsDAO;
import com.RevHire.RevHire1.models.Jobs;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobsDAOImpl implements JobsDAO {

    @Override
    public List<Jobs> getAllJobs() {
        List<Jobs> jobs = new ArrayList<>();
        String query = "SELECT * FROM jobs WHERE status = 'OPEN'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    @Override
    public List<Jobs> searchJobsByKeyword(String keyword) {
        List<Jobs> jobs = new ArrayList<>();
        String query = "SELECT * FROM jobs WHERE (title LIKE ? OR location LIKE ?) AND status = 'OPEN'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }

    @Override
    public List<Jobs> getJobsByEmployerId(int employerId) {
        List<Jobs> jobs = new ArrayList<>();
        String query = "SELECT * FROM jobs WHERE employer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    @Override
    public Jobs getJobById(int jobId) {
        String query = "SELECT * FROM jobs WHERE job_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, jobId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToJob(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addJob(Jobs job) {
        String sql = "INSERT INTO jobs (employer_id, title, description, experience_required, " +
                     "education_required, location, salary_min, salary_max, job_type, deadline, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, job.getEmployerId());
            stmt.setString(2, job.getTitle());
            stmt.setString(3, job.getDescription());
            
            // FIX 1: Use setInt for experience
            stmt.setInt(4, job.getExperienceRequired()); 
            
            stmt.setString(5, job.getEducationRequired());
            stmt.setString(6, job.getLocation());
            stmt.setDouble(7, job.getSalaryMin());
            stmt.setDouble(8, job.getSalaryMax());
            stmt.setString(9, job.getJobType());
            
            // FIX 2: Correct Date handling for java.sql.Date
            if (job.getDeadline() != null) {
                stmt.setDate(10, job.getDeadline());
            } else {
                stmt.setNull(10, java.sql.Types.DATE);
            }
            
            stmt.setString(11, job.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateJob(Jobs job) {
        String query = "UPDATE jobs SET title = ?, description = ?, location = ?, status = ? WHERE job_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, job.getTitle());
            stmt.setString(2, job.getDescription());
            stmt.setString(3, job.getLocation());
            stmt.setString(4, job.getStatus());
            stmt.setInt(5, job.getJobId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteJob(int jobId) {
        // We "soft delete" by updating the status to 'CLOSED'
        String sql = "UPDATE jobs SET status = 'CLOSED' WHERE job_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, jobId);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error closing job: " + e.getMessage());
            return false;
        }
    }

 
    private Jobs mapResultSetToJob(ResultSet rs) throws SQLException {
        Jobs job = new Jobs();
        job.setJobId(rs.getInt("job_id"));
        job.setEmployerId(rs.getInt("employer_id"));
        job.setTitle(rs.getString("title"));
        job.setDescription(rs.getString("description"));
        
   
        job.setExperienceRequired(rs.getInt("experience_required")); 
        
        job.setEducationRequired(rs.getString("education_required"));
        job.setLocation(rs.getString("location"));
        job.setSalaryMin(rs.getDouble("salary_min"));
        job.setSalaryMax(rs.getDouble("salary_max"));
        job.setJobType(rs.getString("job_type"));
        
        // FIX 4: Correct Date assignment for java.sql.Date
        java.sql.Date sqlDate = rs.getDate("deadline");
        if (sqlDate != null) {
            job.setDeadline(sqlDate);
        }
        
        job.setStatus(rs.getString("status"));
        return job;
    }

    @Override
    public void viewApplicationsByEmployer(int employerId) {
     
    }

    @Override
    public boolean applyForJob(int userId, int jobId, String coverLetter) {
        return false;
    }
}