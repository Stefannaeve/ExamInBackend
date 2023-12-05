package com.example.examinbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

//    @ManyToOne
//    @JoinColumn(name = "subassembly_id")
//    private List<Subassembly> subassembly;
}
