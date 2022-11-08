package com.servicemanager.events.fail;

import com.servicemanager.annotations.Failure;
import com.servicemanager.dto.ConsumerDto;
import com.servicemanager.dto.Review;
import com.servicemanager.producer.WorkflowProducer;

@Failure
public class WorkflowFailEvent {

    private final WorkflowProducer workflowProducer;

    public WorkflowFailEvent(WorkflowProducer workflowProducer){
        this.workflowProducer = workflowProducer;
    }

    public void workflowFailed(String ticketNo){
        ConsumerDto consumerDto = new ConsumerDto();
        Review review = new Review();
        review.setContent("fail");
        review.setId(Long.valueOf(ticketNo));
        consumerDto.setName("workflow");
        consumerDto.setObject(review);
        workflowProducer.sendMessage(consumerDto);
    }
}
