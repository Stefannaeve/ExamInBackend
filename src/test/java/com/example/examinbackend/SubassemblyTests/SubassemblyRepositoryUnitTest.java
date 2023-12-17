package com.example.examinbackend.SubassemblyTests;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.SubassemblyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
public class SubassemblyRepositoryUnitTest {
    @Autowired
    private SubassemblyRepository subassemblyRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindSubassemblyBySubassemblyName(){
        List<Part> listOfTestParts = List.of(new Part("Test part 1"), new Part("Test part 2"));
        Subassembly testSubassembly = new Subassembly("Test subassembly", listOfTestParts);
        entityManager.persist(testSubassembly);
        entityManager.flush();
        assert subassemblyRepository.findBySubassemblyName(testSubassembly.getSubassemblyName()).isPresent();
    }
}
