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

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
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
    public List<Order> getAllOrdersPageable(int pageNumber, int pageSize) {
        return orderRepository.findAll().subList(pageNumber, pageSize);
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
        return Optional.of(orderRepository.save(optionalOrder.get()));
    }
}
