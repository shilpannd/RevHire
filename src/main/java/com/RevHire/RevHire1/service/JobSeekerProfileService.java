package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.JobSeekerProfileDAO;
import com.RevHire.RevHire1.dao.implementation.JobSeekerProfileDAOImpl;
import com.RevHire.RevHire1.models.JobSeekerProfile;

public class JobSeekerProfileService {
    // FIX: Change 'new JobSeekerProfileDAO()' to 'new JobSeekerDAOImpl()'
    private JobSeekerProfileDAO profileDAO = new JobSeekerProfileDAOImpl();

    public JobSeekerProfile getProfileByUserId(int userId) {
        return profileDAO.getProfileByUserId(userId);
    }

    public boolean createProfile(JobSeekerProfile profile) {
        return profileDAO.createProfile(profile);
    }

	public int getResumeIdByUserId(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}
}