package com.example.examinbackend.controller;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping(value="/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Optional<Address> optionalAddress = addressService.createAddress(address);
        return optionalAddress.map(value -> ResponseEntity.status(HttpStatus.CREATED).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping(value ="/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        Optional<Address> optionalAddress = addressService.updateAddressById(id, address);
        return optionalAddress.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
    @GetMapping("/all")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }
    @GetMapping("/all/{pageNumber}/{pageSize}")
    public List<Address> getAllAddressesPageable(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return addressService.getAllAddressesPageable(pageNumber, pageSize);
    }

    @PutMapping(value="/add/customer/{addressId}/{customerId}", produces = "application/json")
    public ResponseEntity<Address> addCustomerToAddress(@PathVariable Long addressId, @PathVariable Long customerId) {
        Optional<Address> optionalAddress = addressService.addCustomerToAddress(addressId, customerId);
        return optionalAddress.map(address -> ResponseEntity.status(HttpStatus.OK).body(address)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
