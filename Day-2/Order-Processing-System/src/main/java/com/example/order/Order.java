package com.example.order;

public class Order {
    private Long id;
    private OrderState state;
    private String description;

    public Order(Long id, String description) {
        this.id = id;
        this.description = description;
        this.state = OrderState.NEW; // Default state
    }

    public Long getId() { return id; }
    public OrderState getState() { return state; }
    public void setState(OrderState state) { this.state = state; }
    public String getDescription() { return description; }
}
