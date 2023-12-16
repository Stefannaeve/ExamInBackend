package com.example.examinbackend.OrderTests;

import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.example.examinbackend.repository.CustomerRepository;



@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        Customer customer = customerRepository.save(new Customer("Johnn Doe", "Email@email.email", "123"));
        Order order = new Order();
        orderRepository.save(new Order(customer));
    }

}
