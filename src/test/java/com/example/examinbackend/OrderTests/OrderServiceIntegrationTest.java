package com.example.examinbackend.OrderTests;

import com.example.examinbackend.model.Customer;
import com.example.examinbackend.model.Order;
import com.example.examinbackend.repository.OrderRepository;
import com.example.examinbackend.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceIntegrationTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    void shouldGetOrderById(){
        Order orderOne = new Order();
        Order orderTwo = new Order();
        Order savedOrder = orderRepository.save(orderTwo);
        var foundOrder = orderService.getOrderById(savedOrder.getId());
        assert foundOrder.get().getId().equals(savedOrder.getId());
    }
    @Test
    @Transactional
    void shouldCreateANewOrder(){
        Customer customer = new Customer("Customer 1", "Customer Address", "Customer Email");
        Order order = new Order(customer);
        Order savedOrder = orderRepository.save(order);
        order.setCustomer(customer);
        assert savedOrder.getCustomer().equals(customer);
    }
    @Test
    @Transactional
    void shouldGetAllOrders(){
        Order order = new Order();
        orderRepository.save(order);
        var orders = orderService.getAllOrders();
        assert orders.size() == 1;
    }
    @Test
    @Transactional
    void shouldDeleteANewOrder(){
        Order order = new Order();
        Order savedOrder = orderRepository.save(order);
        orderService.deleteOrder(savedOrder.getId());
        assert orderService.getAllOrders().size() == 0;
    }
}
