package com.RevHire.RevHire1.service;

import java.util.List;

import com.RevHire.RevHire1.dao.ExperienceDAO;
import com.RevHire.RevHire1.dao.implementation.ExperienceDAOImpl;
import com.RevHire.RevHire1.models.Experience;

public class ExperienceService {
    private int experienceId;
    private int userId;
    private String jobTitle;
    private String company;
    private String description;
    
    private ExperienceDAO experienceDAO = new ExperienceDAOImpl();
    public List<Experience> getExperience(int resumeId) {
        return experienceDAO.getExperienceByResumeId(resumeId);
    }

    // Default Constructor
    public ExperienceService() {}

    // Getters and Setters
    public int getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(int experienceId) {
        this.experienceId = experienceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public boolean addExperience(Experience exp) {
		// TODO Auto-generated method stub
		return experienceDAO.addExperience(exp);
	}
	

}