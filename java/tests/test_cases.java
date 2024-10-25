import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class OrderAggregateTest {

    @Test
    void whenOrderLinesAdded_thenOrderTotalShouldReturnPrice() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        Order order = new Order(customerId);
        order.addLine(new Product("Refactoring by Kent Beck"), 1, 50.0);
        order.addLine(new Product("Designing Data-Intensive Applications: Martin Kleppmann"), 1, 50.0);
        order.addPayment(new Money(100.0));
        order.shipOrder();
    }

    @Test
    void whenOrderLineAdded_thenShipOrderShouldReturnCantShipUnpaidOrder() {
        Order order = new OrderBuilder().build();
        Exception ex = assertThrows(IllegalStateException.class, order::shipOrder);
        assertTrue(ex.getMessage().contains("Can't ship unpaid order"));
    }

    @Test
    void whenOrderLineAdded_thenShipOrderShouldReturnOrderIsInTransit() {
        Order order = new OrderBuilder().build();
        order.addPayment(new Money(70.0));
        order.addPayment(new Money(30.0));
        order.shipOrder();

        assertEquals(OrderContext.OrderStatus.IN_TRANSIT, order.getStatus());
    }

    @Test
    void whenAddingOrderPayment_thenPaidAmountShouldBeIncreased() {
        Order order = new OrderBuilder().build();
        order.addPayment(new Money(70.0));

        assertEquals(new Money(70.0), order.getPaidAmount());
    }

    @Test
    void whenAddingOrderPayment_thenPaidAmountShouldNotBeNegative() {
        Order order = new OrderBuilder().build();
        order.addPayment(new Money(70.0));

        Exception ex = assertThrows(IllegalStateException.class, () -> order.addPayment(new Money(100.0)));
        assertTrue(ex.getMessage().contains("Payment can't exceed order total"));
    }

    @Test
    void whenAddingOrderPayment_thenCantAddNegativeAmount() {
        Order order = new OrderBuilder().build();
        Exception ex = assertThrows(IllegalStateException.class, () -> order.addPayment(new Money(-10.0)));
        assertTrue(ex.getMessage().contains("Payment must be positive."));
    }

    @Test
    void whenAddedOrderLine_thenCantAddLineAfterPaymentMade() {
        Order order = new OrderBuilder().build();
        order.addPayment(new Money(100.0));

        Exception ex = assertThrows(IllegalStateException.class,
            () -> order.addLine(new Product("Refactoring by Kent Beck"), 1, 50.0));
        assertTrue(ex.getMessage().contains("Can't modify order, payment has been done."));
    }

    @Test
    void whenShippingOrder_thenCantShipAlreadyShipped() {
        Order order = new OrderBuilder().build();
        order.addPayment(new Money(100.0));
        order.shipOrder();
        Exception ex = assertThrows(IllegalStateException.class, order::shipOrder);
        assertEquals("Order already shipped to customer.", ex.getMessage());

        assertThrows(IllegalStateException.class, () -> order.addQuantity(new Product("Designing Data-Intensive Applications: Martin Kleppmann"), 1));
    }

    @Test
    void whenRemoveLine_thenItemRemoved() {
        // Given
        Order order = new Order(new CustomerId(UUID.randomUUID()));
        Product product1 = new Product("Product 1");
        Product product2 = new Product("Product 2");
        order.addLine(product1, 2, 10.0);
        order.addLine(product2, 3, 15.0);

        // When
        order.removeLine(product1);

        // Then
        assertEquals(1, order.getItems().size());
        assertFalse(order.getItems().contains(product1));
    }

    @Test
    void whenOrderTotalQuantityUpdated_thenTotalQuantityShouldBeUpdated() {
        // Given
        Order order = new Order(new CustomerId(UUID.randomUUID()));
        Product product1 = new Product("Product 1");
        Product product2 = new Product("Product 2");

        // When
        order.addLine(product1, 2, 10.0);
        order.addLine(product2, 3, 15.0);

        // Then
        assertEquals(5, order.getTotalQuantity());
    }

    @Test
    void whenAddingLineItemToOrderWithNonPendingPaymentStatus_thenThrowsInvalidOperationException() {
        // Given
        Order order = new Order(new CustomerId(UUID.randomUUID()));
        Product product = new Product("Product A");
        int quantity = 2;
        double unitPrice = 10.5;
        order.addLine(product, quantity, unitPrice);
        order.addPayment(new Money(quantity * unitPrice));

        // When, Then
        assertThrows(IllegalStateException.class, () -> order.addLine(product, quantity, unitPrice));
    }

    @Test
    void whenAddingPaymentWithNegativeAmount_thenThrowsException() {
        // Given
        Order order = new Order(new CustomerId(UUID.randomUUID()));
        Money paymentAmount = new Money(-100.0);

        // When, Then
        assertThrows(IllegalStateException.class, () -> order.addPayment(paymentAmount));
    }

    @Test
    void whenAddingPaymentExceedingOrderTotal_thenThrowsException() {
        // Given
        Order order = new Order(new CustomerId(UUID.randomUUID()));
        Money paymentAmount = new Money(100.0);
        order.addLine(new Product("Product 1"), 2, 10.0);

        // When, Then
        assertThrows(IllegalStateException.class, () -> order.addPayment(paymentAmount));
    }

    @Test
    void whenShipOrderWithoutItemLines_thenThrowsException() {
        // Given
        Order order = new Order(new CustomerId(UUID.randomUUID()));

        // When, Then
        assertThrows(IllegalStateException.class, order::shipOrder);
    }
}

class OrderBuilder {
    private CustomerId customerId = new CustomerId(UUID.randomUUID());

    public Order build() {
        Order order = new Order(customerId);
        order.addLine(new Product("Refactoring by Kent Beck"), 1, 50.0);
        order.addLine(new Product("Designing Data-Intensive Applications: Martin Kleppmann"), 1, 50.0);
        return order;
    }
}
