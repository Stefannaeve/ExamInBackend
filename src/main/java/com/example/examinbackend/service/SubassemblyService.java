package com.example.examinbackend.service;

import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.SubassemblyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubassemblyService {

    private final SubassemblyRepository subassemblyRepository;

    @Autowired
    public SubassemblyService(SubassemblyRepository subassemblyRepository) {
        this.subassemblyRepository = subassemblyRepository;
    }

    public Subassembly getSubassemblyById(Long id) {
        return subassemblyRepository.findById(id).orElse(null);
    }

    public void createSubassembly(Subassembly subassembly) {
        subassemblyRepository.save(subassembly);
    }

    public List<Subassembly> getAllSubassemblies() {
        return subassemblyRepository.findAll();
    }

    public void deleteSubassembly(Long id) {
        subassemblyRepository.deleteById(id);
    }
    public Subassembly updateSubassemblyName(Long id, Subassembly subassembly) {
        Subassembly existingSubassembly = getSubassemblyById(id);
        existingSubassembly.setSubassemblyName(subassembly.getSubassemblyName());
        return subassemblyRepository.save(existingSubassembly);
    }

    public Subassembly getSubassemblyByName(String name) {
        return subassemblyRepository.findBySubassemblyName(name);
    }
}
