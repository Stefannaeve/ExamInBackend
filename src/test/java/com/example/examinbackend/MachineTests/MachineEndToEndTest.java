package com.example.examinbackend.MachineTests;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Part;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.AddressRepository;
import com.example.examinbackend.repository.MachineRepository;
import com.example.examinbackend.repository.PartRepository;
import com.example.examinbackend.repository.SubassemblyRepository;
import com.example.examinbackend.service.AddressService;
import com.example.examinbackend.service.MachineService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MachineEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MachineService machineService;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private SubassemblyRepository subassemblyRepository;

    @Autowired
    private PartRepository partRepository;

    private Long machineIds;

    @BeforeEach
    public void setUp() {
        Part cpu = partRepository.save(new Part("CPU", 1000L));
        Part cpuCooler = partRepository.save(new Part("CPU Cooler", 100L));
        Part thermalPaste = partRepository.save(new Part("Thermal Paste", 10L));
        List<Part> cpuParts = Arrays.asList(cpu, cpuCooler, thermalPaste);

        Subassembly cpuSubassembly = subassemblyRepository.save(new Subassembly("CPU", cpuParts));
        List<Subassembly> subassemblies = Arrays.asList(cpuSubassembly);

        machineIds = machineRepository.save(new Machine("Windows Desktop", subassemblies)).getId();
    }

    @Test
    @Transactional
    public void shouldGetMachineById() throws Exception {
        mockMvc.perform(get("/api/machine/" + machineIds))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineName").value("Windows Desktop"))
                .andExpect(jsonPath("$.subassemblies[0].subassemblyName").value("CPU"))
                .andExpect(jsonPath("$.subassemblies[0].parts[0].partName").value("CPU"))
                .andExpect(jsonPath("$.subassemblies[0].parts[0].partPrice").value(1000L))
                .andExpect(jsonPath("$.subassemblies[0].parts[1].partName").value("CPU Cooler"))
                .andExpect(jsonPath("$.subassemblies[0].parts[1].partPrice").value(100L))
                .andExpect(jsonPath("$.subassemblies[0].parts[2].partName").value("Thermal Paste"))
                .andExpect(jsonPath("$.subassemblies[0].parts[2].partPrice").value(10L));
    }

    @Test
    @Transactional
    public void shouldGetAllMachines() throws Exception {
        Part cpu = partRepository.save(new Part("CPU2", 10002L));
        Part cpuCooler = partRepository.save(new Part("CPU Cooler2", 1002L));
        Part thermalPaste = partRepository.save(new Part("Thermal Paste2", 102L));
        List<Part> cpuParts = Arrays.asList(cpu, cpuCooler, thermalPaste);

        Subassembly cpuSubassembly = subassemblyRepository.save(new Subassembly("CPU2", cpuParts));
        List<Subassembly> subassemblies = Arrays.asList(cpuSubassembly);

        machineIds = machineRepository.save(new Machine("Windows Desktop2", subassemblies)).getId();


        mockMvc.perform(get("/api/machine/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].machineName").value("Windows Desktop"))
                .andExpect(jsonPath("$[0].subassemblies[0].subassemblyName").value("CPU"))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[0].partName").value("CPU"))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[0].partPrice").value(1000L))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[1].partName").value("CPU Cooler"))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[1].partPrice").value(100L))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[2].partName").value("Thermal Paste"))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[2].partPrice").value(10L))
                .andExpect(jsonPath("$[1].machineName").value("Windows Desktop2"))
                .andExpect(jsonPath("$[1].subassemblies[0].subassemblyName").value("CPU2"))
                .andExpect(jsonPath("$[1].subassemblies[0].parts[0].partName").value("CPU2"))
                .andExpect(jsonPath("$[1].subassemblies[0].parts[0].partPrice").value(10002L))
                .andExpect(jsonPath("$[1].subassemblies[0].parts[1].partName").value("CPU Cooler2"))
                .andExpect(jsonPath("$[1].subassemblies[0].parts[1].partPrice").value(1002L))
                .andExpect(jsonPath("$[1].subassemblies[0].parts[2].partName").value("Thermal Paste2"))
                .andExpect(jsonPath("$[1].subassemblies[0].parts[2].partPrice").value(102L));
    }

    @Test
    @Transactional
    public void shouldUpdateMachineName() throws Exception {
        mockMvc.perform(put("/api/machine/" + machineIds + "/update/name")
                .contentType("application/json")
                .content("{\"machineName\":\"MacBook\"}")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineName").value("MacBook"));
    }

    @Test
    @Transactional
    public void shouldDeleteMachine() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/machine/delete/" + machineIds))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineName").value("Windows Desktop"))
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        JsonNode machineJson = new ObjectMapper().readTree(jsonString);
        Long id = machineJson.path("id").asLong();
        assertTrue(machineRepository.findById(id).isEmpty());
    }

}
