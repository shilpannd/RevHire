package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.Resume;

public interface ResumeDAO {

    /**
     * Initializes a new resume record for a user.
     * Usually called during registration or first-time profile setup.
     */
    int createResume(int userId);

    /**
     * Retrieves the primary key (resume_id) associated with a specific user.
     * Crucial for adding Experience, Education, and Skills.
     */
    int getResumeIdByUserId(int userId);

    /**
     * Fetches the full Resume model object for displaying profile summaries.
     */
    Resume getResumeByUserId(int userId);

    /**
     * Updates the summary or objective section of the resume.
     */
    boolean updateSummary(int resumeId, String summary);
    
    /**
     * Deletes a resume record (optional, usually handled by cascading deletes).
     */
    boolean deleteResume(int resumeId);
    
}