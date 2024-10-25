package shared;

import java.util.Objects;

public abstract class IdentityBase<T> {
    protected T id;

    protected IdentityBase() { }

    public IdentityBase(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) return true;
        if (anotherObject == null || getClass() != anotherObject.getClass()) return false;
        IdentityBase<?> that = (IdentityBase<?>) anotherObject;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass().hashCode(), id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [Id=" + id + "]";
    }
}