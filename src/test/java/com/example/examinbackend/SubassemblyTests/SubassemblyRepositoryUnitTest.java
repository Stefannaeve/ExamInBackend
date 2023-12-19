package com.example.examinbackend.SubassemblyTests;

import com.example.examinbackend.model.Part;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.repository.SubassemblyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class SubassemblyRepositoryUnitTest {
    @Autowired
    private SubassemblyRepository subassemblyRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindSubassemblyBySubassemblyName(){
        Part part1 = new Part("Test part 1");
        Part part2 = new Part("Test part 2");
        entityManager.persist(part1);
        entityManager.persist(part2);

        List<Part> listOfTestParts = List.of(part1, part2);
        Subassembly testSubassembly = new Subassembly("Test subassembly", listOfTestParts);

        entityManager.persist(testSubassembly);

        assert subassemblyRepository.findBySubassemblyName("Test subassembly").isPresent();
    }
}
