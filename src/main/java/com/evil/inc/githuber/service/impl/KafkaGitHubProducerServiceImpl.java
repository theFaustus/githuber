package com.evil.inc.githuber.service.impl;

import com.evil.inc.githuber.domain.GitHubRepo;
import com.evil.inc.githuber.service.GitHubProducerService;
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
public class KafkaGitHubProducerServiceImpl implements GitHubProducerService {
    private final KafkaTemplate<String, GitHubRepo> kafkaTemplate;

    @Override
    public void sendRepo(GitHubRepo gitHubRepo) {
        log.info("Kafka => Producing message={}", gitHubRepo);

        ListenableFuture<SendResult<String, GitHubRepo>> future = kafkaTemplate.send("trending", gitHubRepo);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("Kafka => Unable to send message={} due to : {}", gitHubRepo, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, GitHubRepo> result) {
                log.info("Kafka => Sent message={} with offset={}", gitHubRepo, result.getRecordMetadata().offset());
            }
        });
    }
}
