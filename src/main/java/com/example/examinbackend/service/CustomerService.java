package com.example.examinbackend.service;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer updateCustomerName(Long id, Customer customer) {
        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setCustomerName(customer.getCustomerName());
        return customerRepository.save(existingCustomer);
    }

    public Customer updateCustomerEmail(Long id, Customer customer) {
        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setEmail(customer.getEmail());
        return customerRepository.save(existingCustomer);
    }

    public Customer updateCustomerPhone(Long id, Customer customer) {
        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setPhone(customer.getPhone());
        return customerRepository.save(existingCustomer);
    }
}
