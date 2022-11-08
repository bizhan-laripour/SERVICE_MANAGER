package com.servicemanager.repository;

import com.servicemanager.entity.Workflow;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkflowRepository extends JpaRepository<Workflow , Long> {

    @NotNull
    Optional<Workflow> findById(@NotNull Long id);

    void deleteById(@NotNull Long id);




}
