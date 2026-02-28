package com.RevHire.RevHire1.models;

public class Resume {
    private int resumeId;
    private int userId;
    private String summary; // Ensure this matches the name in your View

    // Default Constructor
    public Resume() {}

    // Getters and Setters
    public int getResumeId() { return resumeId; }
    public void setResumeId(int resumeId) { this.resumeId = resumeId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    // This is the missing method!
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}
