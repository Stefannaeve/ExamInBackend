package com.example.examinbackend.service;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.repository.PartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService {

    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public Part getPartById(Long id) {
        return partRepository.findById(id).orElse(null);
    }
    public Part createPart(Part part) {
        return partRepository.save(part);
    }

    public void deletePart(Long id) {
        partRepository.deleteById(id);
    }

    public Part updatePartName(Long id, Part part) {
        Part existingPart = getPartById(id);
        existingPart.setPartName(part.getPartName());
        return partRepository.save(existingPart);
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }
}
