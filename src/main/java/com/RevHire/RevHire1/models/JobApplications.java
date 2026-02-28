package com.RevHire.RevHire1.models;

import java.sql.Timestamp;

public class JobApplications {

    private int applicationId;
    private int jobId;
    private int userId;
    private String status;
    private String coverLetter;
    private String withdrawalReason;
    private String employerComments;
    private Timestamp appliedAt;
    
    // ADDED: Fields to hold joined data for the View
    private String jobTitle;
    private String companyName;

    // Standard Getters and Setters
    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public String getWithdrawalReason() { return withdrawalReason; }
    public void setWithdrawalReason(String withdrawalReason) { this.withdrawalReason = withdrawalReason; }

    public String getEmployerComments() { return employerComments; }
    public void setEmployerComments(String employerComments) { this.employerComments = employerComments; }

    public Timestamp getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Timestamp appliedAt) { this.appliedAt = appliedAt; }

    // FIXED: Proper String getters/setters for the UI
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}