package shared;

public abstract class Entity<T> {
    protected T id;

    public T getId() {
        return id;
    }

    protected void setId(T id) {
        this.id = id;
    }
}

public abstract class AggregateRoot<T> extends Entity<T> {
    // Additional behavior for AggregateRoots can be added here
}