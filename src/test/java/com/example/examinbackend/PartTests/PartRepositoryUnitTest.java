package com.example.examinbackend.PartTests;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.repository.PartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class PartRepositoryUnitTest {
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindPartByPartName(){
        Part testPart = new Part("Test part");
        entityManager.persist(testPart);
        entityManager.flush();
        assert partRepository.findByPartName(testPart.getPartName()).isPresent();
    }
}
