package com.example.eventTicketing.Threads;

import com.example.eventTicketing.Repository.CustomerRepository;
import com.example.eventTicketing.Repository.EventRepository;
import com.example.eventTicketing.classes.Ticket;
//import com.example.eventTicketing.classes.TicketPool;
import com.example.eventTicketing.classes.TicketPool;
import com.example.eventTicketing.config.SystemConfiguration;
import com.example.eventTicketing.model.Customer;
import com.example.eventTicketing.model.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CustomerThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(CustomerThread.class.getName());
    private TicketPool ticketPool;
    private SystemConfiguration config;
    private String customerId;
    private EventRepository eventRepository;
    private CustomerRepository customerRepository;
    private Event event;
    private int numberOfTicketsToBuy;

    public CustomerThread(TicketPool ticketPool, SystemConfiguration config, String customerId, EventRepository eventRepository,CustomerRepository customerRepository, Event event, int numberOfTicketsToBuy) {
        this.ticketPool = ticketPool;
        this.config = config;
        this.customerId = customerId;
        this.eventRepository = eventRepository;
        this.customerRepository = customerRepository;
        this.event = event;
        this.numberOfTicketsToBuy = numberOfTicketsToBuy;
    }

    @Override
    public void run() {
//        while (ticketPool.getAvailableTicketCount() > 0) {
//            String ticket = ticketPool.removeTicket();
//            if (ticket != null) {
//                processTicketPurchase(ticket);
//            }

        if (ticketPool.getAvailableTicketCount() > 0) {
            processTicketPurchase(numberOfTicketsToBuy);
            // Clear the thread after completing the processTicketPurchase
            Thread.currentThread().interrupt();
        }
    }

    private long calculateRetrievalInterval() {
        // Convert retrieval rate to milliseconds between ticket purchases
        return (long) (60000 / config.getCustomerRetrievalRate());
    }

    // Method to process ticket purchase
//    private void processTicketPurchase() {
//
//        try {
//            System.out.println("Available tickets count: " + ticketPool.getAvailableTicketCount());
//            Ticket availableTicket = ticketPool.getTickets().stream()
//                    .filter(ticket -> !ticket.isSold())
//                    .findFirst()
//                    .orElse(null);
//            if (availableTicket != null) {
//                availableTicket.setSold(true);
//                availableTicket.setCustomerId(customerId);
//                ticketPool.removeTicket(availableTicket, customerId);
//                System.out.println("Ticket Sold, Ticket ID: " + availableTicket.getId());
//                System.out.println("New available tickets count: " + ticketPool.getAvailableTicketCount());
//                eventRepository.save(event);
//                LOGGER.info(customerId + " purchased ticket: " + availableTicket.getId() +
//                " for event: " + event.getName() +
//                " at price: $" + availableTicket.getPrice());
//            } else {
//                LOGGER.info("No tickets available for event: " + event.getName());
//            }
//        } catch (RuntimeException e) {
//            LOGGER.info("Error purchasing ticket: " + e.getMessage());
//        }
//    }
    private void processTicketPurchase(int numberOfTicketsToBuy) {
        try {
            System.out.println("Available tickets count: " + ticketPool.getAvailableTicketCount());
            List<Ticket> availableTickets = ticketPool.getTickets().stream()
                    .filter(ticket -> !ticket.isSold())
                    .limit(numberOfTicketsToBuy)
                    .collect(Collectors.toList());

            if (availableTickets.size() == numberOfTicketsToBuy) {
                availableTickets.forEach(ticket -> {
                    ticket.setSold(true);
                    ticket.setCustomerId(customerId);
                });
                ticketPool.removeTickets(availableTickets, customerId);
                System.out.println("Tickets Sold, Ticket IDs: " + availableTickets.stream().map(Ticket::getId).collect(Collectors.toList()));
                System.out.println("New available tickets count: " + ticketPool.getAvailableTicketCount());
                eventRepository.save(event);
                // Update the customer record
                updateCustomerWithTickets(availableTickets);
                LOGGER.info(customerId + " purchased " + numberOfTicketsToBuy + " tickets for event: " + event.getName());
            } else {
                LOGGER.info("Not enough tickets available for event: " + event.getName());
            }
        } catch (RuntimeException e) {
            LOGGER.info("Error purchasing tickets: " + e.getMessage());
        }
    }

    private void updateCustomerWithTickets(List<Ticket> purchasedTickets) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        // Add purchased tickets to the customer's existing tickets
        List<Ticket> currentTickets = new ArrayList<>(Arrays.asList(customer.getTickets()));
        currentTickets.addAll(purchasedTickets);

        // Update the customer tickets and save the customer
        customer.setTickets(currentTickets.toArray(new Ticket[0]));
        customerRepository.save(customer);
    }
}