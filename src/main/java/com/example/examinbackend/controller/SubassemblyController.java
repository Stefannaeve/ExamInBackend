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
        Optional<Subassembly> optionalSubassembly = subassemblyService.getSubassemblyById(id);
        if (optionalSubassembly.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalSubassembly.get());
        }
    }
    @GetMapping("/all")
    public List<Subassembly> getAllSubassemblies() {
        return subassemblyService.getAllSubassemblies();
    }
    @PostMapping("/add")
    public void createSubassembly(@RequestBody Subassembly subassembly) {
        subassemblyService.createSubassembly(subassembly);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Subassembly> updateSubassemblyName(@PathVariable Long id, @RequestBody Subassembly subassembly) {
        Optional<Subassembly> optionalSubassembly = subassemblyService.updateSubassemblyName(id, subassembly);
        if (optionalSubassembly.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalSubassembly.get());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Subassembly> deleteSubassembly(@PathVariable Long id) {
        Optional<Subassembly> optionalSubassembly = subassemblyService.deleteSubassembly(id);
        if (optionalSubassembly.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalSubassembly.get());
        }
    }
}
