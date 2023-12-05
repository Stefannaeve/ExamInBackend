package com.example.examinbackend.service;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId).orElse(null);
    }

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddressById(Long id, Address address) {
        Address existingAddress = getAddressById(id);
        existingAddress.setAddress(address.getAddress());
        return addressRepository.save(existingAddress);
    }
}
