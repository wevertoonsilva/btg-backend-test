package com.btgpactualdigital.repository;

import com.btgpactualdigital.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountsBase {

    public List<Account> accounts;

    public AccountsBase() {
        accounts = new ArrayList<>(0);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void populateTestData() {
        accounts.add(new Account(1, "Weverton", new BigDecimal(1000000.0)));
        accounts.add(new Account(2, "Alessandra", new BigDecimal(0.0)));
        accounts.add(new Account(3, "Júlio César", new BigDecimal(100.0)));
        accounts.add(new Account(4, "Mateus", new BigDecimal(500.0)));
        accounts.add(new Account(5, "Higor", new BigDecimal(500.0)));
    }
}
