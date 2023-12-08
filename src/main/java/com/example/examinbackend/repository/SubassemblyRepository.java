package com.example.examinbackend.repository;

import com.example.examinbackend.model.Subassembly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubassemblyRepository extends JpaRepository<Subassembly, Long>{
}
