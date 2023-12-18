package com.example.examinbackend.controller;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.service.AddressService;
import com.example.examinbackend.service.CustomerService;
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

    // region GET
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalCustomer.get());
        }
    }

    @GetMapping(value = "/all", produces = "application/json")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/all/{pageNumber}/{pageSize}", produces = "application/json")
    public List<Customer> getAllCustomersPageable(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return customerService.getAllCustomersPageable(pageNumber, pageSize);
    }

    //endregion

    //region POST
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    //endregion

    //region PUT
    @PutMapping(value = "/update/{id}/name", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> updateCustomerName(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> optionalCustomer = customerService.updateCustomerName(id, customer);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalCustomer.get());
        }
    }

    @PutMapping(value = "/update/{id}/email", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> updateCustomerEmail(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> optionalCustomer = customerService.updateCustomerEmail(id, customer);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalCustomer.get());
        }
    }

    @PutMapping(value = "/update/{id}/phone", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> updateCustomerPhone(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> optionalCustomer = customerService.updateCustomerPhone(id, customer);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalCustomer.get());
        }
    }
    //endregion

    //region DELETE
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.deleteCustomer(id);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalCustomer.get());
        }
    }
    //endregion

    //region Address

    @PostMapping(value="/{customerId}/address/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Customer> addAddress(@RequestBody Address address, @PathVariable Long customerId) {
        Optional<Customer> optionalCustomer = customerService.addAddress(address, customerId);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalCustomer.get());
        }
    }

    @PutMapping(value = "/address/update/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        Optional<Address> optionalAddress = addressService.updateAddressById(id, address);
        if (optionalAddress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalAddress.get());
        }
    }

    @GetMapping("/{customerId}/address/all")
    public List<Address> getAllAddresses(@PathVariable Long customerId) {
        return customerService.getAllAddresses(customerId);
    }

    @DeleteMapping("/{customerId}/address/delete/{addressId}")
    public ResponseEntity<Customer> deleteAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        Optional<Customer> optionalCustomer = customerService.deleteAddress(customerId, addressId);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalCustomer.get());
        }
    }

    //endregion

}
