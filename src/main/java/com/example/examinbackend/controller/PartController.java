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
        Optional<Part> optionalPart;
        optionalPart = partService.getPartById(id);
        return optionalPart.map(part -> ResponseEntity.status(HttpStatus.OK).body(part)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
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
        Optional<Part> optionalPart;
        optionalPart = partService.updatePartName(id, part);
        return optionalPart.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<Part> deletePart(@PathVariable Long id) {
        Optional<Part> optionalPart;
        optionalPart = partService.deletePart(id);
        return optionalPart.map(part -> ResponseEntity.status(HttpStatus.OK).body(part)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
