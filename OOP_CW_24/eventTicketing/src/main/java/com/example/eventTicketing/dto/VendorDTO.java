package com.example.eventTicketing.dto;

import com.example.eventTicketing.model.Event;

import java.util.List;

public class VendorDTO {

    private String id; // Auto-generated; included in responses
    private String name;
    private String contactEmail;
    private String contactPhone;
    private String password;
    private List<EventDTO> events;

    // Constructors
    public VendorDTO() {}

    public VendorDTO(String id, String name, String contactEmail, String contactPhone, String password, List<EventDTO> events) {
        this.id = id;
        this.name = name;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.password = password;
        this.events = events;
    }

    // Getters and Setters
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }
}