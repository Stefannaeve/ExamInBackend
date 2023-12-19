package com.example.examinbackend.controller;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Order;
import com.example.examinbackend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Optional<Order> optionalOrder;
        optionalOrder = orderService.getOrderById(id);
        return optionalOrder.map(order -> ResponseEntity.status(HttpStatus.OK).body(order)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("/all/{pageNumber}/{pageSize}")
    public List<Order> getAllOrdersPageable(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return orderService.getAllOrdersPageable(pageNumber, pageSize);
    }

    @PostMapping(value = "/add/customer/{customerId}", produces = "application/json")
    public ResponseEntity<Order> createOrder(@PathVariable Long customerId) {
        Optional<Order> optionalOrder;
        optionalOrder = orderService.createOrder(customerId);
        return optionalOrder.map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping(value = "/update/{orderId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Order> updateOrderMachines(@PathVariable Long orderId, @RequestBody List<Machine> machines) {
        Optional<Order> optionalOrder;
        optionalOrder = orderService.updateOrderMachines(machines, orderId);
        return optionalOrder.map(order -> ResponseEntity.status(HttpStatus.OK).body(order)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.deleteOrder(id);
        return optionalOrder.map(order -> ResponseEntity.status(HttpStatus.OK).body(order)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
