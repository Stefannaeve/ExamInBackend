package com.example.examinbackend.service;

import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.SubassemblyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubassemblyService {

    private final SubassemblyRepository subassemblyRepository;

    @Autowired
    public SubassemblyService(SubassemblyRepository subassemblyRepository) {
        this.subassemblyRepository = subassemblyRepository;
    }

    public Optional<Subassembly> getSubassemblyById(Long id) {
        Optional<Subassembly> optionalSubassembly = subassemblyRepository.findById(id);
        if (optionalSubassembly.isEmpty()) {
            return Optional.empty();
        }else {
            return optionalSubassembly;
        }
    }

    public void createSubassembly(Subassembly subassembly) {
        subassemblyRepository.save(subassembly);
    }

    public List<Subassembly> getAllSubassemblies() {
        return subassemblyRepository.findAll();
    }

    public Optional<Subassembly> deleteSubassembly(Long id) {
        Optional<Subassembly> optionalSubassembly = getSubassemblyById(id);
        if (optionalSubassembly.isEmpty()) {
            return Optional.empty();
        }
        subassemblyRepository.deleteById(id);
        return optionalSubassembly;
    }
    public Optional<Subassembly> updateSubassemblyName(Long id, Subassembly subassembly) {
        Optional<Subassembly> existingSubassembly = getSubassemblyById(id);
        if (existingSubassembly.isEmpty()) {
            return Optional.empty();
        }
        existingSubassembly.get().setSubassemblyName(subassembly.getSubassemblyName());
        return Optional.of(subassemblyRepository.save(existingSubassembly.get()));
    }

    public Subassembly getSubassemblyByName(String name) {
        return subassemblyRepository.findBySubassemblyName(name);
    }
}
