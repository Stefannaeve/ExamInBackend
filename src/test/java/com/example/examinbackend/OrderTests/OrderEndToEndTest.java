package com.example.examinbackend.OrderTests;

import com.example.examinbackend.model.*;
import com.example.examinbackend.repository.*;
import com.example.examinbackend.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    private Long orderId;

    @BeforeEach
    public void setUp() {
        Customer customer = customerRepository.save(new Customer("TestCustomer", "TestEmail", "TestPhone"));
        orderId = orderService.createOrder(customer.getId()).get().getId();
    }



    @Test
    @Transactional
    public void shouldGetAllOrders() throws Exception {
        Customer customer = customerRepository.save(new Customer("Test Customer", "Test Email", "Test Phone"));
        Customer order2 = orderService.createOrder(customer.getId()).get().getCustomer();

        mockMvc.perform(get("/api/order/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(orderId))
                .andExpect(jsonPath("$[0].customer.customerName").value("TestCustomer"))
                .andExpect(jsonPath("$[0].customer.email").value("TestEmail"))
                .andExpect(jsonPath("$[0].customer.phone").value("TestPhone"))
                .andExpect(jsonPath("$[1].id").value(order2.getId()))
                .andExpect(jsonPath("$[1].customer.customerName").value("Test Customer"))
                .andExpect(jsonPath("$[1].customer.email").value("Test Email"))
                .andExpect(jsonPath("$[1].customer.phone").value("Test Phone"));
    }

    @Test
    @Transactional
    public void shouldGetAllOrdersPageable() throws Exception {
        Customer customer = customerRepository.save(new Customer("customer2", "customer2@email.email", "123"));
        Order order2 = orderService.createOrder(customer.getId()).get();
        Customer customer2 = customerRepository.save(new Customer("customer3", "customer3@email.email", "1233"));
        Order order3 = orderService.createOrder(customer2.getId()).get();
        Customer customer3 = customerRepository.save(new Customer("customer4", "customer4@email.email", "12333"));
        Order order4 = orderService.createOrder(customer3.getId()).get();

        mockMvc.perform(get("/api/order/all/0/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(orderId))
                .andExpect(jsonPath("$[0].customer.customerName").value("TestCustomer"))
                .andExpect(jsonPath("$[1].id").doesNotExist());

        mockMvc.perform(get("/api/order/all/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order2.getId()))
                .andExpect(jsonPath("$[0].customer.customerName").value("customer2"))
                .andExpect(jsonPath("$[1].id").doesNotExist());

        mockMvc.perform(get("/api/order/all/0/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(orderId))
                .andExpect(jsonPath("$[0].customer.customerName").value("TestCustomer"))
                .andExpect(jsonPath("$[1].id").value(order2.getId()))
                .andExpect(jsonPath("$[1].customer.customerName").value("customer2"))
                .andExpect(jsonPath("$[2].id").value(order3.getId()))
                .andExpect(jsonPath("$[2].customer.customerName").value("customer3"))
                .andExpect(jsonPath("$[3].id").doesNotExist());

        mockMvc.perform(get("/api/order/all/1/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order4.getId()))
                .andExpect(jsonPath("$[0].customer.customerName").value("customer4"))
                .andExpect(jsonPath("$[1].id").doesNotExist());

    }

    @Test
    @Transactional
    public void shouldGetOrderById() throws Exception {
        mockMvc.perform(get("/api/order/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.customer.customerName").value("TestCustomer"))
                .andExpect(jsonPath("$.customer.email").value("TestEmail"))
                .andExpect(jsonPath("$.customer.phone").value("TestPhone"));
    }

    @Test
    @Transactional
    public void shouldNotGetOrderByIdBecauseIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/order/" + orderId + 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void ShouldAddCustomerToOrderAndCreateOrder() throws Exception {

        Customer customer = customerRepository.save(new Customer("Test Customer", "Test Email", "Test Phone"));

        MvcResult result = mockMvc.perform(post("/api/order/add/customer/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerName\":\"Test Customer\", \"email\":\"Test Email\", \"phone\":\"Test Phone\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer.customerName").value("Test Customer"))
                .andExpect(jsonPath("$.customer.email").value("Test Email"))
                .andExpect(jsonPath("$.customer.phone").value("Test Phone"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = new ObjectMapper().readTree(content);
        Long orderId = jsonNode.get("id").asLong();
        Optional<Order> entity = orderRepository.findById(orderId);
        assert entity.isPresent();

        Order orderEntity = entity.get();
        assert orderEntity.getCustomer().getCustomerName().equals("Test Customer");
        assert orderEntity.getCustomer().getEmail().equals("Test Email");
        assert orderEntity.getCustomer().getPhone().equals("Test Phone");
    }

    @Test
    @Transactional
    public void shouldDeleteOrder() throws Exception {
        mockMvc.perform(delete("/api/order/delete/" + orderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer.customerName").value("TestCustomer"))
                .andExpect(jsonPath("$.customer.email").value("TestEmail"))
                .andExpect(jsonPath("$.customer.phone").value("TestPhone"));

        Optional<Order> entity = orderRepository.findById(orderId);
        assert entity.isEmpty();
    }

}
