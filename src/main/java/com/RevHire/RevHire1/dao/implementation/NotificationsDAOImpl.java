package com.RevHire.RevHire1.dao.implementation;

import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.dao.NotificationsDAO;
import com.RevHire.RevHire1.models.Notifications;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationsDAOImpl implements NotificationsDAO {

    // Matches the Interface: getNotificationsByUserId
    @Override
    public List<Notifications> getNotificationsByUserId(int userId) {
        List<Notifications> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY notification_id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notifications n = new Notifications();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setUserId(rs.getInt("user_id"));
                n.setMessage(rs.getString("message"));
                
                // FIX: Manually convert Oracle's 1/0 to Java's true/false
                // This avoids compatibility issues with rs.getBoolean()
                int isReadVal = rs.getInt("is_read");
                n.setRead(isReadVal == 1); 
                
                list.add(n);
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return list;
    }

    @Override
    public boolean addNotification(int userId, String message) {
    	String sql = "INSERT INTO notifications (user_id, message, is_read) VALUES (?, ?, 0)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, message);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // Matches the Interface: markAsRead
    @Override
    public boolean markAsRead(int notificationId) {
    	String sql = "UPDATE notifications SET is_read = 1 WHERE notification_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, notificationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}