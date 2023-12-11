package com.example.examinbackend.MachineTests;

import com.example.examinbackend.model.Machine;
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
        Machine machineOne = new Machine("Machine 1");
        Machine machineTwo = new Machine("Machine 2");
        when(machineRepository.findById(2L)).thenReturn(java.util.Optional.of(machineTwo));
        var machineById = machineService.getMachineById(2L);
        assert machineById.getMachineName().equals("Machine 2");
    }
    @Test
    void shouldCreateANewMachine() {
        Machine machine = new Machine("Machine 1");
        when(machineRepository.save(machine)).thenReturn(machine);
        var createdMachine = machineService.createMachine(machine);
        assert createdMachine.getMachineName().equals("Machine 1");
    }
    @Test
    void shouldDeleteANewMachineById() {
        Machine machine = new Machine("Machine 1");
        machineService.deleteMachine(1L);
        assert machineService.getAllMachines().size() == 0;
    }
    @Test
    void shouldGetAllMachines() {
        List<Machine> listOfMachines = List.of(new Machine(), new Machine(), new Machine());
        when(machineRepository.findAll()).thenReturn(listOfMachines);
        var machines = machineService.getAllMachines();
        assert machines.size() == 3;
    }
}
