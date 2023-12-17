package com.example.examinbackend.controller;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/part")
public class PartController {

    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable Long id) {
        Optional<Part> optionalPart = partService.getPartById(id);
        if (optionalPart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalPart.get());
        }
    }

    @GetMapping("/all")
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    @GetMapping("/all/{pageNumber}/{pageSize}")
    public List<Part> getAllPartsPageable(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return partService.getAllPartsPageable(pageNumber, pageSize);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Part createPart(@RequestBody Part part) {
        return partService.createPart(part);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Part> updatePartName(@PathVariable Long id, @RequestBody Part part) {
        Optional<Part> optionalPart = partService.updatePartName(id, part);
        if (optionalPart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalPart.get());
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<Part> deletePart(@PathVariable Long id) {
        Optional<Part> optionalPart = partService.deletePart(id);
        if (optionalPart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalPart.get());
        }
    }
}
