package com.example.eventTicketing.service.impl;

import com.example.eventTicketing.Repository.VendorRepository;
import com.example.eventTicketing.classes.Ticket;
import com.example.eventTicketing.dto.EventDTO;
import com.example.eventTicketing.dto.VendorDTO;
import com.example.eventTicketing.model.Event;
import com.example.eventTicketing.model.Vendor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public VendorDTO saveVendor(VendorDTO vendorDTO) {
        Vendor vendor = new Vendor();
        vendor.setName(vendorDTO.getName());
        vendor.setContactEmail(vendorDTO.getContactEmail());
        vendor.setContactPhone(vendorDTO.getContactPhone());
        vendor.setPassword(vendorDTO.getPassword());
        vendor.setEvents(new ArrayList<>());


        Vendor savedVendor = vendorRepository.save(vendor);
        return new VendorDTO(savedVendor.getId(), savedVendor.getName(), savedVendor.getContactEmail(), savedVendor.getContactPhone(), savedVendor.getPassword(), vendorDTO.getEvents());
    }

    public VendorDTO getVendorById(String id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));
        return new VendorDTO(vendor.getId(), vendor.getName(), vendor.getContactEmail(), vendor.getContactPhone(), vendor.getPassword(), vendor.getEvents().stream()
                .map(event -> new EventDTO(event)) // assuming you have a constructor in EventDTO that takes an Event
                .collect(Collectors.toList()));
    }

    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(vendor -> new VendorDTO(vendor.getId(), vendor.getName(), vendor.getContactEmail(), vendor.getContactPhone(), vendor.getPassword(),
                        vendor.getEvents().stream()
                                .map(event -> new EventDTO(event)) // assuming you have a constructor in EventDTO that takes an Event
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }


    public VendorDTO getVendorByEmail(String email) {
        Vendor vendor = vendorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Vendor not found with email: " + email));

        if (vendor == null) {
            throw new RuntimeException("Vendor not found with email: " + email);
        }
        return new VendorDTO(vendor.getId(), vendor.getName(), vendor.getContactEmail(), vendor.getContactPhone(), vendor.getPassword(), vendor.getEvents().stream()
                .map(event -> new EventDTO(event)) // assuming you have a constructor in EventDTO that takes an Event
                .collect(Collectors.toList()));
    }
}
