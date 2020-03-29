package com.btgpactualdigital;

import com.btgpactualdigital.exception.CustomException;
import com.btgpactualdigital.model.UserTransaction;
import com.btgpactualdigital.repository.IAccountRepository;

import java.math.BigDecimal;

public class MoneyTransfer {

    private IAccountRepository accountRepository;

    public MoneyTransfer(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean transfer(Long origemAccountId, Long destinyAccountId, BigDecimal amount) {

        UserTransaction transaction = new UserTransaction(amount, origemAccountId, destinyAccountId);

        int updateCount = 0;

        try {
            updateCount = accountRepository.transferAccountBalance(transaction);
        } catch (CustomException e) {
            e.printStackTrace();
        }

        if (updateCount == 2) {
            return true;
        } else {
            // transaction failed
            return false;
        }
    }
}
