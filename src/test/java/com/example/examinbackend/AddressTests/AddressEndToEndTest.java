package com.example.examinbackend.AddressTests;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.repository.AddressRepository;
import com.example.examinbackend.service.AddressService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    private List<Long> addressIds;

    @BeforeEach
    public void setUp() {
        addressIds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Optional<Address> address = addressService.createAddress(new Address("TestAddress" + i));
            addressIds.add(address.get().getId());
        }
    }

    @AfterEach
    public void tearDown() {
        addressRepository.deleteAll();
    }
    @Test
    public void shouldCreateAddress() throws Exception {
        String addressString = "Miami";
        MvcResult result = mockMvc.perform(post("/api/address/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"" + addressString + "\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(addressString))
                .andReturn();

        // Verifying that the address is created in the database
        String jsonString = result.getResponse().getContentAsString();
        JsonNode address = new ObjectMapper().readTree(jsonString);
        Long id = address.path("id").asLong();
        Optional<Address> entity = addressRepository.findById(id);
        assertTrue(entity.isPresent());

        // Verifying that the address string is correct
        Address addressEntity = entity.get();
        assertEquals(addressString, addressEntity.getAddress());
    }

    @Test
    public void getAllAddressesTest() throws Exception {
        mockMvc.perform(get("/api/address/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("TestAddress0"))
                .andExpect(jsonPath("$[1].address").value("TestAddress1"))
                .andExpect(jsonPath("$[2].address").value("TestAddress2"));
    }
    @Test
    public void updateAddressTest() throws Exception {
        List<Address> addresses = addressService.getAllAddresses();
        for (Address address : addresses) {
            System.out.println(address);
        }
        mockMvc.perform(put("/api/address/update/{id}", addressIds.get(0))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"Kongeslottet\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("Kongeslottet"));
    }
}