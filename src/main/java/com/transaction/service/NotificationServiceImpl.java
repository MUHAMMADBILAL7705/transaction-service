package com.transaction.service;

import com.transaction.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendEmail(NotificationType type, String email) {
        JSONObject obj = new JSONObject();
        obj.put("purpose", type.toString());
        obj.put("email", email);
        kafkaTemplate.send("EMAIL", obj.toString());
    }

    @Override
    public void sendSMS(NotificationType type, String phoneName) {
        JSONObject obj = new JSONObject();
        obj.put("purpose", type.toString());
        obj.put("phoneNumber", phoneName);
        kafkaTemplate.send("SMS", obj.toString());
    }
}
