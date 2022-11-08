package com.servicemanager.events.success;

import com.servicemanager.annotations.Success;
import com.servicemanager.dto.ConsumerDto;
import com.servicemanager.dto.Review;
import com.servicemanager.entity.Workflow;
import com.servicemanager.producer.WorkflowProducer;

@Success
public class WorkFlowSuccessEvent {

    private final WorkflowProducer workflowProducer;

    public WorkFlowSuccessEvent(WorkflowProducer workflowProducer){
        this.workflowProducer = workflowProducer;
    }

    public void workFlowSuccessfully(String ticketNo){
        ConsumerDto consumerDto = new ConsumerDto();
        Review review = new Review();
        review.setContent("success");
        review.setId(Long.valueOf(ticketNo));
        consumerDto.setName("workflow");
        consumerDto.setObject(review);
        workflowProducer.sendMessage(consumerDto);
    }
}
