package com.example.examinbackend.CustomerTests;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.AddressRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

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
    public void shouldGetCustomerById() throws Exception {
        customerRepository.deleteAll();
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Long customerId = customerRepository.save(customer).getId();

        mockMvc.perform(get("/api/customer/" + customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer.getPhone()));
    }

    @Test
    @Transactional
    public void shouldGetCustomerByIdBadReq() throws Exception {
        customerRepository.deleteAll();
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Long customerId = customerRepository.save(customer).getId();

        mockMvc.perform(get("/api/customer/" + customerId + 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void shouldGetAllCustomers() throws Exception {
        Customer customer = customerService.createCustomer(new Customer("TestCustomer", "TestEmail", "012345"));
        Customer customer2 = customerService.createCustomer(new Customer("TestCustomer2", "TestEmail2", "0123452"));

        mockMvc.perform(get("/api/customer/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$[0].email").value(customer.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(customer.getPhone()))
                .andExpect(jsonPath("$[1].customerName").value(customer2.getCustomerName()))
                .andExpect(jsonPath("$[1].email").value(customer2.getEmail()))
                .andExpect(jsonPath("$[1].phone").value(customer2.getPhone()));
    }

    @Test
    @Transactional
    public void shouldGetAllCustomerByPageable() throws Exception {
        Customer customer = customerService.createCustomer(new Customer("TestCustomer", "TestEmail", "012345"));
        Customer customer2 = customerService.createCustomer(new Customer("TestCustomer2", "TestEmail2", "0123452"));
        Customer customer3 = customerService.createCustomer(new Customer("TestCustomer3", "TestEmail3", "0123453"));

        mockMvc.perform(get("/api/customer/all/0/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$[0].email").value(customer.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(customer.getPhone()))
                .andExpect(jsonPath("$[1].customerName").value(customer2.getCustomerName()))
                .andExpect(jsonPath("$[1].email").value(customer2.getEmail()))
                .andExpect(jsonPath("$[1].phone").value(customer2.getPhone()))
                .andExpect(jsonPath("$[2].customerName").doesNotExist());

        mockMvc.perform(get("/api/customer/all/1/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].customerName").value(customer3.getCustomerName()))
                .andExpect(jsonPath("$[0].email").value(customer3.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(customer3.getPhone()))
                .andExpect(jsonPath("$[1].customerName").doesNotExist());
    }

    @Test
    @Transactional
    public void shouldUpdateCustomerName() throws Exception {
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Long customerId = customerService.createCustomer(customer).getId();

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
    public void shouldDeleteCustomer() throws Exception {
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Long customerId = customerService.createCustomer(customer).getId();

        MvcResult result = mockMvc.perform(delete("/api/customer/delete/" + customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer.getPhone()))
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        JsonNode customerJson = new ObjectMapper().readTree(jsonString);
        Long id = customerJson.path("id").asLong();
        assertTrue(customerRepository.findById(id).isEmpty());
    }

    @Test
    @Transactional
    public void shouldAddAddressToCustomer() throws Exception {
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Long customerId = customerService.createCustomer(customer).getId();

        mockMvc.perform(post("/api/customer/" + customerId + "/address/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"TestAddress\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.addresses[0].address").value("TestAddress"));
    }

    @Test
    @Transactional
    public void shouldGetAllAddressesFromCustomer() throws Exception {
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        customer.setAddresses(Arrays.asList(new Address("TestAddress"), new Address("TestAddress2")));
        Long customerId = customerService.createCustomer(customer).getId();

        mockMvc.perform(get("/api/customer/" + customerId + "/address/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("TestAddress"))
                .andExpect(jsonPath("$[1].address").value("TestAddress2"));
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
    }

    @Test
    @Transactional
    public void shouldDeleteAddressFromCustomer() throws Exception {
        Customer customer = new Customer("TestCustomer", "TestEmail", "012345");
        Address address = new Address("TestAddress");
        Customer customerFromRepo = customerService.createCustomer(customer);
        address.setCustomer(customerFromRepo);
        Address repoAddress = addressRepository.save(address);
        customerFromRepo.setAddresses(Arrays.asList(repoAddress));
        // customer.setAddresses(Arrays.asList(addressRepository.save(new Address("TestAddress"))));
        Customer testCustomer = customerRepository.findById(customerFromRepo.getId()).get();
        Address testAddress = addressRepository.findById(repoAddress.getId()).get();
        int x = 2;

        MvcResult result = mockMvc.perform(delete("/api/customer/" + customerFromRepo.getId() + "/address/delete/" + repoAddress.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addresses").isEmpty())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        JsonNode customerJson = new ObjectMapper().readTree(jsonString);
        Long id = customerJson.path("id").asLong();
        Optional<Customer> entity = customerRepository.findById(id);
        assertTrue(entity.isPresent());

        Customer customerEntity = entity.get();
        assertTrue(customerEntity.getAddresses().isEmpty());
    }

}
