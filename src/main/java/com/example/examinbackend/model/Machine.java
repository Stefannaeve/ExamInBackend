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
public class Machine {

    @Id
    @GeneratedValue(generator = "machine_generator")
    @SequenceGenerator(name = "machine_generator", sequenceName = "machine_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "machine_id")
    private Long id;

    @Column(name = "machine_name")
    private String name;

    @OneToMany
    @JoinColumn(name = "subassembly_id")
    private List<Subassembly> subassemblies;
}
