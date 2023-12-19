package com.example.examinbackend.controller;

import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.service.SubassemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subassembly")
public class SubassemblyController {
    private final SubassemblyService subassemblyService;
    @Autowired
    public SubassemblyController(SubassemblyService subassemblyService) {
        this.subassemblyService = subassemblyService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Subassembly> getSubassembly(@PathVariable Long id) {
        Optional<Subassembly> optionalSubassembly;
        optionalSubassembly = subassemblyService.getSubassemblyById(id);
        return optionalSubassembly.map(subassembly -> ResponseEntity.status(HttpStatus.OK).body(subassembly)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
    @GetMapping("/all")
    public List<Subassembly> getAllSubassemblies() {
        return subassemblyService.getAllSubassemblies();
    }

    @GetMapping("/all/{pageNumber}/{pageSize}")
    public List<Subassembly> getAllSubassembliesPageable(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return subassemblyService.getAllSubassembliesPageable(pageNumber, pageSize);
    }
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Subassembly createSubassembly(@RequestBody Subassembly subassembly) {
        return subassemblyService.createSubassembly(subassembly);
    }
    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Subassembly> updateSubassemblyName(@PathVariable Long id, @RequestBody Subassembly subassembly) {
        Optional<Subassembly> optionalSubassembly;
        optionalSubassembly = subassemblyService.updateSubassemblyName(id, subassembly);
        return optionalSubassembly.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
    @DeleteMapping(value ="/delete/{id}", produces = "application/json")
    public ResponseEntity<Subassembly> deleteSubassembly(@PathVariable Long id) {
        Optional<Subassembly> optionalSubassembly;
        optionalSubassembly = subassemblyService.deleteSubassembly(id);
        return optionalSubassembly.map(subassembly -> ResponseEntity.status(HttpStatus.OK).body(subassembly)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
