package com.transaction.service;

import com.transaction.domain.NotificationType;

public interface NotificationService {

    void sendEmail(NotificationType type, String email);

    void sendSMS(NotificationType type, String phoneNumber);
}
