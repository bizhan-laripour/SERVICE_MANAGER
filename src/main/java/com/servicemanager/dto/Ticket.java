package com.servicemanager.dto;

import com.servicemanager.enums.Status;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;


public class Ticket implements Serializable {


    private Long id;

    private String ticketName;

    private String ticketNo;

    private Status status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
