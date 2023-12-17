package com.example.examinbackend.SubassemblyTests;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.PartRepository;
import com.example.examinbackend.repository.SubassemblyRepository;
import com.example.examinbackend.service.PartService;
import com.example.examinbackend.service.SubassemblyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SubassemblyEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubassemblyRepository subassemblyRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SubassemblyService subassemblyService;

    @Autowired
    private PartService partService;

    private Long subassemblyId;

    @BeforeEach
    public void setUp() {
        Part part = partRepository.save(new Part("Part 1", 100L));
        Part part2 = partRepository.save(new Part("Part 2", 200L));
        Part part3 = partRepository.save(new Part("Part 3", 300L));
        List<Part> parts = List.of(part, part2, part3);

        subassemblyId = subassemblyRepository.save(new Subassembly("Subassembly 1", parts)).getId();
    }

    @Test
    @Transactional
    public void shouldGetSubassemblyById() throws Exception {
        mockMvc.perform(get("/api/subassembly/" + subassemblyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subassemblyName").value("Subassembly 1"))
                .andExpect(jsonPath("$.parts[0].partName").value("Part 1"))
                .andExpect(jsonPath("$.parts[1].partName").value("Part 2"))
                .andExpect(jsonPath("$.parts[2].partName").value("Part 3"));
    }

    @Test
    @Transactional
    public void shouldNotGetSubassemblyById() throws Exception {
        mockMvc.perform(get("/api/subassembly/" + subassemblyId + 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void shouldGetAllSubassemblies() throws Exception {
        Part part5 = partRepository.save(new Part("Part 5", 100L));
        Part part6 = partRepository.save(new Part("Part 6", 200L));
        Part part7 = partRepository.save(new Part("Part 7", 300L));
        List<Part> parts = List.of(part5, part6, part7);

        subassemblyId = subassemblyRepository.save(new Subassembly("Subassembly 2", parts)).getId();

        mockMvc.perform(get("/api/subassembly/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subassemblyName").value("Subassembly 1"))
                .andExpect(jsonPath("$[0].parts[0].partName").value("Part 1"))
                .andExpect(jsonPath("$[0].parts[1].partName").value("Part 2"))
                .andExpect(jsonPath("$[0].parts[2].partName").value("Part 3"))
                .andExpect(jsonPath("$[1].subassemblyName").value("Subassembly 2"))
                .andExpect(jsonPath("$[1].parts[0].partName").value("Part 5"))
                .andExpect(jsonPath("$[1].parts[1].partName").value("Part 6"))
                .andExpect(jsonPath("$[1].parts[2].partName").value("Part 7"));
    }

    @Test
    @Transactional
    public void shouldGetAllSubassembliesPageable() throws Exception {
        Part part5 = partRepository.save(new Part("Part 5", 100L));
        Part part6 = partRepository.save(new Part("Part 6", 200L));
        Part part7 = partRepository.save(new Part("Part 7", 300L));
        List<Part> parts = List.of(part5, part6, part7);

        subassemblyId = subassemblyRepository.save(new Subassembly("Subassembly 2", parts)).getId();

        Part part8 = partRepository.save(new Part("Part 5", 100L));
        Part part9 = partRepository.save(new Part("Part 6", 200L));
        Part part10 = partRepository.save(new Part("Part 7", 300L));
        List<Part> parts2 = List.of(part8, part9, part10);

        subassemblyId = subassemblyRepository.save(new Subassembly("Subassembly 3", parts2)).getId();

        Part part11 = partRepository.save(new Part("Part 5", 100L));
        Part part12 = partRepository.save(new Part("Part 6", 200L));
        Part part13 = partRepository.save(new Part("Part 7", 300L));
        List<Part> parts3 = List.of(part11, part12, part13);

        subassemblyId = subassemblyRepository.save(new Subassembly("Subassembly 3", parts3)).getId();

        mockMvc.perform(get("/api/subassembly/all/0/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subassemblyName").value("Subassembly 1"))
                .andExpect(jsonPath("$[0].parts[0].partName").value("Part 1"))
                .andExpect(jsonPath("$[0].parts[1].partName").value("Part 2"))
                .andExpect(jsonPath("$[0].parts[2].partName").value("Part 3"))
                .andExpect(jsonPath("$[1].subassemblyName").doesNotExist());

        mockMvc.perform(get("/api/subassembly/all/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subassemblyName").value("Subassembly 2"))
                .andExpect(jsonPath("$[0].parts[0].partName").value("Part 5"))
                .andExpect(jsonPath("$[0].parts[1].partName").value("Part 6"))
                .andExpect(jsonPath("$[0].parts[2].partName").value("Part 7"))
                .andExpect(jsonPath("$[1].subassemblyName").doesNotExist());

        mockMvc.perform(get("/api/subassembly/all/0/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subassemblyName").value("Subassembly 1"))
                .andExpect(jsonPath("$[0].parts[0].partName").value("Part 1"))
                .andExpect(jsonPath("$[0].parts[1].partName").value("Part 2"))
                .andExpect(jsonPath("$[0].parts[2].partName").value("Part 3"))
                .andExpect(jsonPath("$[1].subassemblyName").value("Subassembly 2"))
                .andExpect(jsonPath("$[1].parts[0].partName").value("Part 5"))
                .andExpect(jsonPath("$[1].parts[1].partName").value("Part 6"))
                .andExpect(jsonPath("$[1].parts[2].partName").value("Part 7"))
                .andExpect(jsonPath("$[2].subassemblyName").doesNotExist());
    }

    @Test
    @Transactional
    public void shouldCreateSubassembly() throws Exception {
        Part part5 = partRepository.save(new Part("Part 5", 100L));
        Part part6 = partRepository.save(new Part("Part 6", 200L));
        Part part7 = partRepository.save(new Part("Part 7", 300L));
        List<Part> parts = List.of(part5, part6, part7);

        Subassembly subassembly = subassemblyRepository.save(new Subassembly("Subassembly 2", parts));

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.set("subassemblyName", mapper.valueToTree(subassembly.getSubassemblyName()));
        objectNode.set("parts", mapper.valueToTree(subassembly.getParts()));

        MvcResult result = mockMvc.perform(post("/api/subassembly/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subassemblyName").value("Subassembly 2"))
                .andExpect(jsonPath("$.parts[0].partName").value("Part 5"))
                .andExpect(jsonPath("$.parts[1].partName").value("Part 6"))
                .andExpect(jsonPath("$.parts[2].partName").value("Part 7"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = new ObjectMapper().readTree(content);
        Long subassemblyId = jsonNode.get("id").asLong();
        Optional<Subassembly> entity = subassemblyRepository.findById(subassemblyId);
        assertTrue(entity.isPresent());

        Subassembly subassemblyEntity = entity.get();
        assertEquals("Subassembly 2", subassemblyEntity.getSubassemblyName());
        assertEquals("Part 5", subassemblyEntity.getParts().get(0).getPartName());
        assertEquals("Part 6", subassemblyEntity.getParts().get(1).getPartName());
        assertEquals("Part 7", subassemblyEntity.getParts().get(2).getPartName());
    }

    @Test
    @Transactional
    public void shouldUpdateSubassemblyName() throws Exception {
        MvcResult result = mockMvc.perform(put("/api/subassembly/update/" + subassemblyId)
                        .contentType("application/json")
                        .content("{\"subassemblyName\":\"Subassembly 2\"}")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subassemblyName").value("Subassembly 2"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = new ObjectMapper().readTree(content);
        Long subassemblyId = jsonNode.get("id").asLong();
        Optional<Subassembly> entity = subassemblyRepository.findById(subassemblyId);
        assertTrue(entity.isPresent());

        Subassembly subassemblyEntity = entity.get();
        assertEquals("Subassembly 2", subassemblyEntity.getSubassemblyName());
    }

    @Test
    @Transactional
    public void shouldDeleteSubassembly() throws Exception {
        mockMvc.perform(delete("/api/subassembly/delete/" + subassemblyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subassemblyName").value("Subassembly 1"))
                .andExpect(jsonPath("$.parts[0].partName").value("Part 1"))
                .andExpect(jsonPath("$.parts[1].partName").value("Part 2"))
                .andExpect(jsonPath("$.parts[2].partName").value("Part 3"));

        Optional<Subassembly> entity = subassemblyRepository.findById(subassemblyId);
        assertTrue(entity.isEmpty());
    }

}
