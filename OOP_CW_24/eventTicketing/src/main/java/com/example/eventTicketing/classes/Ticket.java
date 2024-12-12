package com.example.eventTicketing.classes;

import com.example.eventTicketing.model.Customer;
import com.example.eventTicketing.model.Event;

import java.util.UUID;

public class Ticket {
    private String id;
    private String eventId;
    private String eventName;
    private double price;
    private boolean isSold;
    private String customerId;

    public Ticket( double price, String eventId, String eventName) {
        this.id = UUID.randomUUID().toString();
        this.price = price;
        this.isSold = false;
        this.customerId = "";
        this.eventId = eventId;
        this.eventName = eventName;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public double getPrice() {
        return price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
