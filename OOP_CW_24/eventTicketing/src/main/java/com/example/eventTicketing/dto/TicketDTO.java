package com.example.eventTicketing.dto;

public class TicketDTO {

    private Long id; // Auto-generated; included in responses
    private String ticketCode;
    private Double price;
    private boolean isAvailable;


    // Constructors
    public TicketDTO() {}

    public TicketDTO(Long id, String ticketCode, Double price, boolean isAvailable) {
        this.id = id;
        this.ticketCode = ticketCode;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

