package com.servicemanager.service;

import com.servicemanager.entity.Workflow;
import com.servicemanager.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkflowService {

    private final WorkflowRepository workflowRepository;

    @Autowired
    public WorkflowService(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }


    @Transactional
    public Workflow createWorkflow(Workflow workflow){
        return workflowRepository.save(workflow);
    }

    public List<Workflow> findAll(){
        return workflowRepository.findAll();
    }




}
