package com.evil.inc.githuber.service.impl;

import com.evil.inc.githuber.service.LogShippingConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaLogConsumerServiceImpl implements LogShippingConsumerService {
    @KafkaListener(topics = "logs", groupId = "githuber")
    @Override
    public void consume(String message) {
        log.info("Kafka => Consumed message: {}", message);
    }
}
