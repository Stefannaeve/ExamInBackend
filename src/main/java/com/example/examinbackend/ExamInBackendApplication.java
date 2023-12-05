package com.example.examinbackend;

import com.example.examinbackend.controller.PartController;
import com.example.examinbackend.model.Part;
import com.example.examinbackend.repository.PartRepository;
import com.example.examinbackend.service.PartService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamInBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExamInBackendApplication.class, args);

    }

}
