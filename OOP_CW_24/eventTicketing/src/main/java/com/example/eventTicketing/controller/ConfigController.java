package com.example.eventTicketing.controller;

import com.example.eventTicketing.config.SystemConfiguration;
import com.example.eventTicketing.dto.ConfigDTO;
import com.example.eventTicketing.service.impl.ConfigureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configure")
public class ConfigController {

    private final ConfigureService configureService;

    public ConfigController(ConfigureService configureService) {
        this.configureService = configureService;
    }

    // Endpoint to configure the system
    @PostMapping("/setup")
    public ResponseEntity<ConfigDTO> configureSystem(
            @RequestBody ConfigDTO configDTO
    ) {
        SystemConfiguration configuration = configureService.configure(configDTO);
        ConfigDTO responseDTO = new ConfigDTO(configuration.getTotalTickets(), configuration.getTicketReleaseRate(), configuration.getCustomerRetrievalRate(), configuration.getMaxTicketCapacity());
        return ResponseEntity.ok(responseDTO);
    }

    // Endpoint to get current configuration
    @GetMapping
    public ResponseEntity<SystemConfiguration> getCurrentConfiguration() {
        try {
            SystemConfiguration configuration = configureService.getCurrentConfiguration();
            return ResponseEntity.ok(configuration);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}