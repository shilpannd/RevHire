package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.ProjectsDAO;
import com.RevHire.RevHire1.dao.implementation.ProjectsDAOImpl;
import com.RevHire.RevHire1.models.Projects;
import java.util.List;

public class ProjectsService {
    private ProjectsDAO projectDAO = new ProjectsDAOImpl();

    public boolean addProject(int resumeId, String title, String desc, String tech) {
        Projects p = new Projects();
        p.setResumeId(resumeId);
        p.setTitle(title);
        p.setDescription(desc);
        p.setTechStack(tech);
        return projectDAO.addProject(p);
    }

    public List<Projects> getProjectsByResume(int resumeId) {
        return projectDAO.getProjectsByResumeId(resumeId);
    }
}