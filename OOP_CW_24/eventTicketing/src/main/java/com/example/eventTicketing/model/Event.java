package com.example.eventTicketing.model;

import com.example.eventTicketing.classes.Ticket;
import com.example.eventTicketing.classes.TicketPool;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Document
public class Event {
    @Id
    private String id;

    private String name;

    private String location;

    private LocalDate eventDate;

    private LocalTime eventTime;
    @DBRef
    @JsonBackReference // Prevents circular reference during JSON serialization
    private Vendor vendor;

    private int totalTickets;

    private TicketPool ticketPool;

    public Event() {
    }

    public Event(String id, String name, String location, LocalDate eventDate, LocalTime eventTime, Vendor vendor, int totalTickets, TicketPool ticketPool) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.vendor = vendor;
        this.totalTickets = totalTickets;
        this.ticketPool = ticketPool;
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

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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

@Override
public String toString() {
    return "Event{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", location='" + location + '\'' +
            ", eventDate=" + eventDate +
            ", eventTime=" + eventTime +
            ", vendor=" + vendor +
            ", totalTickets=" + totalTickets +
            ", ticketPool=" + ticketPool.toString() +
            '}';
}
}
