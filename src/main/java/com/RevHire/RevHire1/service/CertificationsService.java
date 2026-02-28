package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.implementation.CertificationsDAOImpl;
import com.RevHire.RevHire1.models.Certifications;
import java.sql.Date;
import java.util.List;

public class CertificationsService {
    private CertificationsDAOImpl certDAO = new CertificationsDAOImpl();

    public boolean addCertification(int userId, String name, String org, String dateStr) {
        try {
            Certifications c = new Certifications();
            c.setUserId(userId);
            c.setCertName(name);
            c.setIssuingOrganization(org);
            c.setIssueDate(Date.valueOf(dateStr)); // Standard format: YYYY-MM-DD
            return certDAO.addCertification(c);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid date format. Please use YYYY-MM-DD.");
            return false;
        }
    }

    // FIX: Instead of writing SQL here, call the DAO!
    public List<Certifications> getCertificationsByUser(int userId) {
        return certDAO.getCertsByUserId(userId);
    }
}