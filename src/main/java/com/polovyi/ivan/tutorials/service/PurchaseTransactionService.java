package com.polovyi.ivan.tutorials.service;

import com.polovyi.ivan.tutorials.dto.PurchaseTransactionDTO;
import com.polovyi.ivan.tutorials.dto.PurchaseTransactionRequest;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseTransactionService {

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    @Value("${aws.sns-topic.name}")
    private String topicName;

    public void processRequest(PurchaseTransactionRequest request) {

        log.info("Received request {}.", request);
        PurchaseTransactionDTO dto = PurchaseTransactionDTO.valueOf(request);

        log.info("Sending purchase transaction");

        // Using send method
        Message<PurchaseTransactionDTO> message = new GenericMessage<>(dto);
        dto.setSentFrom("Method send args <<generic message>>. Using default topic.");
        log.info("Method send args <<generic message>>. Using default topic.");
        notificationMessagingTemplate.send(message);

        dto.setSentFrom("Method send args <<topic name>> and <<generic message>>.");
        log.info("Method send args <<topic name>> and <<generic message>>.");
        notificationMessagingTemplate.send(topicName, message);

        log.info("Method send args <<topic name>> and <<generic message>>. Message with headers");
        HashMap<String, Object> messageHeaders = new HashMap<>();
        messageHeaders.put("Key_send_message", "Value_send_message");
        dto.setSentFrom("Method send args <<topic name>> and <<generic message>>. Message with headers");
        message = new GenericMessage<>(dto, messageHeaders);
        notificationMessagingTemplate.send(topicName, message);

        //Using convertAndSend method
        log.info("Method convertAndSend args <<object dto>>. Using default topic.");
        dto.setSentFrom("Method convertAndSend args <<object dto>>. Using default topic.");
        notificationMessagingTemplate.convertAndSend(new GenericMessage<>(dto));

        log.info("Method convertAndSend args  <<topic name>> and <<object dto>>. ");
        dto.setSentFrom("Method convertAndSend args <<topic name>> and <<object dto>>.");
        notificationMessagingTemplate.convertAndSend(topicName, dto);

        log.info("Method convertAndSend args <<topicName>> , <<object dto>>, <<messageHeaders>>.");
        dto.setSentFrom("Method convertAndSend args <<topicName>> , <<object dto>>, <<messageHeaders>>.");
        messageHeaders.put("Key_send_message", "Value_convertAndSend_message");
        notificationMessagingTemplate.convertAndSend(topicName, dto, messageHeaders);

        // Using sendNotification method
        log.info("Method sendNotification args <<object dto>>, <<subject>>. Using default topic.");
        dto.setSentFrom("Method sendNotification args <<object dto>>, <<subject>>. Using default topic.");
        notificationMessagingTemplate.sendNotification(dto, "purchase-transaction-subject");

        log.info("Method sendNotification args <<topicName>> , <<object dto>>, <<subject>>.");
        dto.setSentFrom("Method sendNotification args <<topicName>> , <<object dto>>, <<subject>>.");
        notificationMessagingTemplate.sendNotification(topicName, dto, "purchase-transaction-subject");
    }
}
