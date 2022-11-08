package com.servicemanager.consumer;

import com.google.gson.Gson;
import com.servicemanager.dto.ConsumerDto;
import com.servicemanager.dto.Ticket;
import com.servicemanager.entity.Workflow;
import com.servicemanager.events.fail.WorkflowFailEvent;
import com.servicemanager.events.success.WorkFlowSuccessEvent;
import com.servicemanager.service.WorkflowService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowConsumer {


    private final WorkflowService workflowService;

    private final WorkFlowSuccessEvent workFlowSuccessEvent;
    private final WorkflowFailEvent workflowFailEvent;


    @Autowired
    public WorkflowConsumer(WorkflowService workflowService, WorkFlowSuccessEvent workFlowSuccessEvent, WorkflowFailEvent workflowFailEvent) {
        this.workflowService = workflowService;
        this.workFlowSuccessEvent = workFlowSuccessEvent;
        this.workflowFailEvent = workflowFailEvent;
    }

    @KafkaListener(topics = "student", containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, ConsumerDto> review) {
        ConsumerDto consumerDto = review.value();
        Gson gson = new Gson();
        String t = gson.toJson(consumerDto.getObject());
        Ticket ticket1 = gson.fromJson(t, Ticket.class);
        try {
            Workflow workflow = new Workflow();
            workflow.setWorkFlowName("ticket_workflow");
            workflow.setTicketNo(ticket1.getTicketNo());
            workflowService.createWorkflow(workflow);
            workFlowSuccessEvent.workFlowSuccessfully(ticket1.getTicketNo());
        } catch (Exception ex) {
            workflowFailEvent.workflowFailed(ticket1.getTicketNo());
        }
    }



    @KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
    @SendTo
    public ConsumerDto handle(ConsumerDto consumerDto) throws InterruptedException {
        List<Workflow> list = workflowService.findAll();
        consumerDto.setObject(list);
        return consumerDto;
    }
}
