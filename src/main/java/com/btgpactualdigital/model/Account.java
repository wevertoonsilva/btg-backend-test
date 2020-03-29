package com.btgpactualdigital.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private Long accountId;
    private String userName;
    private BigDecimal balance;

    public Account() {
    }

    public Account(String userName, BigDecimal balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public Account(long accountId, String userName, BigDecimal balance) {
        this.accountId = accountId;
        this.userName = userName;
        this.balance = balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getUserName() {
        return userName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId.equals(account.accountId) &&
                userName.equals(account.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, userName);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
