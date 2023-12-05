package com.example.examinbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "subassembly")
public class Subassembly {

    @Id
    @GeneratedValue(generator = "subassembly_generator")
    @SequenceGenerator(name = "subassembly_generator", sequenceName = "subassembly_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "subassembly_id")
    private Long id;

    @Column(name = "subassembly_name")
    private String name;

    @OneToMany
    @JoinColumn(name = "part_id")
    private List<Part> parts;
}
