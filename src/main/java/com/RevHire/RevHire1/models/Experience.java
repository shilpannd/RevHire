package com.RevHire.RevHire1.models;


import java.sql.Date;
import java.util.List;

public class Experience {

    private int experienceId;
    private int resumeId;
    private String company;
    private String jobRole;
    private Date startDate;
    private Date endDate;
    private String description;
	public int getExperienceId() {
		return experienceId;
	}
	public void setExperienceId(int experienceId) {
		this.experienceId = experienceId;
	}
	public int getResumeId() {
		return resumeId;
	}
	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getJobRole() {
		return jobRole;
	}
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getUserId() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setUserId(int int1) {
		// TODO Auto-generated method stub
		
	}
	public boolean addWorkHistory(Experience newExp) {
		// TODO Auto-generated method stub
		return false;
	}
	public List<Experience> getUserExperience(int userId) {
		// TODO Auto-generated method stub
		return null;
	}


    // getters and setters
}
