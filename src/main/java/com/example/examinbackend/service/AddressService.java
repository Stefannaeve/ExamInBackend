package com.example.examinbackend.service;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.AddressRepository;
import com.example.examinbackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<Address> getAddressById(Long addressId) {
        return addressRepository.findById(addressId);
    }

    public Optional<Address> updateAddressById(Long id, Address address) {
        Optional<Address> existingAddress = getAddressById(id);
        if (existingAddress.isEmpty()) {
            return Optional.empty();
        }
        existingAddress.get().setAddress(address.getAddress());
        return Optional.of(addressRepository.save(existingAddress.get()));
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> getAllAddressesPageable(int pageNumber, int pageSize) {
        return addressRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
    }

    public Optional<Address> createAddress(Address address) {
        return Optional.of(addressRepository.save(address));
    }

    public Optional<Address> addCustomerToAddress(Long addressId, Long customerId) {
        Optional<Address> optionalAddress = getAddressById(addressId);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalAddress.isEmpty() || optionalCustomer.isEmpty()) {
            return Optional.empty();
        }

        Address address = optionalAddress.get();
        Customer customer = optionalCustomer.get();

        List<Address> addresses = customer.getAddresses();
        if(addresses == null) {
            addresses = new ArrayList<>();
        }
        addresses.add(address);
        customer.setAddresses(addresses);
        customerRepository.save(customer);
        List<Customer> customers = address.getCustomer();
        if (customers == null) {
            customers = new ArrayList<>();
        }
        customers.add(optionalCustomer.get());
        optionalAddress.get().setCustomer(customers);
        return Optional.of(addressRepository.save(optionalAddress.get()));
    }
}
