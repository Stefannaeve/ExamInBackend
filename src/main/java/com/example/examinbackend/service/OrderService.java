package com.example.examinbackend.service;

import com.example.examinbackend.model.Customer;
import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Order;
import com.example.examinbackend.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final EntityManager entityManager;
    private final CustomerService customerService;
    private final MachineService machineService;

    @Autowired
    public OrderService(OrderRepository orderRepository, EntityManager entityManager, CustomerService customerService, MachineService machineService) {
        this.orderRepository = orderRepository;
        this.entityManager = entityManager;
        this.customerService = customerService;
        this.machineService = machineService;
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Optional<Order> createOrder(Long customerId) {
        Order order = new Order();
        Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);
        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }
        Customer customer = entityManager.merge(optionalCustomer.get());
        order.setCustomer(customer);
        return Optional.of(orderRepository.save(order));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public List<Order> getAllOrdersPageable(int pageNumber, int pageSize) {
        return orderRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
    }

    public Optional<Order> deleteOrder(Long id) {
        Optional<Order> optionalOrder = getOrderById(id);
        if (optionalOrder.isEmpty()) {
            return Optional.empty();
        }
        orderRepository.deleteById(id);
        return optionalOrder;
    }

    public Optional<Order> updateOrderMachines(List<Machine> machines, Long orderId) {
        Optional<Order> optionalOrder = getOrderById(orderId);
        for (Machine machine : machines) {
            Optional<Machine> optionalMachine = machineService.getMachineById(machine.getId());
            if (optionalMachine.isEmpty()) {
                return Optional.empty();
            }
            optionalOrder.get().getMachines().add(optionalMachine.get());
        }
        Order order = optionalOrder.get();
        System.out.println(order);
        orderRepository.deleteById(orderId);
        return Optional.of(orderRepository.save(order));
    }
}
