package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.Certifications;
import java.util.List;

public interface CertificationsDAO {
    /**
     * Adds a new certification for a user.
     */
    boolean addCertification(Certifications cert);

    /**
     * Retrieves all certifications belonging to a specific user.
     */
    List<Certifications> getCertsByUserId(int userId);
}