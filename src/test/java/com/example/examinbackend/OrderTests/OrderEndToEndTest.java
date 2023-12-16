package com.example.examinbackend.OrderTests;

import com.example.examinbackend.model.*;
import com.example.examinbackend.repository.*;
import com.example.examinbackend.service.CustomerService;
import com.example.examinbackend.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
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

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SubassemblyRepository subassemblyRepository;

    @Autowired
    private MachineRepository machineRepository;

    private Long orderId;

    @BeforeEach
    public void setUp() {
        Customer customer = customerRepository.save(new Customer("Johnn Doe", "Email@email.email", "123"));
        orderId = orderService.createOrder(customer.getId()).get().getId();
    }



    @Test
    @Transactional
    public void shouldGetAllOrders() throws Exception {
        Customer customer = new Customer("Johnny Big Boi", "Johnny@big.boi", "BIG BOI's NUMBER");
        orderRepository.save(new Order(customer));

        mockMvc.perform(get("/api/order/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].customer.customerName").value("Johnn Doe"))
                .andExpect(jsonPath("$[0].customer.email").value("Email@email.email"))
                .andExpect(jsonPath("$[0].customer.phone").value("123"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].customer.customerName").value("Johnny Big Boi"))
                .andExpect(jsonPath("$[1].customer.email").value("Johnny@big.boi"))
                .andExpect(jsonPath("$[1].customer.phone").value("BIG BOI's NUMBER"));
    }

    @Test
    @Transactional
    public void shouldGetOrderById() throws Exception {
        mockMvc.perform(get("/api/order/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customer.customerName").value("Johnn Doe"))
                .andExpect(jsonPath("$.customer.email").value("Email@email.email"))
                .andExpect(jsonPath("$.customer.phone").value("123"));
    }

    @Test
    @Transactional
    public void ShouldAddCustomerToOrderAndCreateOrder() throws Exception {

        Customer customer = customerRepository.save(new Customer("James", "Small boie", "123"));

        mockMvc.perform(post("/api/order/add/customer/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerName\":\"James\", \"email\":\"Small boie\", \"phone\":\"123\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.customer.customerName").value("James"))
                .andExpect(jsonPath("$.customer.email").value("Small boie"))
                .andExpect(jsonPath("$.customer.phone").value("123"));
    }

    @Test
    @Transactional
    public void shouldUpdateOrderWithMachines() throws Exception {
        List<Part> part = List.of(partRepository.save(new Part("Part1", 100L)));
        List<Subassembly> subassembly = List.of(subassemblyRepository.save(new Subassembly("Subassembly1", part)));
        List<Machine> machine = List.of(machineRepository.save(new Machine("MachineName", subassembly)));

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        Long machineId = machine.get(0).getId();
        List<ObjectNode> jsonListOfIds = List.of(jsonNode.put("id", machineId));
        System.out.println(jsonListOfIds);

        mockMvc.perform(put("/api/order/update/" + orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonListOfIds.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.machines[0].machineName").value("MachineName"))
                .andExpect(jsonPath("$.machines[0].subassemblies[0].subassemblyName").value("Subassembly1"))
                .andExpect(jsonPath("$.machines[0].subassemblies[0].parts[0].partName").value("Part1"))
                .andExpect(jsonPath("$.machines[0].subassemblies[0].parts[0].partPrice").value(100));
    }


}
