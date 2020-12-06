package com.evil.inc.githuber.service.impl;

import com.evil.inc.githuber.service.GitHubConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaGitHubConsumerServiceImpl implements GitHubConsumerService {
    @KafkaListener(topics = "trending", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        log.info("Kafka => Consumed message: {}", message);
    }
}
