package com.example.examinbackend.service;

import com.example.examinbackend.model.Customer;
import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Order;
import com.example.examinbackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Order> createOrder(Long customerId) {
        Order order = new Order();
        Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);
        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }
        order.setCustomer(optionalCustomer.get());
        return Optional.of(orderRepository.save(order));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrderMachines(List<Machine> machines, Long orderId) {
        Order order = getOrderById(orderId);
        for (Machine machine : machines) {
            machine = machineService.getMachineById(machine.getId());
            order.getMachines().add(machine);
        }
        return orderRepository.save(order);
    }
}
