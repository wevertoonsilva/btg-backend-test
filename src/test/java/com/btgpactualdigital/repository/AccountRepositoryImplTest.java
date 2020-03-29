package com.btgpactualdigital.repository;

import com.btgpactualdigital.exception.CustomException;
import com.btgpactualdigital.model.Account;
import com.btgpactualdigital.model.UserTransaction;
import com.btgpactualdigital.repository.impl.AccountRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryImplTest {

    private static IAccountRepository accountRepository;

    @BeforeAll
    public static void setup() {
        AccountsBase accountsBase = new AccountsBase();
        accountsBase.populateTestData();

        accountRepository = new AccountRepositoryImpl(accountsBase);
    }

    @Test
    public void testGetAccountById() {
        Account account = accountRepository.getAccountById(1L);
        assertTrue(account.getUserName().equals("Weverton"));
    }

    @Test
    public void testUpdateAccountBalanceSufficientFund() throws CustomException {

        BigDecimal deltaDeposit = new BigDecimal(50).setScale(4, RoundingMode.HALF_EVEN);
        BigDecimal afterDeposit = new BigDecimal(150).setScale(4, RoundingMode.HALF_EVEN);
        int rowsUpdated = accountRepository.updateAccountBalance(3L, deltaDeposit);
        assertTrue(rowsUpdated == 1);
        assertTrue(accountRepository.getAccountById(3L).getBalance().equals(afterDeposit));

        BigDecimal deltaWithDraw = new BigDecimal(-50).setScale(4, RoundingMode.HALF_EVEN);
        BigDecimal afterWithDraw = new BigDecimal(100).setScale(4, RoundingMode.HALF_EVEN);
        int rowsUpdatedW = accountRepository.updateAccountBalance(3L, deltaWithDraw);
        assertTrue(rowsUpdatedW == 1);
        assertTrue(accountRepository.getAccountById(3L).getBalance().equals(afterWithDraw));
    }

    @Test
    public void testUpdateAccountBalanceNotEnoughFund() {

        AtomicInteger rowsUpdatedW = new AtomicInteger();

        Exception exception = assertThrows(CustomException.class, () -> {
            BigDecimal deltaWithDraw = new BigDecimal(-50000).setScale(4, RoundingMode.HALF_EVEN);
            rowsUpdatedW.set(accountRepository.updateAccountBalance(2L, deltaWithDraw));
        });

        assertTrue(rowsUpdatedW.get() == 0);
    }

    @Test
    public void testAccountTransfer() throws CustomException {

        BigDecimal transferAmount = new BigDecimal(50.01234).setScale(4, RoundingMode.HALF_EVEN);

        UserTransaction transaction = new UserTransaction(transferAmount, 4L, 5L);

        accountRepository.transferAccountBalance(transaction);

        Account accountFrom = accountRepository.getAccountById(4L);

        Account accountTo = accountRepository.getAccountById(5L);

        assertTrue(accountFrom.getBalance().compareTo(new BigDecimal(449.9877).setScale(4, RoundingMode.HALF_EVEN)) == 0);
        assertTrue(accountTo.getBalance().equals(new BigDecimal(550.0123).setScale(4, RoundingMode.HALF_EVEN)));
    }
}
