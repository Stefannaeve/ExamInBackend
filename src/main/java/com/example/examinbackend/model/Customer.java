package com.example.examinbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Customer {

    @Id
    @GeneratedValue(generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator", sequenceName = "customer_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String email;

    @Column(name = "customer_phone")
    private String phone;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("customer")
    @JoinColumn(name = "customer_id")
    private List<Order> orders = new ArrayList<>();

    public Customer(String customerName, String email, String phone) {
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
    }

    public Customer(String customerName) {
        this.customerName = customerName;
    }
}
