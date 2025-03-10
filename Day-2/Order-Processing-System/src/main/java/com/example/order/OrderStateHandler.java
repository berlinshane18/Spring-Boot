package com.example.order;

public class OrderStateHandler {

    public static OrderState getNextState(OrderState currentState, OrderEvent event) {
        switch (currentState) {
            case NEW:
                if (event == OrderEvent.PROCESS) return OrderState.PROCESSING;
                break;
            case PROCESSING:
                if (event == OrderEvent.SHIP) return OrderState.SHIPPED;
                break;
            case SHIPPED:
                if (event == OrderEvent.DELIVER) return OrderState.DELIVERED;
                break;
            case DELIVERED:
                if (event == OrderEvent.CANCEL) return OrderState.CANCELLED;
                break;
            default:
                throw new IllegalStateException("Invalid state transition from " + currentState);
        }
        throw new IllegalStateException("Event " + event + " not allowed from state " + currentState);
    }
}
