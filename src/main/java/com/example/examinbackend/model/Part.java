package com.example.examinbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Part {

    @Id
    @GeneratedValue(generator = "part_generator")
    @SequenceGenerator(name = "part_generator", sequenceName = "part_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "part_id")
    private Long id;

    @Column(name = "part_name")
    private String partName;

    @Column(name = "part_price")
    private Long partPrice;

    @ManyToMany(mappedBy = "parts")
    private List<Subassembly> subassemblies = new ArrayList<>();

    public Part(String partName, Long partPrice) {
        this.partName = partName;
        this.partPrice = partPrice;
    }
}
