package com.btgpactualdigital.model;

import java.math.BigDecimal;
import java.util.Objects;

public class UserTransaction {

    private BigDecimal amount;
    private Long fromAccountId;
    private Long toAccountId;

    public UserTransaction() {
    }

    public UserTransaction(BigDecimal amount, Long fromAccountId, Long toAccountId) {
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTransaction that = (UserTransaction) o;
        return amount.equals(that.amount) &&
                fromAccountId.equals(that.fromAccountId) &&
                toAccountId.equals(that.toAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, fromAccountId, toAccountId);
    }

    @Override
    public String toString() {
        return "UserTransaction{" +
                "amount=" + amount +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                '}';
    }
}
