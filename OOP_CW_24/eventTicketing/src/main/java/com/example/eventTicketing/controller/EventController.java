package com.example.eventTicketing.controller;

import com.example.eventTicketing.classes.Ticket;
import com.example.eventTicketing.dto.EventDTO;
import com.example.eventTicketing.service.impl.EventService;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable String eventId) {
        EventDTO event = eventService.getEventById(eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
//    @GetMapping("/all")
//    public String getAllEvents() {
//        String events = eventService.getAllEvents();
//        return events;
//    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventDTO> updateEvent(
            @PathVariable String eventId,
            @RequestBody EventDTO eventDTO)
    {
        EventDTO updatedEvent = eventService.updateEvent(eventId, eventDTO);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{eventId}/start-ticketing")
    public ResponseEntity<String> startTicketing(@PathVariable String eventId) {
        eventService.startTicketingThreads(eventId);
        return ResponseEntity.ok("Ticketing process started for event: " + eventId);
    }


//    @PostMapping("/{eventId}/buy-ticket")
//    public ResponseEntity<String> buyTicket(@PathVariable String eventId, @RequestParam String customerId) {
//        Ticket result = eventService.buyTicket(eventId, customerId);
//        return ResponseEntity.ok("Ticket: " + result.getId());
//    }
    @PostMapping("/{eventId}/buy-ticket")

    public ResponseEntity<String> buyTicket(@PathVariable String eventId, @RequestParam String customerId, @RequestParam int numberOfTicketsToBuy) {
        try {
            eventService.buyTicket(eventId, customerId, numberOfTicketsToBuy);
//            return ResponseEntity.ok("Ticket: " + result.getId());
            return ResponseEntity.ok("Ticket bought successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error buying ticket: " + e.getMessage());
        }
    }
}
