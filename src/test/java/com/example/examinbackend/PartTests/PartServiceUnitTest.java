package com.example.examinbackend.PartTests;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.repository.PartRepository;
import com.example.examinbackend.service.PartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class PartServiceUnitTest {

    @MockBean
    private PartRepository partRepository;
    @Autowired
    private PartService partService;


    @Test
    void shouldGetPartById() {
        List<Part> listOfParts = List.of(new Part("Part 1", 200L), new Part("Part 2", 300L));

        when(partRepository.findById(2L)).thenReturn(Optional.of(listOfParts.get(1)));

        Optional<Part> partById = partService.getPartById(2L);

        Part part = partById.get();

        assert part.getPartName().equals("Part 2");
    }
    @Test
    void shouldGetPartByName() {
        Part partOne = new Part("Part 1", 200L);
        Part partTwo = new Part("Part 2", 300L);
        when(partRepository.findByPartName("Part 2")).thenReturn(Optional.of(partTwo));
        var partByName = partService.getPartByName("Part 2");
        assert partByName.getPartName().equals("Part 2");
    }
    @Test
    void shouldCreateANewPart() {
        Part part = new Part("Part 1", 200L);
        when(partRepository.save(part)).thenReturn(part);
        var createdPart = partService.createPart(part);
        assert createdPart.getPartName().equals("Part 1");
    }
    @Test
    void shouldDeleteANewPart() {
        Part part = new Part("Part 1", 200L);
        partService.deletePart(1L);
        assert partService.getAllParts().size() == 0;
    }
    @Test
    void shouldUpdatePartName() {
        Part part = new Part("Part 1", 200L);
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
