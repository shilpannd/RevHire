package com.RevHire.RevHire1.service;

import com.RevHire.RevHire1.dao.NotificationsDAO;
import com.RevHire.RevHire1.dao.implementation.NotificationsDAOImpl;
import com.RevHire.RevHire1.models.Notifications;
import java.util.List;

public class NotificationsService {
    private NotificationsDAO notificationDAO = new NotificationsDAOImpl();

    public List<Notifications> getNotificationsForUser(int userId) {
        return notificationDAO.getNotificationsByUserId(userId);
    }

    /**
     * FIXED: Renamed from sendNotification to addNotification 
     * to match the call in JobApplicationsService.
     */
    public boolean addNotification(int userId, String message) {
        // This ensures the DAO is called correctly
        return notificationDAO.addNotification(userId, message);
    }

    public boolean markAsRead(int notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }
}