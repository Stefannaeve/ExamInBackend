package com.example.examinbackend.MachineTests;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Part;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.AddressRepository;
import com.example.examinbackend.repository.MachineRepository;
import com.example.examinbackend.service.AddressService;
import com.example.examinbackend.service.MachineService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MachineEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MachineService machineService;

    @Autowired
    private MachineRepository machineRepository;

    @BeforeEach
    public void setUp() {
        // Parts for CPU Subassembly
        Part cpu = new Part("CPU", 1000L);
        Part cpuCooler = new Part("CPU Cooler", 600L);
        Part thermalPaste = new Part("Thermal Paste", 50L);
        List<Part> cpuParts = Arrays.asList(cpu, cpuCooler, thermalPaste);

        Subassembly cpuSubassembly = new Subassembly("CPU", cpuParts);


        List<Subassembly> subassemblies = Arrays.asList(cpuSubassembly);
        Machine machine = new Machine("Windows Desktop", subassemblies);
        machineRepository.save(machine);
    }

    @Test
    @Transactional
    public void shouldGetMachineById() throws Exception {
        Machine machine = machineService.getMachineById(1L).get();
        System.out.println(machine);
    }
}
