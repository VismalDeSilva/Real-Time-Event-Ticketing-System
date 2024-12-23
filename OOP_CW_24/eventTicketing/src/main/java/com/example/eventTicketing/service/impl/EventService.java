package com.example.eventTicketing.service.impl;


import com.example.eventTicketing.Repository.CustomerRepository;
import com.example.eventTicketing.Repository.EventRepository;
import com.example.eventTicketing.Repository.VendorRepository;
import com.example.eventTicketing.Threads.CustomerThread;
import com.example.eventTicketing.Threads.VendorThread;
import com.example.eventTicketing.classes.Ticket;
import com.example.eventTicketing.classes.TicketPool;
import com.example.eventTicketing.config.SystemConfiguration;
import com.example.eventTicketing.dto.EventDTO;
import com.example.eventTicketing.model.Customer;
import com.example.eventTicketing.model.Event;
import com.example.eventTicketing.model.Vendor;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private SystemConfiguration systemConfiguration;


    public EventService(EventRepository eventRepository, VendorRepository vendorRepository, CustomerRepository customerRepository, SystemConfiguration systemConfiguration) {
        this.eventRepository = eventRepository;
        this.vendorRepository = vendorRepository;
        this.customerRepository = customerRepository;
        this.systemConfiguration = systemConfiguration;
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        // Find the vendor
        Vendor vendor = vendorRepository.findById(eventDTO.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        // Create new event
        Event event = new Event();


        System.out.println("hallo: " + systemConfiguration.getTotalTickets());
        event.setName(eventDTO.getName());
        event.setLocation(eventDTO.getLocation());
        event.setEventDate(eventDTO.getEventDate());
        event.setEventTime(eventDTO.getEventTime());
        event.setVendor(vendor);
        event.setTicketPool(new TicketPool(systemConfiguration));
        event.setTotalTickets(Math.min(systemConfiguration.getMaxTicketCapacity(), eventDTO.getTotalTickets()));


        // Save event
        Event savedEvent = eventRepository.save(event);
        // Update vendor's events array
        if (vendor.getEvents() == null) {
            vendor.setEvents(new ArrayList<>());
        }
        vendor.getEvents().add(savedEvent);

        // Save updated vendor
        vendorRepository.save(vendor);
        return convertToDTO(savedEvent);
    }

    // Utility method to convert Event to EventDTO
    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setLocation(event.getLocation());
        dto.setEventDate(event.getEventDate());
        dto.setEventTime(event.getEventTime());
        dto.setVendorId(event.getVendor().getId());
        dto.setTotalTickets(event.getTotalTickets());
        System.out.println("Total Tickets: " + event.getTotalTickets());
        System.out.println("Max Capacity: " + event.getTicketPool().getMaxCapacity());
        System.out.println("Available Tickets count: " + event.getTicketPool().getAvailableTicketCount());
        System.out.println("TicketPool content: " + event.getTicketPool().getTickets());
        dto.setTicketPool(event.getTicketPool());


        // If you want to include tickets, uncomment and implement
        // dto.setTickets(event.getTickets().stream()
        //     .map(ticket -> new TicketDTO(
        //         ticket.getId(),
        //         ticket.getTicketCode(),
        //         ticket.getPrice(),
        //         ticket.isAvailable()))
        //     .collect(Collectors.toList()));

        return dto;
    }


    public EventDTO getEventById(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
//        System.out.println("TicketPool content: " + event.getTicketPool().getTickets());
        System.out.println("Event: " + event);
        System.out.println("Event Details: " + event.getTicketPool());
        return convertToDTO(event);
    }

    //    public String getAllEvents() {
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
//        return "hello world";
    }

    public List<EventDTO> getEventsByVendor(Long vendorId) {
        return eventRepository.findByVendorId(vendorId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventDTO updateEvent(String eventId, EventDTO eventDTO) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Update event details
        existingEvent.setName(eventDTO.getName());
        existingEvent.setLocation(eventDTO.getLocation());
        existingEvent.setEventDate(eventDTO.getEventDate());
        existingEvent.setEventTime(eventDTO.getEventTime());

        // If vendor is being updated
        if (eventDTO.getVendorId() != null) {
            Vendor vendor = vendorRepository.findById(eventDTO.getVendorId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));
            existingEvent.setVendor(vendor);
        }

        Event updatedEvent = eventRepository.save(existingEvent);
        return convertToDTO(updatedEvent);
    }


    public void deleteEvent(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventRepository.delete(event);
    }

    public void startTicketingThreads(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));

        TicketPool ticketPool = event.getTicketPool();
        System.out.println("Max Capacity: " + ticketPool.getMaxCapacity());
        // Start Vendor Thread
        VendorThread vendorThread = new VendorThread(ticketPool, event, eventRepository, vendorRepository, systemConfiguration);
        Thread vendor = new Thread(vendorThread, "VendorThread");
        vendor.start();

        // Start Customer Threads
//        for (int i = 1; i <= customerCount; i++) {
//            CustomerThread customerThread = new CustomerThread(ticketPool, systemConfiguration, "Customer-" + i);
//            Thread customer = new Thread(customerThread, "CustomerThread-" + i);
//            customer.start();
//        }

        // Optional: Monitor threads
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            vendor.interrupt();
            Thread.getAllStackTraces().keySet().stream()
                    .filter(t -> t.getName().startsWith("CustomerThread"))
                    .forEach(Thread::interrupt);
        }));
    }


//    public void updateTicketPool(String eventId, TicketPool ticketPool) {
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
//        event.setTicketPool(ticketPool);
//        eventRepository.save(event);
//    }

//    public Ticket buyTicket(String eventId, String customerId) {
//
//        Customer customer = customerRepository.findById(customerId)
//        .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
//
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
//        TicketPool ticketPool = event.getTicketPool();
//        Ticket availableTicket = ticketPool.getTickets().stream()
//                .filter(ticket -> !ticket.isSold())
//                .findFirst()
//                .orElse(null);
//        if (availableTicket != null) {
//            System.out.println(availableTicket.getId());
//            availableTicket.setSold(true);
//            availableTicket.setCustomerId(customerId);
//            return availableTicket;
//        } else {
//            // Handle the case when no available tickets are found
//            return null;
//        }
//    }

    public void buyTicket(String eventId, String customerId, int numberOfTicketsToBuy) {
        Customer customerRepo = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
        TicketPool ticketPool = event.getTicketPool();
        CustomerThread customerThread = new CustomerThread(ticketPool, systemConfiguration, customerId, eventRepository,customerRepository, event, numberOfTicketsToBuy);
        Thread customer = new Thread(customerThread, "CustomerThread");
        customer.start();
    }
//    try {
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
//
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
//        TicketPool ticketPool = event.getTicketPool();
//        System.out.println("Available tickets count: " + ticketPool.getAvailableTicketCount());
//        Ticket availableTicket = ticketPool.getTickets().stream()
//                .filter(ticket -> !ticket.isSold())
//                .findFirst()
//                .orElse(null);
//        if (availableTicket != null) {
//            availableTicket.setSold(true);
//            availableTicket.setCustomerId(customerId);
//            ticketPool.removeTicket();
//            System.out.println("Ticket Sold, Ticket ID: " + availableTicket.getId());
//            System.out.println("New available tickets count: " + ticketPool.getAvailableTicketCount());
////            event.setTicketPool(ticketPool);
//            eventRepository.save(event);
//            return "Ticket purchased successfully. Ticket ID: " + availableTicket.getId();
//        } else {
//            return "No tickets available for this event.";
//        }
//    } catch (RuntimeException e) {
//        return "Error purchasing ticket: " + e.getMessage();
//    }


//    return "Ticket purchased successfully. Ticket ID: " + availableTicket.getId();




}
