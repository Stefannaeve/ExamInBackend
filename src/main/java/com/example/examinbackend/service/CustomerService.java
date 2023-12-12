package com.example.examinbackend.service;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }else {
            return optionalCustomer;
        }
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> deleteCustomer(Long id) {
        Optional<Customer> optionalCustomer = getCustomerById(id);
        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }
        customerRepository.deleteById(id);
        return optionalCustomer;
    }

    public Optional<Customer> updateCustomerName(Long id, Customer customer) {
        Optional<Customer> existingCustomer = getCustomerById(id);
        if (existingCustomer.isEmpty()) {
            return Optional.empty();
        }
        existingCustomer.get().setCustomerName(customer.getCustomerName());
        return Optional.of(customerRepository.save(existingCustomer.get()));
    }

    public Optional<Customer> updateCustomerEmail(Long id, Customer customer) {
        Optional<Customer> existingCustomer = getCustomerById(id);
        if (existingCustomer.isEmpty()) {
            return Optional.empty();
        }
        existingCustomer.get().setEmail(customer.getEmail());
        return Optional.of(customerRepository.save(existingCustomer.get()));
    }

    public Optional<Customer> updateCustomerPhone(Long id, Customer customer) {
        Optional<Customer> existingCustomer = getCustomerById(id);
        if (existingCustomer.isEmpty()) {
            return Optional.empty();
        }
        existingCustomer.get().setPhone(customer.getPhone());
        return Optional.of(customerRepository.save(existingCustomer.get()));
    }
    public Optional<Customer> addCustomerAddress(Long id, Address address) {
        Optional<Customer> existingCustomer = getCustomerById(id);
        if (existingCustomer.isEmpty()) {
            return Optional.empty();
        }
        existingCustomer.get().getAddresses().add(address);
        return Optional.of(customerRepository.save(existingCustomer.get()));
    }

    public List<Address> getAllAddresses(Customer customer) {
        return customer.getAddresses();
    }

    public Optional<Customer> addAddress(Address address, Long id) {
        Optional<Customer> optionalCustomer = getCustomerById(id);
        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }
        optionalCustomer.get().getAddresses().add(address);
        return Optional.of(customerRepository.save(optionalCustomer.get()));
    }
}
