package com.btgpactualdigital;

import com.btgpactualdigital.exception.CustomException;
import com.btgpactualdigital.model.Account;
import com.btgpactualdigital.repository.AccountsBase;
import com.btgpactualdigital.repository.IAccountRepository;
import com.btgpactualdigital.repository.impl.AccountRepositoryImpl;
import sun.util.resources.cldr.en.CurrencyNames_en;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        AccountsBase accountsBase = new AccountsBase();
        accountsBase.getAccounts().add(new Account(1, "Weverton", new BigDecimal(1000000)));
        accountsBase.getAccounts().add(new Account(2, "Alessandra", new BigDecimal(0)));

        IAccountRepository accountRepository = new AccountRepositoryImpl(accountsBase);

        MoneyTransfer moneyTransfer = new MoneyTransfer(accountRepository);

        moneyTransfer.transfer(1L, 2L, new BigDecimal(100000));

        accountsBase.getAccounts().forEach(a -> logger.log(Level.INFO, a.toString()));
    }
}
