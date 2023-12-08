package com.example.examinbackend.controller;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.service.MachineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machine")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping("/{id}")
    public Machine getMachine(@PathVariable Long id) {
        return machineService.getMachineById(id);
    }
    @GetMapping("/all")
    public List<Machine> getAllMachines() {
        return machineService.getAllMachines();
    }
    @PostMapping("/add")
    public Machine createMachine(@RequestBody Machine machine) {
        return machineService.createMachine(machine);
    }
    @PutMapping("/update/{id}")
    public Machine updateMachineName(@PathVariable Long id, @RequestBody Machine machine) {
        return machineService.updateMachineName(id, machine);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteMachine(@PathVariable Long id) {
        machineService.deleteMachine(id);
    }
}