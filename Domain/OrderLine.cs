namespace Domain;

public class OrderLine
{
    public long OrderLineId { get; set; }
    public Order Order { get; set; }
    public string Name { get; set; }
    public int Quantity { get; set; }
    public decimal UnitPrice { get; set; }
}