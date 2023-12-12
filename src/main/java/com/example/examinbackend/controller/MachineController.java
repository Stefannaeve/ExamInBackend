package com.example.examinbackend.controller;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.service.MachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/machine")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Machine> getMachine(@PathVariable Long id) {
        Optional<Machine> optionalMachine = machineService.getMachineById(id);
        if (optionalMachine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalMachine.get());
        }

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
    public ResponseEntity<Machine> updateMachineName(@PathVariable Long id, @RequestBody Machine machine) {
        Optional<Machine> optionalMachine = machineService.updateMachineName(id, machine);
        if (optionalMachine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalMachine.get());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Machine> deleteMachine(@PathVariable Long id) {
        Optional<Machine> optionalMachine = machineService.deleteMachine(id);
        if (optionalMachine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalMachine.get());
        }
    }
}
