package com.example.examinbackend.AddressTests;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.repository.AddressRepository;
import com.example.examinbackend.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public class AddressServiceIntegrationTest {

    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    void shouldGetAddressById() {
        Address addressOne = new Address("Address 1");
        Address addressTwo = new Address("Address 2");
        Address savedAddress = addressRepository.save(addressTwo);
        var addressById = addressService.getAddressById(savedAddress.getId());
        assert addressById.get().getAddress().equals("Address 2");
    }
    @Test
    void shouldUpdateAddressById() {
        Address addressOne = new Address("Address 1");
        Address savedAddress = addressRepository.save(addressOne);
        var addressById = addressService.getAddressById(savedAddress.getId());
        addressById.get().setAddress("New address");
        assert addressById.get().getAddress().equals("New address");
    }
    @Test
    void shouldGetAllAddresses() {
        Address addressOne = new Address("Address 1");
        Address addressTwo = new Address("Address 2");
        addressRepository.save(addressOne);
        addressRepository.save(addressTwo);
        var allAddresses = addressService.getAllAddresses();
        assert allAddresses.size() == 2;
    }
}
