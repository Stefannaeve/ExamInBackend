package com.example.examinbackend.OrderTests;

import com.example.examinbackend.model.Customer;
import com.example.examinbackend.model.Order;
import com.example.examinbackend.repository.CustomerRepository;
import com.example.examinbackend.repository.OrderRepository;
import com.example.examinbackend.service.CustomerService;
import com.example.examinbackend.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceUnitTest {
    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    void shouldGetOrderById() {
        Customer customerOne = new Customer();
        Customer customerTwo = new Customer();
        Order orderOne = new Order(customerOne);
        Order orderTwo = new Order(customerTwo);
        when(orderRepository.findById(2L)).thenReturn(java.util.Optional.of(orderTwo));
        var orderById = orderService.getOrderById(2L);
        assert orderById.get().getCustomer().equals(customerTwo);
    }

    @Test
    void shouldCreateANewOrder() {
        Customer customer = new Customer();
        Order order = new Order(customer);
        when(orderRepository.save(order)).thenReturn(order);
        var newOrder = orderService.createOrder(1L);
        assert newOrder.get().getCustomer().equals(customer);
    }
    @Test
    void shouldGetAllOrders() {
        Customer customer = new Customer();
        Order order = new Order(customer);
        when(orderRepository.findAll()).thenReturn(List.of(order));
        var orders = orderService.getAllOrders();
        assert orders.size() == 1;
    }
    @Test
    void shouldDeleteANewOrder() {
        Customer customer = new Customer();
        Order order = new Order(customer);
        orderService.deleteOrder(1L);
        assert orderService.getAllOrders().size() == 0;
    }
    @Test
    void shouldUpdateOrderMachines() {
        Customer customer = new Customer();
        Order order = new Order(customer);
        orderService.updateOrderMachines(List.of(), 1L);
        assert order.getMachines().size() == 0;
    }

}
