package com.btgpactualdigital;

import com.btgpactualdigital.exception.CustomException;
import com.btgpactualdigital.model.Account;
import com.btgpactualdigital.model.UserTransaction;
import com.btgpactualdigital.repository.AccountsBase;
import com.btgpactualdigital.repository.IAccountRepository;
import com.btgpactualdigital.repository.impl.AccountRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTransferTest {

    private final static Logger logger = Logger.getLogger(MoneyTransferTest.class.getName());
    private static MoneyTransfer moneyTransfer;

    @BeforeAll
    public static void setup() {
        AccountsBase accountsBase = new AccountsBase();
        accountsBase.populateTestData();

        moneyTransfer = new MoneyTransfer(new AccountRepositoryImpl(accountsBase));
    }

    @Test
    public void testAccountTransfer() throws CustomException {

        BigDecimal transferAmount = new BigDecimal(50.01234).setScale(4, RoundingMode.HALF_EVEN);

        assertTrue(moneyTransfer.transfer(4L, 5L, transferAmount));
        assertFalse(moneyTransfer.transfer(7L, 5L, transferAmount));
    }
}
