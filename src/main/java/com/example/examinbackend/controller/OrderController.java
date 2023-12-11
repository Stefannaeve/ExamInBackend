package com.example.examinbackend.controller;

import com.example.examinbackend.model.Order;
import com.example.examinbackend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/id")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    @PostMapping("/add/customer/{customerId}")
    public Order createOrder(@RequestBody Order order, @PathVariable Long customerId) {
        return orderService.createOrder(order, customerId);
    }
    @PostMapping("/update/{orderId}/machines/{machineId}")
    public Order updateOrderMachines(@PathVariable Long orderId, @PathVariable Long machineId) {
        return orderService.updateOrderMachines(machineId, orderId);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
