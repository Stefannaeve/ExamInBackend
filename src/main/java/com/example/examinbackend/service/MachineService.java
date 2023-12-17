package com.example.examinbackend.service;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {

    private final MachineRepository machineRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }
    public Optional<Machine> getMachineById(Long id) {
        Optional<Machine> optionalMachine = machineRepository.findById(id);
        return optionalMachine;
    }
    public Machine createMachine(Machine machine) {
        long subassemblyPrice = 0;
        for (Subassembly subassembly : machine.getSubassemblies()) {
            subassemblyPrice += subassembly.getSubassemblyPrice();
        }
        machine.setMachinePrice(subassemblyPrice);
        return machineRepository.save(machine);
    }
    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }

    public List<Machine> getAllMachinesPageable(int pageNumber, int pageSize) {
        return machineRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
    }
    public Optional<Machine> deleteMachine(Long id) {
        Optional<Machine> optionalMachine = getMachineById(id);
        if (optionalMachine.isEmpty()) {
            return Optional.empty();
        }
        machineRepository.deleteById(id);
        return optionalMachine;
    }
    public Optional<Machine> updateMachineName(Long id, Machine machine) {
        Optional<Machine> optionalMachine = getMachineById(id);
        if (optionalMachine.isEmpty()) {
            return Optional.empty();
        }
        optionalMachine.get().setMachineName(machine.getMachineName());
        return Optional.of(machineRepository.save(optionalMachine.get()));
    }
}
