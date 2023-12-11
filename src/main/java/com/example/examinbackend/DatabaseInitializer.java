package com.example.examinbackend;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final PartService partService;

    @Autowired
    public DatabaseInitializer(PartService partService) {
        this.partService = partService;
    }

    public void run(String... args) {
        partService.createPart(new Part("Cog"));
        partService.createPart(new Part("Screw"));
        partService.createPart(new Part("Bolt"));
        partService.createPart(new Part("Spring"));
        partService.createPart(new Part("Wire"));
    }
}
