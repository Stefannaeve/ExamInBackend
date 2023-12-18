package com.example.examinbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Address {

    @Id
    @GeneratedValue(generator = "address_generator")
    @SequenceGenerator(name = "address_generator", sequenceName = "address_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "address")
    private String address;

    @ManyToMany(mappedBy = "addresses", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("addresses")
    private List<Customer> customer;

    public Address(String address) {
        this.address = address;
    }

}
