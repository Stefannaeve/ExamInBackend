package com.example.examinbackend.controller;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/part")
public class PartController {

    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/{id}")
    public Part getPartById(@PathVariable Long id) {
        return partService.getPartById(id);
    }

    @GetMapping("/all")
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    @PostMapping("/add")
    public Part createPart(@RequestBody Part part) {
        return partService.createPart(part);
    }
}
