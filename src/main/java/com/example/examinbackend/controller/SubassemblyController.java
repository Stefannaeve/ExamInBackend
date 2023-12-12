package com.example.examinbackend.controller;

import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.service.SubassemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subassembly")
public class SubassemblyController {
    private final SubassemblyService subassemblyService;
    @Autowired
    public SubassemblyController(SubassemblyService subassemblyService) {
        this.subassemblyService = subassemblyService;
    }
    @GetMapping("/{id}")
    public Subassembly getSubassembly(@PathVariable Long id) {
        return subassemblyService.getSubassemblyById(id);
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
    public Subassembly updateSubassemblyName(@PathVariable Long id, @RequestBody Subassembly subassembly) {
        return subassemblyService.updateSubassemblyName(id, subassembly);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteSubassembly(@PathVariable Long id) {
        subassemblyService.deleteSubassembly(id);
    }
}
