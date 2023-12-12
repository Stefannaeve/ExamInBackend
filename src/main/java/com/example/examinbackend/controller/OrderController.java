package com.example.examinbackend.controller;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Order;
import com.example.examinbackend.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@Slf4j
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
    public ResponseEntity<Order> createOrder(@PathVariable Long customerId) {
        Optional<Order> optionalOrder = orderService.createOrder(customerId);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalOrder.get());
        }
    }

    @PostMapping("/update/{orderId}")
    public Order updateOrderMachines(@PathVariable Long orderId, @RequestBody List<Machine> machines) {
        return orderService.updateOrderMachines(machines, orderId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
