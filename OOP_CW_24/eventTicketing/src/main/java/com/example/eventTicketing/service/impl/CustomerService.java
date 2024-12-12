package com.example.eventTicketing.service.impl;

import com.example.eventTicketing.Repository.CustomerRepository;
import com.example.eventTicketing.classes.Ticket;
import com.example.eventTicketing.dto.CustomerDTO;
import com.example.eventTicketing.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setPassword(customerDTO.getPassword());
        customer.setTickets(new Ticket[]{});

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO savedCustomerDTO = new CustomerDTO();
        savedCustomerDTO.setId(savedCustomer.getId());
        savedCustomerDTO.setName(savedCustomer.getName());
        savedCustomerDTO.setEmail(savedCustomer.getEmail());
        savedCustomerDTO.setPhoneNumber(savedCustomer.getPhoneNumber());
        savedCustomerDTO.setPassword(savedCustomer.getPassword());
        savedCustomerDTO.setTickets(savedCustomer.getTickets());

        return savedCustomerDTO;
    }

    public CustomerDTO getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setTickets(customer.getTickets());

        return customerDTO;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> {
                    CustomerDTO customerDTO = new CustomerDTO();
                    customerDTO.setId(customer.getId());
                    customerDTO.setName(customer.getName());
                    customerDTO.setEmail(customer.getEmail());
                    customerDTO.setPhoneNumber(customer.getPhoneNumber());
                    customerDTO.setPassword(customer.getPassword());
                    customerDTO.setTickets(customer.getTickets());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setTickets(customer.getTickets());

        return customerDTO;
    }
}
