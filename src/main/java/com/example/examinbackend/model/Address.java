package com.example.examinbackend.model;

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
public class Address {

    @Id
    @GeneratedValue(generator = "address_generator")
    @SequenceGenerator(name = "address_generator", sequenceName = "address_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
