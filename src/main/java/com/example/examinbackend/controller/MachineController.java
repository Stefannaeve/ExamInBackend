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
        Optional<Machine> optionalMachine;
        optionalMachine = machineService.getMachineById(id);
        return optionalMachine.map(machine -> ResponseEntity.status(HttpStatus.OK).body(machine)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }
    @GetMapping("/all")
    public List<Machine> getAllMachines() {
        return machineService.getAllMachines();
    }

    @GetMapping("/all/{pageNumber}/{pageSize}")
    public List<Machine> getAllMachinesPageable(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return machineService.getAllMachinesPageable(pageNumber, pageSize);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Machine createMachine(@RequestBody Machine machine) {
        return machineService.createMachine(machine);
    }
    @PutMapping(value = "/update/{id}/name", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Machine> updateMachineName(@PathVariable Long id, @RequestBody Machine machine) {
        Optional<Machine> optionalMachine = machineService.updateMachineName(id, machine);
        return optionalMachine.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
    @DeleteMapping(value ="/delete/{id}", produces = "application/json")
    public ResponseEntity<Machine> deleteMachine(@PathVariable Long id) {
        Optional<Machine> optionalMachine;
        optionalMachine = machineService.deleteMachine(id);
        return optionalMachine.map(machine -> ResponseEntity.status(HttpStatus.OK).body(machine)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
