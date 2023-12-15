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
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
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
        when(orderRepository.findById(2L)).thenReturn(Optional.of(orderTwo));
        var orderById = orderService.getOrderById(2L);
        assert orderById.get().getCustomer().equals(customerTwo);
    }

    @Test
    void shouldCreateANewOrder() {
        Customer customer = new Customer("Customer 1", "Customer Address", "Customer Email");
        Order order = new Order(customer);
        when(orderRepository.save(order)).thenReturn(order);
        order.setCustomer(customer);
        assert order.getCustomer().equals(customer);
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
    void shouldUpdateOrderById() {
        Customer customer = new Customer();
        Order order = new Order(customer);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        var updatedOrder = orderService.getOrderById(1L);
        updatedOrder.get().setId(2L);
        assert updatedOrder.get().getId() == 2L;
    }
}
