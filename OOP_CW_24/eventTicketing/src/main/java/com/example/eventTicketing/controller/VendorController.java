package com.example.eventTicketing.controller;

import com.example.eventTicketing.dto.VendorDTO;
import com.example.eventTicketing.service.impl.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    //create a new vendor
    @PostMapping
    public ResponseEntity<VendorDTO> saveVendor(@RequestBody VendorDTO vendorDTO) {
        VendorDTO savedVendor = vendorService.saveVendor(vendorDTO);
        return ResponseEntity.ok(savedVendor);
    }

    //get a vendor by id
    @GetMapping("/id/{id}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable String id) {
        VendorDTO vendorDTO = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendorDTO);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<VendorDTO> getVendorByEmail(@PathVariable String email) {
        VendorDTO vendorDTO = vendorService.getVendorByEmail(email);
        return ResponseEntity.ok(vendorDTO);
    }

    //get all vendors
    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<VendorDTO> vendorDTOList = vendorService.getAllVendors();
        return ResponseEntity.ok(vendorDTOList);
    }
}
