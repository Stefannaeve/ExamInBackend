package com.example.examinbackend.MachineTests;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Part;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.MachineRepository;
import com.example.examinbackend.repository.PartRepository;
import com.example.examinbackend.repository.SubassemblyRepository;
import com.example.examinbackend.service.SubassemblyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private MachineRepository machineRepository;

    @Autowired
    private SubassemblyRepository subassemblyRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SubassemblyService subassemblyService;

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

    @AfterEach
    public void tearDown() {
        machineRepository.deleteAll();
        subassemblyRepository.deleteAll();
        partRepository.deleteAll();
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
    public void shouldGetBadReqById() throws Exception {
        mockMvc.perform(get("/api/machine/" + machineIds + 1))
                .andExpect(status().isBadRequest());
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
    public void shouldGetAllMachinesPageable() throws Exception {
        Part cpu = partRepository.save(new Part("CPU2", 10002L));
        Part cpuCooler = partRepository.save(new Part("CPU Cooler2", 1002L));
        Part thermalPaste = partRepository.save(new Part("Thermal Paste2", 102L));
        List<Part> cpuParts = Arrays.asList(cpu, cpuCooler, thermalPaste);

        Subassembly cpuSubassembly = subassemblyRepository.save(new Subassembly("CPU2", cpuParts));
        List<Subassembly> subassemblies = Arrays.asList(cpuSubassembly);

        machineRepository.save(new Machine("Windows Desktop2", subassemblies));

        mockMvc.perform(get("/api/machine/all/0/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].machineName").value("Windows Desktop"))
                .andExpect(jsonPath("$[0].subassemblies[0].subassemblyName").value("CPU"))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[0].partName").value("CPU"))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[0].partPrice").value(1000L))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[1].partName").value("CPU Cooler"))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[1].partPrice").value(100L))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[2].partName").value("Thermal Paste"))
                .andExpect(jsonPath("$[0].subassemblies[0].parts[2].partPrice").value(10L))
                .andExpect(jsonPath("$[1]").doesNotExist());

        mockMvc.perform(get("/api/machine/all/0/2"))
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
                .andExpect(jsonPath("$[1].subassemblies[0].parts[2].partPrice").value(102L))
                .andExpect(jsonPath("$[2]").doesNotExist());
    }

    @Test
    @Transactional
    public void shouldCreateMachine() throws Exception {
        Part cpu = partRepository.save(new Part("CPU2", 10002L));
        Part cpuCooler = partRepository.save(new Part("CPU Cooler2", 1002L));
        Part thermalPaste = partRepository.save(new Part("Thermal Paste2", 102L));
        List<Part> cpuParts = Arrays.asList(cpu, cpuCooler, thermalPaste);

        Subassembly cpuSubassembly = subassemblyService.createSubassembly(new Subassembly("CPU2", cpuParts));
        List<Subassembly> subassemblies = Arrays.asList(cpuSubassembly);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("machineName", "Windows Desktop2");
        objectNode.set("subassemblies", mapper.valueToTree(subassemblies));

        MvcResult result = mockMvc.perform(post("/api/machine/add")
                        .contentType("application/json")
                        .content(objectNode.toString())
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineName").value("Windows Desktop2"))
                .andExpect(jsonPath("$.subassemblies[0].subassemblyName").value("CPU2"))
                .andExpect(jsonPath("$.subassemblies[0].subassemblyPrice").value(11106L))
                .andExpect(jsonPath("$.subassemblies[0].parts[0].partName").value("CPU2"))
                .andExpect(jsonPath("$.subassemblies[0].parts[1].partName").value("CPU Cooler2"))
                .andExpect(jsonPath("$.subassemblies[0].parts[1].partPrice").value(1002L))
                .andExpect(jsonPath("$.subassemblies[0].parts[2].partName").value("Thermal Paste2"))
                .andExpect(jsonPath("$.subassemblies[0].parts[2].partPrice").value(102L))
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        JsonNode machineJson = new ObjectMapper().readTree(jsonString);
        Long id = machineJson.path("id").asLong();
        Optional<Machine> entity = machineRepository.findById(id);
        assertTrue(entity.isPresent());

        Machine machineEntity = entity.get();
        assertEquals("Windows Desktop2", machineEntity.getMachineName());
        assertEquals("CPU2", machineEntity.getSubassemblies().get(0).getSubassemblyName());
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
