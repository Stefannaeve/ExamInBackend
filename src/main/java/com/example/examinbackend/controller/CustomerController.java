package com.example.examinbackend.controller;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.service.AddressService;
import com.example.examinbackend.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/add")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("/add/{customerId}/{addressId}")
    public void addCustomerAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        Customer customer = customerService.getCustomerById(customerId);
        Address address = addressService.getAddressById(addressId);

        customer.getAddresses().add(address);
        customerService.createCustomer(customer);
    }

    @PutMapping("/update/{id}/name")
    public Customer updateCustomerName(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomerName(id, customer);
    }

    @PutMapping("/update/{id}/email")
    public Customer updateCustomerEmail(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomerEmail(id, customer);
    }

    @PutMapping("/update/{id}/phone")
    public Customer updateCustomerPhone(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomerPhone(id, customer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }


}
