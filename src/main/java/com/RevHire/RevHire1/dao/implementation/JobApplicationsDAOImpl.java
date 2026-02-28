package com.RevHire.RevHire1.dao.implementation;

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.JobApplicationsDAO;
import com.RevHire.RevHire1.models.JobApplications;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobApplicationsDAOImpl implements JobApplicationsDAO {

    @Override
    public boolean submitApplication(int userId, int jobId) {
        // Table: job_applications | Columns: user_id, job_id, status
        String sql = "INSERT INTO job_applications (user_id, job_id, status, applied_at) VALUES (?, ?, 'APPLIED', CURRENT_TIMESTAMP)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, jobId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<JobApplications> getApplicationsByUserId(int userId) {
        List<JobApplications> list = new ArrayList<>();
        // Joining with jobs and employer_profile to get Title and Company
        String sql = "SELECT a.*, j.title, e.company_name " +
                     "FROM job_applications a " +
                     "JOIN jobs j ON a.job_id = j.job_id " +
                     "JOIN employer_profile e ON j.employer_id = e.user_id " +
                     "WHERE a.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JobApplications app = new JobApplications();
                app.setApplicationId(rs.getInt("application_id"));
                app.setJobId(rs.getInt("job_id"));
                app.setStatus(rs.getString("status"));
                app.setAppliedAt(rs.getTimestamp("applied_at")); // Pass Timestamp, not String
                app.setJobTitle(rs.getString("title")); 
                app.setCompanyName(rs.getString("company_name"));
                list.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<JobApplications> getApplicationsByEmployerId(int employerId) {
        List<JobApplications> list = new ArrayList<>();
        // This query joins Job_Applications with Jobs to find only THIS employer's jobs
        String sql = "SELECT ja.*, j.title FROM job_applications ja " +
                     "JOIN jobs j ON ja.job_id = j.job_id " +
                     "WHERE j.employer_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JobApplications app = new JobApplications();
                app.setApplicationId(rs.getInt("application_id"));
                app.setUserId(rs.getInt("user_id"));
                app.setJobId(rs.getInt("job_id"));
                app.setStatus(rs.getString("status"));
                app.setJobTitle(rs.getString("title")); // Ensure your model has this field
                list.add(app);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public boolean updateApplicationStatus(int applicationId, String status) {
        String sql = "UPDATE job_applications SET status = ? WHERE application_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, applicationId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}