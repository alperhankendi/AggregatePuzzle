namespace Domain;

public enum OrderStatus { New,PendingPayment, ReadyForShipping, InTransit,Delivered }
public class Order
{
    public Guid CustomerId { get; set; }
    public long OrderId { get; set; }
    public DateTime CreationDate { get; set; }
    public decimal PaidAmount { get; set; } 
    public OrderStatus Status { get; set; }
    public ICollection<OrderLine> Items { get; set; } = new List<OrderLine>();
} 