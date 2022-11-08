package com.servicemanager.producer;

import com.servicemanager.dto.ConsumerDto;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class WorkflowProducer {

    private final KafkaTemplate<String, ConsumerDto> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(WorkflowProducer.class);

    @Autowired
    public WorkflowProducer(KafkaTemplate<String, ConsumerDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ConsumerDto result) {
        String topicName = "result";
        ListenableFuture<SendResult<String, ConsumerDto>> future = kafkaTemplate.send(topicName, result);

        //This will check producer result asynchronously to avoid thread blocking
        future.addCallback(new ListenableFutureCallback<SendResult<String, ConsumerDto>>() {
            @Override
            public void onFailure(@NotNull Throwable throwable) {
                logger.error("Failed to send message", throwable);
            }

            @Override
            public void onSuccess(SendResult<String, ConsumerDto> stringStringSendResult) {
                logger.info("Sent message successfully");
            }
        });
    }

}
