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
@Table(name = "machine")
public class Machine {

    @Id
    @GeneratedValue(generator = "machine_generator")
    @SequenceGenerator(name = "machine_generator", sequenceName = "machine_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "machine_id")
    private Long id = 0L;

    @Column(name = "machine_name")
    private String machineName;

    @Column(name = "machine_price")
    private Long machinePrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "machine_id")
    private List<Subassembly> subassemblies = new ArrayList<>();

    public Machine(String machineName, List<Subassembly> subassemblies) {
        this.machineName = machineName;
        this.subassemblies = subassemblies;
    }
}
