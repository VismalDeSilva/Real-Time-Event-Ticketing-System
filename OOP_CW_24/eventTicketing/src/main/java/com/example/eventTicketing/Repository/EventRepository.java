package com.example.eventTicketing.Repository;

import com.example.eventTicketing.classes.Ticket;
import com.example.eventTicketing.classes.TicketPool;
import com.example.eventTicketing.model.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
//import org.springframework.data.mongodb.repository.Modifying;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    Event findByName(String name);

    // Find events by vendor ID
    List<Event> findByVendorId(Long vendorId);

    // Update ticket pool by adding a ticket
//    @Update(
//            update = "{ '$push': { 'ticketPool.tickets': ?1 }, '$inc': { 'ticketPool.availableTickets': 1 } }",
//            query = "{ '_id': ?0, 'ticketPool.availableTickets': { '$lt': 'ticketPool.maxCapacity' } }"
//    )
//    @Modifying
//    @Query("{ '_id': ?0, 'ticketPool.availableTickets': { '$lt': 'ticketPool.maxCapacity' } }")
//    @Update("{ '$push': { 'ticketPool.tickets': ?1 }, '$inc': { 'ticketPool.availableTickets': 1 } }")
//    int addTicketToPool(String eventId, Ticket ticket);

//    @Modifying
//    @Query("{ '_id': ?0, 'ticketPool.availableTickets': { '$lt': ?1 } }")
//    @Update("{ '$push': { 'ticketPool.tickets': ?2 }, '$inc': { 'ticketPool.availableTickets': 1 } }")
//    int addTicketToPool(String eventId, int maxCapacity, Ticket ticket);

//    @Query("{ '_id': ?0, 'ticketPool.tickets': { '$size': { '$lt': ?1 } } }")
//    @Update("{ '$push': { 'ticketPool.tickets': ?2 } }")
//    int addTicketToPool(String eventId, int maxCapacity, Ticket ticket);

//    @Query("{ '_id': ?0, 'ticketPool.tickets': { '$size': { '$lt': ?1 } } }")
//    @Update("{ '$push': { 'ticketPool.tickets': ?2 } }")
//    int addTicketToPool(String eventId, int maxCapacity, Ticket ticket);

//    @Query("{ '_id': ?0, 'ticketPool.tickets': { '$size': { '$lt': ?1 } } }")
//    @Update("{ '$push': { 'ticketPool.tickets': ?2 } }")
//    int addTicketToPool(String eventId, int maxCapacity, Ticket ticket);

    @Query("{ '_id': ?0, '$expr': { '$lt': [{ '$size': '$ticketPool.tickets' }, ?1] } }")
    @Update("{ '$push': { 'ticketPool.tickets': ?2 },'$inc': { 'ticketPool.availableTickets': 1 } }")
    int addTicketToPool(String eventId, int maxCapacity, Ticket ticket);


//    @Query("{ '_id' : ?0 }")
//    void addTicketToPool(@Param("id") String id, @Param("ticket") Ticket ticket);

//    // Find events with available tickets
//    @Query("SELECT DISTINCT e FROM Event e JOIN e.tickets t WHERE t.isAvailable = true")
//    List<Event> findEventsWithAvailableTickets();
}
