package com.example.eventTicketing.dto;

import java.util.List;

public class PurchaseRequestDTO {
    private Long customerId;
    private List<Long> ticketIds;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Long> ticketIds) {
        this.ticketIds = ticketIds;
    }
}
