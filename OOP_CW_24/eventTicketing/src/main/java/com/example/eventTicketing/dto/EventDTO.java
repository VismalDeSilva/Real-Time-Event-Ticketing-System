package com.example.eventTicketing.dto;

import com.example.eventTicketing.classes.TicketPool;
import com.example.eventTicketing.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class EventDTO {

    private String id; // Auto-generated; included in responses
    private String name;
    private String location;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String vendorId; // Link to the vendor
    private int totalTickets;
    private TicketPool ticketPool;


    public EventDTO() {
    }

    public EventDTO(String id, String name, String location, LocalDate eventDate, LocalTime eventTime, String vendorId, int totalTickets, TicketPool ticketPool) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.vendorId = vendorId;
        this.totalTickets = totalTickets;
        this.ticketPool = ticketPool;
    }

    public EventDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.location = event.getLocation();
        this.eventDate = event.getEventDate();
        this.eventTime = event.getEventTime();
        this.vendorId = event.getVendor().getId();
        this.totalTickets = event.getTotalTickets();
        this.ticketPool = event.getTicketPool();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }
}
