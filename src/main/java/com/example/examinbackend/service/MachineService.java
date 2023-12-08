package com.example.examinbackend.service;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {

    private final MachineRepository machineRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }
    public Machine getMachineById(Long id) {
        return machineRepository.findById(id).orElse(null);
    }
    public Machine createMachine(Machine machine) {
        return machineRepository.save(machine);
    }
    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }
    public void deleteMachine(Long id) {
        machineRepository.deleteById(id);
    }
    public Machine updateMachineName(Long id, Machine machine) {
        Machine existingMachine = getMachineById(id);
        existingMachine.setMachineName(machine.getMachineName());
        return machineRepository.save(existingMachine);
    }
}
