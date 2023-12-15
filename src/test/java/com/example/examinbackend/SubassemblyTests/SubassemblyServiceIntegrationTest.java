package com.example.examinbackend.SubassemblyTests;

import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.SubassemblyRepository;
import com.example.examinbackend.service.SubassemblyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class SubassemblyServiceIntegrationTest {
    @Autowired
    private SubassemblyService subassemblyService;
    @Autowired
    private SubassemblyRepository subassemblyRepository;

    @Test
    @Transactional
    void shouldGetSubassemblyById() {
        Subassembly subassembly = new Subassembly("Subassembly 1", 1L);
        Subassembly savedSubassembly = subassemblyRepository.save(subassembly);
        var foundSubassembly = subassemblyService.getSubassemblyById(savedSubassembly.getId());
        assert foundSubassembly.get().getSubassemblyName().equals("Subassembly 1");
    }
    @Test
    @Transactional
    void shouldCreateSubassembly(){
        Subassembly createdSubassembly = new Subassembly("Subassembly 1", 1L);
        Subassembly savedSubassembly = subassemblyRepository.save(createdSubassembly);
        assert savedSubassembly.getSubassemblyName().equals("Subassembly 1");
    }
    @Test
    @Transactional
    void shouldGetAllSubassemblies() {
        Subassembly subassembly = new Subassembly("Subassembly 1", 1L);
        Subassembly subassemblyTwo = new Subassembly("Subassembly 2", 1L);
        subassemblyRepository.save(subassembly);
        subassemblyRepository.save(subassemblyTwo);
        var subassemblies = subassemblyService.getAllSubassemblies();
        assert subassemblies.size() == 2;
    }
    @Test
    @Transactional
    void shouldDeleteSubassembly(){
        Subassembly subassembly = new Subassembly("Subassembly 1", 1L);
        Subassembly savedSubassembly = subassemblyRepository.save(subassembly);
        subassemblyService.deleteSubassembly(savedSubassembly.getId());
        assert subassemblyService.getAllSubassemblies().size() == 0;
    }
    @Test
    @Transactional
    void shouldUpdateSubassemblyName(){
        Subassembly subassembly = new Subassembly("Subassembly 1", 1L);
        Subassembly savedSubassembly = subassemblyRepository.save(subassembly);
        var SubassemblyById = subassemblyService.getSubassemblyById(savedSubassembly.getId());
        SubassemblyById.get().setSubassemblyName("Subassembly 2");
        assert SubassemblyById.get().getSubassemblyName().equals("Subassembly 2");
    }
    @Test
    @Transactional
    void shouldGetSubassemblyByName(){
        Subassembly subassembly = new Subassembly("Subassembly 1", 1L);
        Subassembly savedSubassembly = subassemblyRepository.save(subassembly);
        var foundSubassembly = subassemblyService.getSubassemblyByName(savedSubassembly.getSubassemblyName());
        assert foundSubassembly.get().getSubassemblyName().equals("Subassembly 1");
    }
}
