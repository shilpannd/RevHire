package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.Projects;
import java.util.List;

public interface ProjectsDAO {

    /**
     * Saves a new project record to the database.
     * @param project The project object containing title, description, and tech stack.
     * @return true if the insertion was successful, false otherwise.
     */
    boolean addProject(Projects project);

    /**
     * Retrieves all projects associated with a specific resume.
     * @param resumeId The ID of the resume to fetch projects for.
     * @return A list of Projects objects.
     */
    List<Projects> getProjectsByResumeId(int resumeId);

    /**
     * Optional: Deletes a project by its unique ID.
     * @param projectId The ID of the project to remove.
     * @return true if deleted successfully.
     */
    boolean deleteProject(int projectId);
    
    /**
     * Optional: Updates existing project details.
     */
    boolean updateProject(Projects project);
}