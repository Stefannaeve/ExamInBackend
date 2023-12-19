package com.example.examinbackend.service;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.AddressRepository;
import com.example.examinbackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public Optional<Customer> getCustomerById(Long id) {
        Optional<Customer> optionalCustomer;
        optionalCustomer = customerRepository.findById(id);
        return optionalCustomer;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getAllCustomersPageable(int pageNumber, int pageSize) {
        return customerRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
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

    public List<Address> getAllAddresses(Long id) {
        Optional<Customer> optionalCustomer = getCustomerById(id);
        return optionalCustomer.map(Customer::getAddresses).orElse(null);
    }

    public Optional<Customer> addAddress(Address address, Long id) {
        Optional<Customer> optionalCustomer = getCustomerById(id);
        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }
        optionalCustomer.get().getAddresses().add(address);
        return Optional.of(customerRepository.save(optionalCustomer.get()));
    }

    public Optional<Customer> deleteAddress(Long customerId, Long addressId) {
        Optional<Customer> optionalCustomer = getCustomerById(customerId);
        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }
        List<Address> addresses = optionalCustomer.get().getAddresses();
        for (Address address : addresses) {
            if (address.getId().equals(addressId)) {
                optionalCustomer.get().getAddresses().remove(address);
                customerRepository.save(optionalCustomer.get());
                addressRepository.delete(address);
                break;
            }
        }
        return Optional.of(customerRepository.save(optionalCustomer.get()));
    }
}
