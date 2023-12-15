namespace TestProject1;


public class OrderAggregateTest
{

    public OrderAggregateTest()
    {
    }
    [Fact]
    public void When_OrderLinesAdded_Then_OrderTotalShouldReturnsPrice()
    {
        var customerId = new CustomerId(Guid.NewGuid());
        var order = new Order(customerId);
        order.AddLine(new Product("Refactoring by Kent Beck"), 1, 50M);
        order.AddLine(new Product("Designing Data-Intensive Applications: Martin Kleppmann"), 1, 50M);
        order.AddPayment(new Money(100M));
        order.ShipOrder();
    }

    [Fact]
    public void When_OrderLineAdded_Than_ShipOrderShouldReturnCantShipUnpaidOrder()
    {
        var order = new OrderBuilder().Build();
        var ex = Assert.Throws<InvalidOperationException>(() => order.ShipOrder());
        ex.Message.Should().Contain("Cant ship order unpaid order");
    }

    [Fact]
    public void When_OrderLineAdded_Than_ShipOrderShouldReturnOrderIsInTransit()
    {
        var order = new OrderBuilder().Build();
        order.AddPayment(new Money(70M));
        order.AddPayment(new Money(30m));
        order.ShipOrder();

        order.Status.Should().Be(OrderContext.OrderStatus.InTransit);
    }

    [Fact]
    public void When_AddingOrderPayment_Than_PaidAmountShouldBeIncreased()
    {
        var order = new OrderBuilder().Build();
        order.AddPayment(new Money(70M));

        order.PaidAmount.Should().Be(new Money(70M));
    }
    [Fact]
    public void When_AddingOrderPayment_Than_PaidAmountShouldNotBeNegative()
    {
        var order = new OrderBuilder().Build();
        order.AddPayment(new Money(70M));

        var ex = Assert.Throws<InvalidOperationException>(() => order.AddPayment(new Money(100M)));
        ex.Message.Should().Contain("Payment can't exceed order total");
    }
    [Fact]
    public void When_AddingOrderPayment_Than_CantAddNegativeAmount()
    {
        var order = new OrderBuilder().Build();
        var ex = Assert.Throws<InvalidOperationException>(() => order.AddPayment(new Money(-10M)));
        ex.Message.Should().Contain("Payment must be positive.");
    }

    [Fact]
    public void When_AddedOrderLine_Than_CantAddLineAfterPaymentMade()
    {
        var order = new OrderBuilder().Build();
        order.AddPayment(new Money(100M));

        var ex = Assert.Throws<InvalidOperationException>(
            () => order.AddLine(new Product("Refactoring by Kent Beck"), 1, 50M));
        ex.Message.Should().Contain("Cant modify order, payment has been done.");
    }
    [Fact]
    public void When_ShippingOrder_Than_CantShipAlreadyShipped()
    {
        var order = new OrderBuilder().Build();
        order.AddPayment(new Money(100M));
        order.ShipOrder();
        var ex = Assert.Throws<InvalidOperationException>(() => order.ShipOrder());
        ex.Message.Should().Be("Order already shipped to customer.");
        
        Assert.Throws<InvalidOperationException>(() =>  order.AddQuantity(new Product("Designing Data-Intensive Applications: Martin Kleppmann"), 1));
    }

    [Fact]
    public void When_RemoveLine_Than_ItemRemoved()
    {
        // Given
        var order = new Order(new CustomerId(Guid.NewGuid()));
        var product1 = new Product("Product 1");
        var product2 = new Product("Product 2");
        order.AddLine(product1, 2, 10.0m);
        order.AddLine(product2, 3, 15.0m);

        // When
        order.RemoveLine(product1);

        // Then
        Assert.Equal(1, order.Items.Count);
        Assert.DoesNotContain(order.Items, x => x.Product == product1);
    }
    [Fact]
    public void When_OrderTotalQuantityUpdated_Than_TotalQuantityShouldBeUpdated()
    {
        // Given
        var order = new Order(new CustomerId(Guid.NewGuid()));
        var product1 = new Product("Product 1");
        var product2 = new Product("Product 2");

        // When
        order.AddLine(product1, 2, 10.0m);
        order.AddLine(product2, 3, 15.0m);

        // Then
        Assert.Equal(5, order.TotalQuantity);
    }
    [Fact]
    public void When_AddingLineItemToOrderWithNonPendingPaymentStatus_Than_ThrowsInvalidOperationException()
    {
        // Given
        var customerId = Guid.NewGuid();
        var order = new Order(new CustomerId(Guid.NewGuid()));
        var product = new Product("Product A");
        uint quantity = 2;
        decimal unitPrice = 10.5m;
        order.AddLine(product, quantity, unitPrice);
        order.AddPayment(new Money(quantity * unitPrice));

        // When, Then
        Assert.Throws<InvalidOperationException>(() => order.AddLine(product, quantity, unitPrice));
    }

    [Fact]
    public void When_Adding_payment_with_negative_amount_Than_throws_exception()
    {
        // Given
        Guid customerId = Guid.NewGuid();
        var order = new Order(new CustomerId(Guid.NewGuid()));
        Money paymentAmount = new Money(-100);

        // When, Then
        Assert.Throws<InvalidOperationException>(() => order.AddPayment(paymentAmount));
    }
    [Fact]
    public void When_Adding_payment_exceeding_order_total_Than_Throws_exception()
    {
        // Given
        Guid customerId = Guid.NewGuid();
        var order = new Order(new CustomerId(Guid.NewGuid()));
        Money paymentAmount = new Money(100);
        order.AddLine(new Product("Product 1"), 2, 10);

        // When, Then
        Assert.Throws<InvalidOperationException>(() => order.AddPayment(paymentAmount));
    }
    [Fact]
    public void When_ShipOrderWithout_item_lines_Than_Throws_exception()
    {
        // Given
        Guid customerId = Guid.NewGuid();
        var order = new Order(new CustomerId(Guid.NewGuid()));

        // When, Then
        Assert.Throws<InvalidOperationException>(() => order.ShipOrder());
    }
}
internal class OrderBuilder
{
    private CustomerId customerId = new CustomerId(Guid.NewGuid());
    public Order Build()
    {
        var order = new Order(customerId);
        order.AddLine(new Product("Refactoring by Kent Beck"),1,50M);
        order.AddLine(new Product("Designing Data-Intensive Applications: Martin Kleppmann"), 1,50M);

        return order;
    }
}