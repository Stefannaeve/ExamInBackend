package com.example.examinbackend.CustomerTests;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.CustomerRepository;
import com.example.examinbackend.service.CustomerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private List<Long> customerIds = new ArrayList<>();

    @AfterEach
    public void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @Transactional
    public void shouldMakeCustomer() throws Exception {

        String customerName = "TestCustomer";
        String email = "TestEmail";
        String phone = "012345";

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("customerName", customerName);
        jsonNode.put("email", email);
        jsonNode.put("phone", phone);

        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address("TestAddress"));
        addresses.add(new Address("TestAddress2"));
        jsonNode.set("addresses", objectMapper.valueToTree(addresses));

        MvcResult result = mockMvc.perform(post("/api/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNode.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(customerName))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.addresses[0].address").value(addresses.get(0).getAddress()))
                .andExpect(jsonPath("$.addresses[1].address").value(addresses.get(1).getAddress()))
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        JsonNode customer = new ObjectMapper().readTree(jsonString);
        Long customerId = customer.path("id").asLong();
        Optional<Customer> entity = customerRepository.findById(customerId);
        assertTrue(entity.isPresent());

        customerIds.add(customerId);

        Customer customerEntity = entity.get();
        assertEquals(customerName, customerEntity.getCustomerName());
        assertEquals(email, customerEntity.getEmail());
        assertEquals(phone, customerEntity.getPhone());
        assertEquals(addresses.get(0).getAddress(), customerEntity.getAddresses().get(0).getAddress());
        assertEquals(addresses.get(1).getAddress(), customerEntity.getAddresses().get(1).getAddress());
    }

    @Test
    @Transactional
    public void shouldGetCustomer() throws Exception {
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Long Id = customerRepository.save(customer).getId();

        mockMvc.perform(get("/api/customer/" + customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer.getPhone()));
    }

    @Test
    @Transactional
    public void shouldGetCustomerById() throws Exception {
        customerRepository.deleteAll();
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Long customerId = customerRepository.save(customer).getId();

        mockMvc.perform(get("/api/customer/" + customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer.getPhone()));
        System.out.println("Willy Billy");
    }

    @Test
    @Transactional
    public void shouldGetAllCustomers() throws Exception {
        Customer customer = customerService.createCustomer(new Customer("TestCustomer", "TestEmail", "012345"));
        Customer customer2 = customerService.createCustomer(new Customer("TestCustomer2", "TestEmail2", "0123452"));

        mockMvc.perform(get("/api/customer/all")
                        .accept(MediaType.APPLICATION_JSON)) // Specify the expected response type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Validate response content type
                .andExpect(jsonPath("$[0].customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$[0].email").value(customer.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(customer.getPhone()))
                .andExpect(jsonPath("$[1].customerName").value(customer2.getCustomerName()))
                .andExpect(jsonPath("$[1].email").value(customer2.getEmail()))
                .andExpect(jsonPath("$[1].phone").value(customer2.getPhone()));
    }

    @Test
    @Transactional
    public void shouldUpdateCustomerName() throws Exception {
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Long customerId = customerService.createCustomer(customer).getId();
        System.out.println(customerService.getAllCustomers());

        MvcResult result = mockMvc.perform(put("/api/customer/update/" + customerId + "/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerName\":\"Bob Marley\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerName").value("Bob Marley"))
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        JsonNode customerJson = new ObjectMapper().readTree(jsonString);
        Long id = customerJson.path("id").asLong();
        Optional<Customer> entity = customerRepository.findById(id);
        assertTrue(entity.isPresent());

        Customer customerEntity = entity.get();
        assertEquals("Bob Marley", customerEntity.getCustomerName());
    }

    @Test
    @Transactional
    public void shouldUpdateCustomerEmail() throws Exception {
        Customer customer = new Customer("Harald Haraldson", "Harald@Haraldson.Harald", "20842048");
        Long customerId = customerRepository.save(customer).getId();

        MvcResult result = mockMvc.perform(put("/api/customer/update/" + customerId + "/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"Harald@BobbyBigBoi.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("Harald@BobbyBigBoi.com"))
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        JsonNode customerJson = new ObjectMapper().readTree(jsonString);
        Long id = customerJson.path("id").asLong();
        Optional<Customer> entity = customerRepository.findById(id);
        assertTrue(entity.isPresent());

        Customer customerEntity = entity.get();
        assertEquals("Harald@BobbyBigBoi.com", customerEntity.getEmail());
    }

    @Test
    @Transactional
    public void shouldUpdateCustomerPhone() throws Exception {
        Customer customer = new Customer("Biggibigboi", "Biggest@Gmail.big", "787382768");
        Long customerId = customerRepository.save(customer).getId();
        System.out.println(customerId);

        MvcResult result = mockMvc.perform(put("/api/customer/update/" + customerId + "/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"phone\":\"536457845\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.phone").value("536457845"))
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        JsonNode customerJson = new ObjectMapper().readTree(jsonString);
        Long id = customerJson.path("id").asLong();
        Optional<Customer> entity = customerRepository.findById(id);
        assertTrue(entity.isPresent());

        Customer customerEntity = entity.get();
        assertEquals("536457845", customerEntity.getPhone());
    }

    @Test
    @Transactional
    public void shouldAddAddressToCustomer() throws Exception {
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Long customerId = customerService.createCustomer(customer).getId();

        mockMvc.perform(post("/api/customer/add/" + customerId + "/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"TestAddress\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.addresses[0].address").value("TestAddress"));
    }

    @Test
    @Transactional
    public void shouldUpdateAddressInCustomer() throws Exception {
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        customer.setAddresses(Arrays.asList(new Address("TestAddress")));
        Address address = customerService.createCustomer(customer).getAddresses().get(0);

        mockMvc.perform(put("/api/customer/address/update/" + address.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"FrognerParken\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("FrognerParken"));
        System.out.println(customer.getAddresses().get(0).getAddress());
    }

}
