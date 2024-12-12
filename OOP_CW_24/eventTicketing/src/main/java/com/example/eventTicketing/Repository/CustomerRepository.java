package com.example.eventTicketing.Repository;

import com.example.eventTicketing.model.Customer;
//import org.springframework.data.jpa.repository.JpaRepository;
import com.example.eventTicketing.model.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
//    Customer findByEmail(String email);

    long count();
    @Query("{email: ?0}")
    public Optional<Customer> findByEmail(String email);

}
