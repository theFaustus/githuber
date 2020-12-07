package com.evil.inc.githuber.service.impl;

import com.evil.inc.githuber.service.LogShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaLogShippingServiceImpl implements LogShippingService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendLog(String loggingMessage) {
        log.info("Kafka => Producing message={}", loggingMessage);

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("logs", loggingMessage);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("Kafka => Unable to send message={} due to : {}", loggingMessage, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Kafka => Sent message={} with offset={}", loggingMessage, result.getRecordMetadata().offset());
            }
        });
    }
}
