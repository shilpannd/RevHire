package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.EmployerProfile;

public interface EmployerProfileDAO {

    /**
     * Creates a blank employer profile linked to a user account.
     * Usually called when a user registers as an 'EMPLOYER'.
     */
    boolean createEmployerProfile(int userId);

    /**
     * Retrieves the company profile based on the logged-in User ID.
     */
    EmployerProfile getProfileByUserId(int userId);

    /**
     * Updates company-specific information.
     */
    boolean updateProfile(EmployerProfile profile);

    /**
     * Checks if a company profile already exists for a user.
     */
    boolean profileExists(int userId);
}