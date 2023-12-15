namespace Shared;

public abstract class Entity<T>
{
    public T Id { get; protected set; }
}

public abstract class AggregateRoot<T> : Entity<T>
{
    
}