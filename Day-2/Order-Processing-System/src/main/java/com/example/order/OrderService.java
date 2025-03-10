package com.example.order;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {
    private final Map<Long, Order> orders = new HashMap<>();

    public Order createOrder(Long id, String description) {
        Order order = new Order(id, description);
        orders.put(id, order);
        return order;
    }

    public Order processOrder(Long orderId, OrderEvent event) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found.");
        }

        // Validate and update state
        OrderState nextState = OrderStateHandler.getNextState(order.getState(), event);
        order.setState(nextState);

        return order;
    }

    public Order getOrder(Long orderId) {
        return orders.get(orderId);
    }
}
