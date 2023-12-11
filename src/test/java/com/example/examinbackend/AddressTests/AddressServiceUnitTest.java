package com.example.examinbackend.AddressTests;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.repository.AddressRepository;
import com.example.examinbackend.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class AddressServiceUnitTest {
    @MockBean
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;

    @Test
    void shouldGetAddressById() {
        Address addressOne = new Address("Address 1");
        Address addressTwo = new Address("Address 2");
        when(addressRepository.findById(2L)).thenReturn(java.util.Optional.of(addressTwo));
        var addressById = addressService.getAddressById(2L);
        assert addressById.getAddress().equals("Address 2");
    }
    @Test
    void shouldCreateANewAddress() {
        Address address = new Address("Address 1");
        when(addressRepository.save(address)).thenReturn(address);
        var createdAddress = addressService.addAddress(address);
        assert createdAddress.getAddress().equals("Address 1");
    }
   @Test
    void shouldGetAllAddresses() {
        List<Address> listOfAddresses = List.of(new Address(), new Address(), new Address());
        when(addressRepository.findAll()).thenReturn(listOfAddresses);
        var addresses = addressService.getAllAddresses();
        assert addresses.size() == 3;
    }
}
