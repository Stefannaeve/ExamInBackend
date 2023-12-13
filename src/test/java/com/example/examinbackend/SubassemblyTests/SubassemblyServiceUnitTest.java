
package com.example.examinbackend.SubassemblyTests;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.SubassemblyRepository;
import com.example.examinbackend.service.SubassemblyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class SubassemblyServiceUnitTest {
    @MockBean
    private SubassemblyRepository subassemblyRepository;

    @Autowired
    private SubassemblyService subassemblyService;

    @Test
    void shouldGetSubassemblyById() {
        List<Part> parts = List.of(new Part(), new Part());
        Subassembly subassemblyOne = new Subassembly("Subassembly 1", parts);
        Subassembly subassemblyTwo = new Subassembly("Subassembly 2", parts);
        when(subassemblyRepository.findById(2L)).thenReturn(Optional.of(subassemblyTwo));
        var foundSubassembly = subassemblyService.getSubassemblyById(2L);
        assert foundSubassembly.get().getSubassemblyName().equals("Subassembly 2");
    }

    @Test
    void shouldCreateANewSubassembly() {
        List<Part> parts = List.of(new Part(), new Part());
        Subassembly subassembly = new Subassembly("Subassembly 1", parts);
        when(subassemblyRepository.save(subassembly)).thenReturn(subassembly);
        var createdSubassembly = subassemblyService.createSubassembly(subassembly);
        assert createdSubassembly.getSubassemblyName().equals("Subassembly 1");
    }

    @Test
    void shouldGetAllSubassemblies(){
        List<Subassembly> listOfSubassemblies = List.of(new Subassembly(), new Subassembly(), new Subassembly());
        when(subassemblyRepository.findAll()).thenReturn(listOfSubassemblies);
        var subassemblies = subassemblyService.getAllSubassemblies();
        assert subassemblies.size() == 3;
    }
    @Test
    void shouldDeleteANewSubassemblyById() {
        List<Part> parts = List.of(new Part(), new Part());
        Subassembly subassembly = new Subassembly("Subassembly 1", parts);

        subassemblyService.deleteSubassembly(1L);
        assert subassemblyService.getAllSubassemblies().size() == 0;
    }
    @Test
    void shouldUpdateSubassemblyName() {
        List<Part> parts = List.of(new Part(), new Part());
        Subassembly subassembly = new Subassembly("Subassembly 1", parts);
        when(subassemblyRepository.save(subassembly)).thenReturn(subassembly);
        when(subassemblyRepository.findById(1L)).thenReturn(java.util.Optional.of(subassembly));
        var subassemblyById = subassemblyService.getSubassemblyById(1L);
        subassemblyById.get().setSubassemblyName("Subassembly 2");
        assert subassemblyService.getSubassemblyById(1L).get().getSubassemblyName().equals("Subassembly 2");
    }
    @Test
    void shouldGetSubassemblyByName() {
        List<Part> parts = List.of(new Part(), new Part());
        Subassembly subassembly = new Subassembly("Subassembly 1", parts);
        when(subassemblyRepository.save(subassembly)).thenReturn(subassembly);
        when(subassemblyRepository.findBySubassemblyName("Subassembly 1")).thenReturn(java.util.Optional.of(subassembly));
        var subassemblyByName = subassemblyService.getSubassemblyByName("Subassembly 1");
        assert subassemblyByName.get().getSubassemblyName().equals("Subassembly 1");
    }
}
