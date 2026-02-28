package com.RevHire.RevHire1.dao.implementation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.JobSeekerProfileDAO;
import com.RevHire.RevHire1.models.JobSeekerProfile;


public class JobSeekerProfileDAOImpl implements JobSeekerProfileDAO {

    @Override
    public boolean createProfile(JobSeekerProfile profile) {
        String sql = """
            INSERT INTO job_seeker_profile
            (user_id, full_name, phone, location, total_experience)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFullName());
            ps.setString(3, profile.getPhone());
            ps.setString(4, profile.getLocation());
            ps.setInt(5, profile.getTotalExperience());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public JobSeekerProfile getProfileByUserId(int userId) {
        String sql = "SELECT * FROM job_seeker_profile WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                JobSeekerProfile profile = new JobSeekerProfile();
                profile.setUserId(rs.getInt("user_id"));
                profile.setFullName(rs.getString("full_name"));
                profile.setPhone(rs.getString("phone"));
                profile.setLocation(rs.getString("location"));
                profile.setTotalExperience(rs.getInt("total_experience"));
                return profile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateProfile(JobSeekerProfile profile) {
        String sql = """
            UPDATE job_seeker_profile
            SET full_name = ?, phone = ?, location = ?, total_experience = ?
            WHERE user_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, profile.getFullName());
            ps.setString(2, profile.getPhone());
            ps.setString(3, profile.getLocation());
            ps.setInt(4, profile.getTotalExperience());
            ps.setInt(5, profile.getUserId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	@Override
	public boolean deleteProfile(int userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<JobSeekerProfile> getProfilesByLocation(String location) {
		// TODO Auto-generated method stub
		return null;
	}
}