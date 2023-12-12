package com.example.examinbackend.service;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.repository.PartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {

    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public Optional<Part> getPartById(Long id) {
        Optional<Part> optionalPart = partRepository.findById(id);
        if (optionalPart.isEmpty()) {
            return Optional.empty();
        }else {
            return optionalPart;
        }
    }
    public Part getPartByName(String name) {
        return partRepository.findByPartName(name).orElse(null);
    }
    public Part createPart(Part part) {
        return partRepository.save(part);
    }

    public Optional<Part> deletePart(Long id) {
        Optional<Part> optionalPart = getPartById(id);
        if (optionalPart.isEmpty()) {
            return Optional.empty();
        }
        partRepository.deleteById(id);
        return optionalPart;
    }

    public Optional<Part> updatePartName(Long id, Part part) {
        Optional<Part> existingPart = getPartById(id);
        if (existingPart.isEmpty()) {
            return Optional.empty();
        }
        existingPart.get().setPartName(part.getPartName());
        return Optional.of(partRepository.save(existingPart.get()));
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }
}
