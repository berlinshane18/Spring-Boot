package com.example.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestParam Long id, @RequestParam String description) {
        Order order = orderService.createOrder(id, description);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}/transition")
    public ResponseEntity<Order> transitionOrder(@PathVariable Long id, @RequestParam OrderEvent event) {
        Order updatedOrder = orderService.processOrder(id, event);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }
}
