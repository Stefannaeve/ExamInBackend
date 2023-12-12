package com.example.examinbackend.PartTests;

import com.example.examinbackend.repository.PartRepository;
import com.example.examinbackend.service.PartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PartIntegrationTest {
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private PartService partService;

    @Test
    void shouldGetAllParts() {
        var parts = partService.getAllParts();
        assert parts.size() == 3;
    }
}
