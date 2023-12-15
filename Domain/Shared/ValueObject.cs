namespace Shared;

public abstract class ValueObject<T> where T : ValueObject<T>
{
    protected abstract IEnumerable<object> GetAttributesToIncludeInEqualityCheck();
    public override bool Equals(object other) => Equals(other as T);
    public bool Equals(T other)
    {
        if (other == null) 
            return false;

        return GetAttributesToIncludeInEqualityCheck().SequenceEqual(other.GetAttributesToIncludeInEqualityCheck());
    }
    public static bool operator ==(ValueObject<T> left, ValueObject<T>? right)
    {
        return Equals(left, right);
    }
    public static bool operator !=(ValueObject<T> left, ValueObject<T> right)=> !(left == right);

    public override int GetHashCode()
    {
        var hash = 13;
        var i = 1;
        foreach (var obj in this.GetAttributesToIncludeInEqualityCheck())
        {
            hash +=  (31 ^ i) * (obj == null ? 1 : obj.GetHashCode());
            i++;
        }
        return hash;
    }
}