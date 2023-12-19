package com.example.examinbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "subassembly")
public class Subassembly {

    @Id
    @GeneratedValue(generator = "subassembly_generator")
    @SequenceGenerator(name = "subassembly_generator", sequenceName = "subassembly_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "subassembly_id")
    private Long id = 0L;

    @Column(name = "subassembly_name")
    private String subassemblyName;

    @Column(name = "subassembly_price")
    private Long subassemblyPrice;

    @JsonIgnoreProperties("subassemblies")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subassembly_part", joinColumns = {@JoinColumn(name = "subassembly_id")})
    private List<Part> parts = new ArrayList<>();

    public Subassembly(String subassemblyName, List<Part> parts) {
        this.subassemblyName = subassemblyName;
        this.parts = parts;
    }
    public Subassembly(String subassemblyName, Long subassemblyPrice) {
        this.subassemblyName = subassemblyName;
        this.subassemblyPrice = subassemblyPrice;
    }
}
