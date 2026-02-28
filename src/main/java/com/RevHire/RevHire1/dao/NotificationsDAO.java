package com.RevHire.RevHire1.dao;

import com.RevHire.RevHire1.models.Notifications;
import java.util.List;

public interface NotificationsDAO {
    List<Notifications> getNotificationsByUserId(int userId);
    
    // Ensure this exact signature is here
    boolean addNotification(int userId, String message);
    
    boolean markAsRead(int notificationId);
}