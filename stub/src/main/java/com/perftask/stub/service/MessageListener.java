package com.perftask.stub.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perftask.stub.dto.InboundMessage;
import com.perftask.stub.dto.OutboundMessage;
import com.perftask.stub.entity.MessageEntity;
import com.perftask.stub.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    private static final DateTimeFormatter OUTBOUND_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final MessageRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String topicOut;

    public MessageListener(
            MessageRepository repository,
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${app.kafka.topic-out}") String topicOut
    ) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.topicOut = topicOut;
    }

    @KafkaListener(topics = "${app.kafka.topic-in}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void onMessage(String payload) {
        try {
            InboundMessage inbound = objectMapper.readValue(payload, InboundMessage.class);
            LocalDateTime now = LocalDateTime.now();

            MessageEntity entity = new MessageEntity();
            entity.setId(UUID.randomUUID());
            entity.setMsgId(inbound.msgId());
            entity.setFullName(inbound.fullName() != null ? inbound.fullName().trim() : "");
            entity.setInn(inbound.inn());
            entity.setTime(now);

            MessageEntity saved = repository.save(entity);

            OutboundMessage outbound = new OutboundMessage(
                    saved.getId(),
                    saved.getTime().format(OUTBOUND_TIME_FORMAT)
            );
            String response = objectMapper.writeValueAsString(outbound);
            kafkaTemplate.send(topicOut, saved.getId().toString(), response);

            log.debug("Processed msgId={}, saved id={}", inbound.msgId(), saved.getId());
        } catch (Exception e) {
            log.error("Failed to process message: {}", payload, e);
            throw new IllegalStateException("Message processing failed", e);
        }
    }
}
