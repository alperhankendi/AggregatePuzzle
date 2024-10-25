namespace Domain;

public class OrderLine
{
    public long OrderLineId { get; set; }
    public Order Order { get; set; }
    public string Name { get; set; }
    public int Quantity { get; set; }
    public decimal UnitPrice { get; set; }
}

package domain;

public class OrderLine {
    private long orderLineId;
    private Order order;
    private String name;
    private int quantity;
    private double unitPrice; // Using double for monetary values; consider BigDecimal for accuracy

    // Getters and Setters
    public long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void
