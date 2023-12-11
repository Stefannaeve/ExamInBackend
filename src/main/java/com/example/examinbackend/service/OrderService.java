package com.example.examinbackend.service;

import com.example.examinbackend.model.Customer;
import com.example.examinbackend.model.Order;
import com.example.examinbackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final MachineService machineService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerService customerService, MachineService machineService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.machineService = machineService;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    public Order createOrder(Order order, Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        order.setCustomer(customer);
        return orderRepository.save(order);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrderMachines(Long machineId, Long orderId) {
        Order order = getOrderById(orderId);
        order.getMachines().add(machineService.getMachineById(machineId));
        return orderRepository.save(order);
    }
}
