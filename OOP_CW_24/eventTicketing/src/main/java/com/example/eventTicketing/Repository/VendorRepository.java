package com.example.eventTicketing.Repository;

import com.example.eventTicketing.model.Event;
import com.example.eventTicketing.model.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends MongoRepository<Vendor, String> {
//    Vendor findByEmail(String email);
       long count();

    @Query("{contactEmail: ?0}")
    public Optional<Vendor> findByEmail(String email);
//    Vendor findByEmail(String email);

//       List<Vendor> findByEvent(Event event);
}
