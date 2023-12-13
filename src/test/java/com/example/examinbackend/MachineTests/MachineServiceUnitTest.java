package com.example.examinbackend.MachineTests;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.MachineRepository;
import com.example.examinbackend.service.MachineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class MachineServiceUnitTest {
    @MockBean
    private MachineRepository machineRepository;
    @Autowired
    private MachineService machineService;

    @Test
    void shouldGetMachineById() {
        List<Subassembly> subassemblies =  List.of(new Subassembly(), new Subassembly());
        Machine machineOne = new Machine("Machine 1", subassemblies);
        Machine machineTwo = new Machine("Machine 2", subassemblies);
        when(machineRepository.findById(2L)).thenReturn(java.util.Optional.of(machineTwo));
        var machineById = machineService.getMachineById(2L);
        assert machineById.get().getMachineName().equals("Machine 2");
    }
    @Test
    void shouldCreateANewMachine() {
        List<Subassembly> subassemblies =  List.of(new Subassembly(), new Subassembly());
        Machine machine = new Machine("Machine 1", subassemblies);
        when(machineRepository.save(machine)).thenReturn(machine);
        var createdMachine = machineService.createMachine(machine);
        assert createdMachine.getMachineName().equals("Machine 1");
    }
    @Test
    void shouldGetAllMachines() {
        List<Machine> listOfMachines = List.of(new Machine(), new Machine(), new Machine());
        when(machineRepository.findAll()).thenReturn(listOfMachines);
        var machines = machineService.getAllMachines();
        assert machines.size() == 3;
    }
    @Test
    void shouldDeleteANewMachineById() {
        List<Subassembly> subassemblies =  List.of(new Subassembly(), new Subassembly());
        Machine machine = new Machine("Machine 1", subassemblies);
        machineService.deleteMachine(1L);
        assert machineService.getAllMachines().size() == 0;
    }
    @Test
    void shouldUpdateMachineName(){
        List<Subassembly> subassemblies =  List.of(new Subassembly(), new Subassembly());
        Machine machine = new Machine("Machine 1", subassemblies);
        when(machineRepository.save(machine)).thenReturn(machine);
        when(machineRepository.findById(1L)).thenReturn(java.util.Optional.of(machine));
        var machineById = machineService.getMachineById(1L);
        machineById.get().setMachineName("Machine 2");
        assert machineService.getMachineById(1L).get().getMachineName().equals("Machine 2");
    }

}
