package com.example.examinbackend.service;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Optional<Address> getAddressById(Long addressId) {
        return addressRepository.findById(addressId);
    }

    public Address addAddress(Address address, Customer customer) {

        return addressRepository.save(address);
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
}
