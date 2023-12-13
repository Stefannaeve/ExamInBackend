package com.example.examinbackend.PartTests;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.repository.PartRepository;
import com.example.examinbackend.service.PartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PartServiceUnitTest {

    @MockBean
    private PartRepository partRepository;
    @Autowired
    private PartService partService;


    @Test
    void shouldGetPartById() {
        Part partOne = new Part("Part 1", 200L);
        when(partRepository.findById(1L)).thenReturn((Optional.of(partOne)));
        var partById = partService.getPartById(1L);
        assert partById.get().getPartName().equals("Part 1");
    }
    @Test
    void shouldGetPartByName() {
        Part partOne = new Part("Part 1");
        Part partTwo = new Part("Part 2");
        when(partRepository.findByPartName("Part 2")).thenReturn(Optional.of(partTwo));
        var partByName = partService.getPartByName("Part 2");
        assert partByName.getPartName().equals("Part 2");
    }
    @Test
    void shouldCreateANewPart() {
        Part part = new Part("Part 1", 100L);
        when(partRepository.save(part)).thenReturn(part);
        var createdPart = partService.createPart(part);
        assert createdPart.getPartName().equals("Part 1");
    }
    @Test
    void shouldDeleteANewPart() {
        Part part = new Part("Part 1");
        partService.deletePart(1L);
        assert partService.getAllParts().size() == 0;
    }
    @Test
    void shouldUpdatePartName() {
        Part part = new Part("Part 1");
        when(partRepository.save(part)).thenReturn(part);
        var createdPart = partService.createPart(part);
        createdPart.setPartName("Part 2");
        assert createdPart.getPartName().equals("Part 2");
    }
    @Test
    void shouldGetAllParts() {
        List<Part> listOfParts = List.of(new Part(), new Part(), new Part());
        when(partRepository.findAll()).thenReturn(listOfParts);
        var parts = partService.getAllParts();
        assert parts.size() == 3;
    }
}
