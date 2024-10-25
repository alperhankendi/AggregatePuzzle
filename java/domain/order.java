package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public enum OrderStatus {
    NEW, PENDING_PAYMENT, READY_FOR_SHIPPING, IN_TRANSIT, DELIVERED
}

public class Order {
    private UUID customerId;
    private long orderId;
    private LocalDateTime creationDate;
    private double paidAmount; // Using double for monetary values; consider BigDecimal for accuracy
    private OrderStatus status;
    private Collection<OrderLine> items = new ArrayList<>();

    // Getters and Setters
    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Collection<OrderLine> getItems() {
        return items;
    }

    public void setItems(Collection<OrderLine> items) {
        this.items = items;
    }
}

class CustomerId {
    private UUID id;

    public CustomerId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}