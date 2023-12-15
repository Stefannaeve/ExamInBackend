package com.example.examinbackend.PartTests;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.repository.PartRepository;
import com.example.examinbackend.service.PartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class PartServiceIntegrationTest {
    @Autowired
    private PartService partService;
    @Autowired
    private PartRepository partRepository;


    @Test
    @Transactional
    void shouldGetPartById() {
        Part part = new Part("Part 1", 1L);
        Part savedPart = partRepository.save(part);
        var foundPart = partService.getPartById(savedPart.getId());
        assert foundPart.get().getId().equals(1L);
    }

    @Test
    @Transactional
    void shouldGetPartByName(){
        Part part = new Part("Part 1", 1L);
        Part savedPart = partRepository.save(part);
        var foundPart = partService.getPartByName(savedPart.getPartName());
        assert foundPart.getPartName().equals("Part 1");
    }
    @Test
    @Transactional
    void shouldCreatePart(){
        Part createdPart = new Part("Part 1", 1L);
        Part savedPart = partRepository.save(createdPart);
        assert savedPart.getPartName().equals("Part 1");
    }

    @Test
    @Transactional
    void shouldDeletePart(){
        Part part = new Part("Part 1", 1L);
        Part savedPart = partRepository.save(part);
        partService.deletePart(savedPart.getId());
        assert partService.getAllParts().size() == 0;
    }

    @Test
    @Transactional
    void shouldUpdatePartName(){
        Part part = new Part("Part 1", 1L);
        Part savedPart = partRepository.save(part);
        var partById = partService.getPartById(savedPart.getId());
        partById.get().setPartName("Part 2");
        assert partById.get().getPartName().equals("Part 2");
    }
    @Test
    @Transactional
    void shouldGetAllParts() {
        Part part = new Part("Part 1", 1L);
        Part partTwo = new Part("Part 2", 2L);
        partRepository.save(part);
        partRepository.save(partTwo);
        var parts = partService.getAllParts();
        assert parts.size() == 2;
    }
}
