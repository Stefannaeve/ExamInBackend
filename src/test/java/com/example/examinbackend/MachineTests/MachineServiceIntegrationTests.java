package com.example.examinbackend.MachineTests;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.MachineRepository;
import com.example.examinbackend.service.MachineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class MachineServiceIntegrationTests {
    @Autowired
    private MachineService machineService;

    @Autowired
    private MachineRepository machineRepository;

    @Test
    @Transactional
    void shouldGetMachineById() {
        List<Subassembly> subassemblies = List.of(new Subassembly(), new Subassembly());
        Machine machineOne = new Machine("Machine 1", subassemblies);
        Machine machineTwo = new Machine("Machine 2", subassemblies);
        Machine savedMachine = machineRepository.save(machineTwo);
        var foundMachine = machineService.getMachineById(savedMachine.getId());
        assert foundMachine.get().getMachineName().equals("Machine 2");
    }

    @Test
    @Transactional
    void shouldCreateANewMachine() {
        List<Subassembly> subassemblies = List.of(new Subassembly("Subassembly1", 1L), new Subassembly("Subassembly2", 2L));
        Machine machine = new Machine("Machine 1", subassemblies);
        Machine savedMachine = machineRepository.save(machine);
        assert savedMachine.getMachineName().equals("Machine 1");
    }

    @Test
    @Transactional
    void shouldGetAllMachines() {
        List<Machine> listOfNewMachines = List.of(new Machine(), new Machine(), new Machine());
        machineRepository.saveAll(listOfNewMachines);
        var machines = machineService.getAllMachines();
        assert machines.size() == 3;
    }

    @Test
    @Transactional
    void shouldDeleteANewMachineById() {
        List<Subassembly> subassemblies = List.of(new Subassembly(), new Subassembly());
        Machine machine = new Machine("Machine 1", subassemblies);
        Machine savedMachine = machineRepository.save(machine);
        machineService.deleteMachine(savedMachine.getId());
        assert machineService.getAllMachines().size() == 0;
    }
    @Test
    @Transactional
    void shouldUpdateMachineName() {
        List<Subassembly> subassemblies = List.of(new Subassembly(), new Subassembly());
        Machine machine = new Machine("Machine 1", subassemblies);
        Machine savedMachine = machineRepository.save(machine);
        var machineById = machineService.getMachineById(savedMachine.getId());
        machineById.get().setMachineName("New machine");
        assert machineById.get().getMachineName().equals("New machine");
    }
}
