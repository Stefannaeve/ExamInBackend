package com.example.examinbackend.AddressTests;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.repository.AddressRepository;
import com.example.examinbackend.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class AddressServiceUnitTest {
    @MockBean
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;

    @Test
    void shouldGetAddressById() {
        Address addressTwo = new Address("Address 2");
        when(addressRepository.findById(2L)).thenReturn(Optional.of(addressTwo));
        var addressById = addressService.getAddressById(2L);
        assert addressById.get().getAddress().equals("Address 2");
    }
    @Test
    void shouldUpdateAddressById() {
        Address addressOne = new Address("Address 1");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(addressOne));
        var addressById = addressService.getAddressById(1L);
        addressById.get().setAddress("New address");
        assert addressById.get().getAddress().equals("New address");
    }

    @Test
    void shouldGetAllAddresses() {
        Address addressOne = new Address("Address 1");
        Address addressTwo = new Address("Address 2");
        when(addressRepository.findAll()).thenReturn(List.of(addressOne, addressTwo));
        var allAddresses = addressService.getAllAddresses();
        assert allAddresses.size() == 2;
    }
}
