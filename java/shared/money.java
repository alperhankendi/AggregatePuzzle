package shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money implements Comparable<Money> {
public static final Money ZERO = new Money(BigDecimal.ZERO);
private final BigDecimal amount;

protected Money() {
    this.amount = BigDecimal.ZERO;
}

public Money(BigDecimal amount) {
    this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
}

public BigDecimal getAmount() {
    return amount;
}

public Money add(Money other) {
    return new Money(this.amount.add(other.amount));
}

public Money subtract(Money other) {
    return new Money(this.amount.subtract(other.amount));
}

// Operator overloading can be simulated with methods in Java
public static Money add(Money one, Money two) {
    return one.add(two);
}

public static Money subtract(Money one, Money two) {
    return one.subtract(two);
}

public static boolean greaterThan(Money one, Money two) {
    return one.compareTo(two) > 0;
}

public static boolean lessThan(Money one, Money two) {
    return one.compareTo(two) < 0;
}

public static boolean lessThanOrEqual(Money one, Money two) {
    return one.compareTo(two) <= 0;
}

public static boolean greaterThanOrEqual(Money one, Money two) {
    return one.compareTo(two) >= 0;
}

@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Money money = (Money) obj;
    return amount.compareTo(money.amount) == 0;
}

@Override
public int hashCode() {
    return Objects.hash(amount);
}

@Override
public int compareTo(Money other) {
    return amount.compareTo(other.amount);
}

@Override
public String toString() {
    return "Money [Amount=" + amount + "]";
}
}