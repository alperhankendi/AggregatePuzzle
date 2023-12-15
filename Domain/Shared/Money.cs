namespace Shared;

public class Money : ValueObject<Money> ,IComparable<Money>
{
    public decimal Amount { get; }
    public static readonly Money Zero= new Money(0M);
    protected Money() {}
    public Money(decimal amount)
    {
        Amount = decimal.Round(amount, 2, MidpointRounding.ToEven);
    }
    public Money Add(Money other) => new Money(this.Amount + other.Amount);
    public Money Subtract(Money other) => new Money(this.Amount - other.Amount);
    public static Money operator +(Money one, Money two) => one.Add(two);
    public static Money operator -(Money one, Money two) => one.Subtract(two);
    public static bool operator >(Money one, Money two) => one.CompareTo(two) > 0;
    public static bool operator <(Money one, Money two) => one.CompareTo(two) < 0;
    public static bool operator <=(Money one, Money two) => one.CompareTo(two) <= 0;
    public static bool operator >=(Money one, Money two) => one.CompareTo(two) >= 0;
    protected override IEnumerable<object> GetAttributesToIncludeInEqualityCheck() {
        yield return Amount;
    }
    public int CompareTo(Money other) => Amount.CompareTo(other.Amount);
}