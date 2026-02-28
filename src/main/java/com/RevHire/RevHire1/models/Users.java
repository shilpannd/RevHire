package com.RevHire.RevHire1.models;
import java.sql.Timestamp;

public class Users {

    private int userId;
    private String email;
    private String password;
    private String role;
    private String securityQuestion;
    private String securityAnswer;
    private int profileCompletionPercent;
    private Timestamp createdAt;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public int getProfileCompletionPercent() {
		return profileCompletionPercent;
	}
	public void setProfileCompletionPercent(int profileCompletionPercent) {
		this.profileCompletionPercent = profileCompletionPercent;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getPhone() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}

    // getters and setters
}
