package shared;

import java.util.Arrays;
import java.util.Objects;

public abstract class ValueObject<T extends ValueObject<T>> {
protected abstract Iterable<Object> getAttributesToIncludeInEqualityCheck();

@Override
public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;
    T that = (T) other;
    return Arrays.equals(getAttributesToIncludeInEqualityCheck(), that.getAttributesToIncludeInEqualityCheck());
}

public static <T extends ValueObject<T>> boolean equals(ValueObject<T> left, ValueObject<T> right) {
    return Objects.equals(left, right);
}

@Override
public int hashCode() {
    int hash = 13;
    int i = 1;
    for (Object obj : getAttributesToIncludeInEqualityCheck()) {
        hash += (31 ^ i) * (obj == null ? 1 : obj.hashCode());
        i++;
    }
    return hash;
}
}