package com.btgpactualdigital.repository;

import com.btgpactualdigital.exception.CustomException;
import com.btgpactualdigital.model.Account;
import com.btgpactualdigital.model.UserTransaction;

import java.math.BigDecimal;

public interface IAccountRepository {

    Account getAccountById(Long id);

    int transferAccountBalance(UserTransaction userTransaction) throws CustomException;

    int updateAccountBalance(long accountId, BigDecimal deltaAmount) throws CustomException;
}
