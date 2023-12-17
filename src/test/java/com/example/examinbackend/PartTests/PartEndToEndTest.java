package com.example.examinbackend.PartTests;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.repository.PartRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PartEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartRepository partRepository;

    private Long partId;

    @BeforeEach
    public void setUp() {
        Part part = new Part("Part 1", 100L);
        partId = partRepository.save(part).getId();
    }

    @Test
    @Transactional
    public void shouldReturnPartById() throws Exception {
        mockMvc.perform(get("/api/part/" + partId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(partId))
                .andExpect(jsonPath("$.partName").value("Part 1"))
                .andExpect(jsonPath("$.partPrice").value(100L));
    }

    @Test
    @Transactional
    public void shouldNotReturnPartById() throws Exception {
        mockMvc.perform(get("/api/part/" + partId + 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void shouldReturnAllParts() throws Exception {

        Part part2 = partRepository.save(new Part("Part 2", 200L));

        mockMvc.perform(get("/api/part/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(partId))
                .andExpect(jsonPath("$[0].partName").value("Part 1"))
                .andExpect(jsonPath("$[0].partPrice").value(100L))
                .andExpect(jsonPath("$[1].id").value(part2.getId()))
                .andExpect(jsonPath("$[1].partName").value("Part 2"))
                .andExpect(jsonPath("$[1].partPrice").value(200L));
    }

    @Test
    @Transactional
    public void shouldReturnAllPartsPageable() throws Exception {

        Part part2 = partRepository.save(new Part("Part 2", 200L));
        Part part3 = partRepository.save(new Part("Part 3", 300L));
        Part part4 = partRepository.save(new Part("Part 4", 400L));

        mockMvc.perform(get("/api/part/all/0/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(partId))
                .andExpect(jsonPath("$[0].partName").value("Part 1"))
                .andExpect(jsonPath("$[0].partPrice").value(100L))
                .andExpect(jsonPath("$[1].partName").doesNotExist());

        mockMvc.perform(get("/api/part/all/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(part2.getId()))
                .andExpect(jsonPath("$[0].partName").value("Part 2"))
                .andExpect(jsonPath("$[0].partPrice").value(200L))
                .andExpect(jsonPath("$[1].partName").doesNotExist());

        mockMvc.perform(get("/api/part/all/0/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(partId))
                .andExpect(jsonPath("$[0].partName").value("Part 1"))
                .andExpect(jsonPath("$[0].partPrice").value(100L))
                .andExpect(jsonPath("$[1].id").value(part2.getId()))
                .andExpect(jsonPath("$[1].partName").value("Part 2"))
                .andExpect(jsonPath("$[1].partPrice").value(200L))
                .andExpect(jsonPath("$[2].partName").doesNotExist());

        mockMvc.perform(get("/api/part/all/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(part3.getId()))
                .andExpect(jsonPath("$[0].partName").value("Part 3"))
                .andExpect(jsonPath("$[0].partPrice").value(300L))
                .andExpect(jsonPath("$[1].id").value(part4.getId()))
                .andExpect(jsonPath("$[1].partName").value("Part 4"))
                .andExpect(jsonPath("$[1].partPrice").value(400L))
                .andExpect(jsonPath("$[2].partName").doesNotExist());
    }

    @Test
    @Transactional
    public void shouldAddPart() throws Exception {
        String partName = "Part 3";
        Long partPrice = 300L;

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("partName", partName);
        objectNode.put("partPrice", partPrice);

        MvcResult result = mockMvc.perform(post("/api/part/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partName").value(partName))
                .andExpect(jsonPath("$.partPrice").value(partPrice))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        JsonNode part = objectMapper.readTree(response);
        Long partId = part.get("id").asLong();
        Optional<Part> entity = partRepository.findById(partId);
        assert entity.isPresent();

        Part partEntity = entity.get();
        assertEquals(partName, partEntity.getPartName());
        assertEquals(partPrice, partEntity.getPartPrice());
    }

    @Test
    @Transactional
    public void shouldUpdatePartName() throws Exception {

        MvcResult result = mockMvc.perform(put("/api/part/update/" + partId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"partName\": \"Part 4\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partName").value("Part 4"))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String response = result.getResponse().getContentAsString();
        JsonNode part = objectMapper.readTree(response);
        Long partId = part.get("id").asLong();
        Optional<Part> entity = partRepository.findById(partId);
        assert entity.isPresent();

        Part partEntity = entity.get();
        assertEquals("Part 4", partEntity.getPartName());
    }

    @Test
    @Transactional
    public void shouldDeletePart() throws Exception {

        mockMvc.perform(delete("/api/part/delete/" + partId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(partId))
                .andExpect(jsonPath("$.partName").value("Part 1"))
                .andExpect(jsonPath("$.partPrice").value(100L));

        Optional<Part> entity = partRepository.findById(partId);
        assert entity.isEmpty();
    }

}
