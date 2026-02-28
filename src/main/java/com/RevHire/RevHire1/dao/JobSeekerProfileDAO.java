package com.RevHire.RevHire1.dao;


import com.RevHire.RevHire1.models.JobSeekerProfile;
import java.util.List;

public interface JobSeekerProfileDAO {

    // 1. Create: Used during onboarding after a User registers
    boolean createProfile(JobSeekerProfile profile);

    // 2. Read: Used to display the dashboard or for employers to view
    JobSeekerProfile getProfileByUserId(int userId);

    // 3. Update: Used when the user wants to change their phone, location, etc.
    boolean updateProfile(JobSeekerProfile profile);

    // 4. Delete: Used if a user wants to deactivate their account
    boolean deleteProfile(int userId);
    
    // 5. Search: (Optional) Used by employers to find candidates by location
    List<JobSeekerProfile> getProfilesByLocation(String location);
}
