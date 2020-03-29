package com.btgpactualdigital.repository.impl;

import com.btgpactualdigital.exception.CustomException;
import com.btgpactualdigital.model.Account;
import com.btgpactualdigital.model.UserTransaction;
import com.btgpactualdigital.repository.AccountsBase;
import com.btgpactualdigital.repository.IAccountRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountRepositoryImpl implements IAccountRepository {

    private final static Logger logger = Logger.getLogger(AccountRepositoryImpl.class.getName());
    private AccountsBase accountsBase;

    public AccountRepositoryImpl(AccountsBase accountsBase) {
        this.accountsBase = accountsBase;
    }

    public Account getAccountById(Long id) {
        return accountsBase.getAccounts().stream()
                .filter(account -> id.equals(account.getAccountId()))
                .findAny()
                .orElse(null);
    }

    /**
     * Transfer balance between two accounts.
     */
    public int transferAccountBalance(UserTransaction userTransaction) throws CustomException {

        int result = 0;

        Account fromAccount;
        Account toAccount;

        try {
            fromAccount = this.getAccountById(userTransaction.getFromAccountId());
            logger.log(Level.INFO, "transferAccountBalance from Account: " + fromAccount);

            toAccount = this.getAccountById(userTransaction.getToAccountId());
            logger.log(Level.INFO, "transferAccountBalance to Account: " + toAccount);

            if (fromAccount == null || toAccount == null) {
                throw new CustomException("Fail to get accounts for transfer");
            }

            BigDecimal fromAccountLeftOver = fromAccount.getBalance().subtract(userTransaction.getAmount());
            if (fromAccountLeftOver.compareTo(new BigDecimal(0).setScale(4, RoundingMode.HALF_EVEN)) < 0) {
                throw new CustomException("Not enough Fund from source Account ");
            }

            result += updateAccountBalance(fromAccount.getAccountId(), userTransaction.getAmount().negate());
            result += updateAccountBalance(toAccount.getAccountId(), userTransaction.getAmount());
        } catch (Exception se) {
            // rollback transaction if exception occurs
            logger.log(Level.INFO, "transferAccountBalance(): User Transaction Failed, rollback initiated for: " + userTransaction,
                    se);
        }

        return result;
    }

    public int updateAccountBalance(long accountId, BigDecimal deltaAmount) throws CustomException {

        Account targetAccount;

        targetAccount = this.getAccountById(accountId);

        logger.log(Level.INFO, "updateAccountBalance from Account: " + targetAccount);

        if (targetAccount == null) {
            throw new CustomException("updateAccountBalance(): fail to lock account : " + accountId);
        }

        BigDecimal balance = targetAccount.getBalance().add(deltaAmount);
        if (balance.compareTo(new BigDecimal(0).setScale(4, RoundingMode.HALF_EVEN)) < 0) {
            throw new CustomException("Not sufficient Fund for account: " + accountId);
        }

        targetAccount.setBalance(targetAccount.getBalance().add(deltaAmount));

        return 1;
    }
}
