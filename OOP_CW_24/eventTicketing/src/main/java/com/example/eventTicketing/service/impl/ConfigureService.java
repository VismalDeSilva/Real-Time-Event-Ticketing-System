package com.example.eventTicketing.service.impl;

import com.example.eventTicketing.config.SystemConfiguration;
import com.example.eventTicketing.dto.ConfigDTO;
import org.springframework.stereotype.Service;

@Service
public class ConfigureService {
    private final SystemConfiguration systemConfiguration;

    public ConfigureService(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    // Method to set configuration
    public SystemConfiguration configure(ConfigDTO configDTO) {
        // Validate input
        validateConfiguration(configDTO);

        // Update configuration fields
        systemConfiguration.setTotalTickets(configDTO.getTotalTickets());
        systemConfiguration.setTicketReleaseRate(configDTO.getTicketReleaseRate());
        systemConfiguration.setCustomerRetrievalRate(configDTO.getCustomerRetrievalRate());
        systemConfiguration.setMaxTicketCapacity(configDTO.getMaxTicketCapacity());

        return systemConfiguration;
    }

    // Method to get current configuration
    public SystemConfiguration getCurrentConfiguration() {
        if (systemConfiguration == null) {
            throw new IllegalStateException("System not configured yet");
        }
        return systemConfiguration;
    }

    // Validation method
    private void validateConfiguration(ConfigDTO configDTO) {
        if (configDTO.getTotalTickets() <= 0) {
            throw new IllegalArgumentException("Total tickets must be positive");
        }
        if (configDTO.getTicketReleaseRate() <= 0) {
            throw new IllegalArgumentException("Ticket release rate must be positive");
        }
        if (configDTO.getCustomerRetrievalRate() <= 0) {
            throw new IllegalArgumentException("Customer retrieval rate must be positive");
        }
        if (configDTO.getMaxTicketCapacity() <= 0) {
            throw new IllegalArgumentException("Maximum ticket capacity must be positive");
        }
    }
}