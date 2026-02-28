package com.RevHire.RevHire1.dao.implementation;

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.CertificationsDAO;
import com.RevHire.RevHire1.models.Certifications;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertificationsDAOImpl implements CertificationsDAO {
    public boolean addCertification(Certifications cert) {
        String sql = "INSERT INTO certifications (user_id, cert_name, organization, issue_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cert.getUserId());
            ps.setString(2, cert.getCertName());
            ps.setString(3, cert.getIssuingOrganization());
            ps.setDate(4, cert.getIssueDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<Certifications> getCertsByUserId(int userId) {
        List<Certifications> list = new ArrayList<>();
        String sql = "SELECT * FROM certifications WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Certifications c = new Certifications();
                c.setCertName(rs.getString("cert_name"));
                c.setIssuingOrganization(rs.getString("organization"));
                c.setIssueDate(rs.getDate("issue_date"));
                list.add(c);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}