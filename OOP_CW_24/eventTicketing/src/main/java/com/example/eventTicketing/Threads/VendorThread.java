package com.example.eventTicketing.Threads;

import com.example.eventTicketing.Repository.EventRepository;
import com.example.eventTicketing.Repository.VendorRepository;
import com.example.eventTicketing.classes.Ticket;
import com.example.eventTicketing.classes.TicketPool;
import com.example.eventTicketing.config.SystemConfiguration;
import com.example.eventTicketing.model.Event;
import com.example.eventTicketing.service.impl.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;

public class VendorThread implements Runnable {
    private TicketPool ticketPool;
    private Event event;
    private SystemConfiguration config;
    private EventRepository eventRepository;
    private VendorRepository vendorRepository;


    public VendorThread(TicketPool ticketPool, Event event, EventRepository eventRepository, VendorRepository vendorRepository, SystemConfiguration config) {
        this.ticketPool = ticketPool;
        this.event = event;
        this.config = config;
        this.eventRepository = eventRepository;
        this.vendorRepository = vendorRepository;
    }

    //    @Override
//    public void run() {
//
//        while (ticketPool.getAvailableTicketCount() < config.getMaxTicketCapacity()) {
////            System.out.println("Max Capacity: " + ticketPool.getMaxCapacity());
//            Ticket ticket = new Ticket( 120, event.getId());
////            ticketPool.addTickets(ticket);
//            event.getTicketPool().addTickets(ticket);
////            event.setTicketPool(ticketPool);
////            EventService eventService = new EventService();
////            eventRepository.updateTicketPool(event.getId(), ticketPool);
//
////            Event eventOnDB = eventRepository.findById(event.getId())
////                    .orElseThrow(() -> new RuntimeException("Event not found with ID: " + event.getId()));
////            eventOnDB.setTicketPool(ticketPool);
////            eventRepository.addTicketToPool(event.getId(), new Ticket(120, event.getId()));
//            System.out.println("Current Ticket Pool: " + ticketPool.getAvailableTicketCount() + " tickets available.");
//            eventRepository.save(event);
//
//            System.out.println("MongoDB ID: " + event.getId());
//            System.out.println("Added ticket: " + ticket.getId() + " to event: " + event.getName());
//
//            try {
//                Thread.sleep(calculateReleaseInterval());
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                break;
//            }
//        }
//    }
    @Override
    public void run() {
        while (ticketPool.getAvailableTicketCount() < config.getMaxTicketCapacity()) {
            // Create a new ticket
            Ticket ticket = new Ticket(120, event.getId(), event.getName());
            ticketPool.addTickets(ticket);
            System.out.println("Releasing ticket: " + ticket.getId() + " to event: " + event.getName());

            // Add ticket to the pool using targeted update
//            int result = eventRepository.addTicketToPool(event.getId(), ticket);
            int updatedCount = eventRepository.addTicketToPool(event.getId(), config.getMaxTicketCapacity(), ticket);


//            if (updatedCount > 0) {
//                System.out.println("Added ticket: " + ticket.getId());
//            } else {
//                System.out.println("Failed to add ticket. Event might be at full capacity.");
//                break;
//            }
            if (updatedCount > 0) {
                System.out.println("Ticket added successfully.");
            } else {
                System.out.println("Ticket pool is at full capacity.");
//                break;
            }

            // Sleep for the interval
            try {
                Thread.sleep(calculateReleaseInterval());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private long calculateReleaseInterval() {
        // Convert release rate to milliseconds between ticket releases
        return (long) (60000 / config.getTicketReleaseRate());
    }
}
