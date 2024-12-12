//package com.example.eventTicketing.classes;
//
//
//import com.example.eventTicketing.config.SystemConfiguration;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class TicketPool {
//    private final ConcurrentLinkedQueue<Ticket> tickets;
//    private final AtomicInteger availableTickets;
//    private final SystemConfiguration systemConfiguration;
//    private final int maxCapacity ;
//
////    public TicketPool(ConcurrentLinkedQueue<Ticket> tickets, AtomicInteger availableTickets, SystemConfiguration systemConfiguration, int maxCapacity) {
////        this.tickets = tickets;
////        this.availableTickets = availableTickets;
////        this.systemConfiguration = systemConfiguration;
////        this.maxCapacity = maxCapacity;
////    }
//
////    public TicketPool() {
////        this.tickets = new ConcurrentLinkedQueue<>();
////        this.availableTickets = new AtomicInteger(0);
////        this.systemConfiguration = systemConfiguration.;  // You might need to handle it if null is not acceptable
////        this.maxCapacity = 0;  // Set a default maxCapacity, or handle it accordingly
////    }
//
//
//    public TicketPool(SystemConfiguration systemConfiguration) {
//        this.systemConfiguration = systemConfiguration;
//        this.tickets = new ConcurrentLinkedQueue<>();
//        this.availableTickets = new AtomicInteger(0);
//        this.maxCapacity = systemConfiguration.getMaxTicketCapacity();
//    }
//
//
//    public synchronized boolean addTickets(Ticket ticket) {
//        if (availableTickets.get() >= maxCapacity) {
////            System.out.println("Max ticket capacity: " + maxCapacity + ", Available tickets: " + availableTickets.get());
//            System.out.println("Ticket pool is full.");
//            return false;  // Pool is full
//        }
//
//        boolean added = tickets.offer(ticket);
//        if (added) {
//            availableTickets.incrementAndGet();
//        }
//        return added;
//    }
//
//    public synchronized String removeTicket() {
////        Ticket ticket = tickets.poll();
////        if (ticket != null) {
//            availableTickets.decrementAndGet();
////        }
//        return "Ticket Sold";
//    }
//
//    public int getAvailableTicketCount() {
//        return availableTickets.get();
//    }
//
//    public int getMaxCapacity() {
//        return maxCapacity;
//    }
//
//
//    public ConcurrentLinkedQueue<Ticket> getTickets() {
//        return tickets;
//    }
//
//    @Override
//    public String toString() {
//        return "TicketPool{" +
//                "systemConfiguration=" + systemConfiguration +
//                ", tickets=" + tickets +
//                ", availableTickets=" + availableTickets +
//                ", maxCapacity=" + maxCapacity +
//                '}';
//    }
//}

package com.example.eventTicketing.classes;

import com.example.eventTicketing.config.SystemConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TicketPool {
//    private List<Ticket> tickets;
    private ConcurrentLinkedQueue<Ticket> tickets;
    private int availableTickets;
    private SystemConfiguration systemConfiguration;
    private int maxCapacity ;

    public TicketPool(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
//        this.tickets = new ArrayList<>();
        this.tickets = new ConcurrentLinkedQueue<>();
        this.availableTickets = 0;
        this.maxCapacity = systemConfiguration.getMaxTicketCapacity();
    }

    public synchronized boolean addTickets(Ticket ticket) {
        if (availableTickets >= maxCapacity) {
            System.out.println("Ticket pool is full.");
            return false;  // Pool is full
        }

//        boolean added = tickets.add(ticket);
        boolean added = tickets.offer(ticket);
        if (added) {
            availableTickets++;
        }
        return added;
    }

//    public synchronized String removeTicket(Ticket ticket, String customerId) {
//        if (availableTickets > 0) {
//            Ticket ticketToRemove = null;
//            for (Ticket t : tickets) {
//                if (t.getId().equals(ticket.getId())) {
//                    ticketToRemove = t;
//                    break;
//                }
//            }
//            if (ticketToRemove != null) {
//                ticketToRemove.setCustomerId(customerId);
//                ticketToRemove.setSold(true);
//                availableTickets--;
//                return "Ticket Sold";
//            }
//        }
//        return "No tickets available";
//    }
    public synchronized String removeTickets(List<Ticket> ticketsToRemove, String customerId) {
        if (ticketsToRemove.size() <= availableTickets) {
            for (Ticket ticket : ticketsToRemove) {
                Ticket ticketToRemove = null;
                for (Ticket t : tickets) {
                    if (t.getId().equals(ticket.getId())) {
                        ticketToRemove = t;
                        break;
                    }
                }
                if (ticketToRemove != null) {
                    ticketToRemove.setCustomerId(customerId);
                    ticketToRemove.setSold(true);
                    availableTickets--;
                }
            }
            return "Tickets Sold";
        }
        return "Not enough tickets available";
    }

    public int getAvailableTicketCount() {
        return availableTickets;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public ConcurrentLinkedQueue<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "TicketPool{" +
                "systemConfiguration=" + systemConfiguration +
                ", tickets=" + tickets +
                ", availableTickets=" + availableTickets +
                ", maxCapacity=" + maxCapacity +
                '}';
    }


//    public void removeTickets(List<Ticket> availableTickets, String customerId) {
//        for (Ticket t : availableTickets) {
//            if (tickets.remove(t)) {
//                t.setCustomerId(customerId);
//                t.setSold(true);
//                availableTickets--;
//            }
//        }
//    }
}


//package com.example.eventTicketing.classes;
////import com.example.eventTicketing.classes.Ticket;
//import com.example.eventTicketing.config.SystemConfiguration;
//import org.springframework.data.annotation.Transient;
//
//import javax.persistence.PostLoad;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class TicketPool {
//    private List<Ticket> ticketList; // For MongoDB serialization
//    private int availableTicketCount; // For MongoDB serialization
//    private final SystemConfiguration systemConfiguration;
//    private final int maxCapacity;
//
//    @Transient
//    private ConcurrentLinkedQueue<Ticket> tickets; // Transient to avoid MongoDB serialization
//
//    @Transient
//    private AtomicInteger availableTickets; // Transient to avoid MongoDB serialization
//
//    public TicketPool(SystemConfiguration systemConfiguration) {
//        this.systemConfiguration = systemConfiguration;
//        this.ticketList = new ArrayList<>();
//        this.tickets = new ConcurrentLinkedQueue<>();
//        this.availableTickets = new AtomicInteger(0);
//        this.maxCapacity = systemConfiguration.getMaxTicketCapacity();
//    }
//
//    @PostLoad
//    private void postLoad() {
//        // Initialize the transient fields after loading from MongoDB
//        this.tickets = new ConcurrentLinkedQueue<>(ticketList);
//        this.availableTickets = new AtomicInteger(availableTicketCount);
//    }
//
//    public synchronized boolean addTickets(Ticket ticket) {
//        if (availableTickets.get() >= maxCapacity) {
//            System.out.println("Ticket pool is full.");
//            return false;
//        }
//
//        boolean added = tickets.offer(ticket);
//        if (added) {
//            availableTickets.incrementAndGet();
//            ticketList.add(ticket); // Keep ticketList updated
//            availableTicketCount = availableTickets.get(); // Update the serialized value
//        }
//        return added;
//    }
//
//    public synchronized String removeTicket() {
//        Ticket removedTicket = tickets.poll();
//        if (removedTicket != null) {
//            availableTickets.decrementAndGet();
//            ticketList.remove(removedTicket); // Keep ticketList updated
//            availableTicketCount = availableTickets.get(); // Update the serialized value
//            return "Ticket Sold";
//        }
//        return "No tickets available.";
//    }
//
//    public int getAvailableTicketCount() {
//        return availableTickets.get();
//    }
//
//    public int getMaxCapacity() {
//        return maxCapacity;
//    }
//
//    public ConcurrentLinkedQueue<Ticket> getTickets() {
//        return tickets;
//    }
//
//    @Override
//    public String toString() {
//        return "TicketPool{" +
//                "systemConfiguration=" + systemConfiguration +
//                ", tickets=" + tickets +
//                ", availableTickets=" + availableTickets +
//                ", maxCapacity=" + maxCapacity +
//                '}';
//    }
//}

