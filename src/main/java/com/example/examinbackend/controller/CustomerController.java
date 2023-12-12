package com.example.examinbackend.controller;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.service.AddressService;
import com.example.examinbackend.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final AddressService addressService;

    @Autowired
    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalCustomer.get());
        }
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/add")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("/add/{customerId}/address")
    public ResponseEntity<Customer> addCustomerAddress(@PathVariable Long customerId, @RequestBody Address address) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalCustomer.get());
        }
    }

    @PutMapping("/update/{id}/name")
    public ResponseEntity<Customer> updateCustomerName(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> optionalCustomer = customerService.updateCustomerName(id, customer);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalCustomer.get());
        }
    }

    @PutMapping("/update/{id}/email")
    public ResponseEntity<Customer> updateCustomerEmail(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> optionalCustomer = customerService.updateCustomerEmail(id, customer);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalCustomer.get());
        }
    }

    @PutMapping("/update/{id}/phone")
    public ResponseEntity<Customer> updateCustomerPhone(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> optionalCustomer = customerService.updateCustomerPhone(id, customer);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalCustomer.get());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.deleteCustomer(id);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalCustomer.get());
        }
    }


}
