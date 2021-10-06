package com.fakebank.backend;


import com.fakebank.backend.account.Account;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountTest {

    @Test
    public void withdrawal() {
        var account = new Account();
        account.setAmount(new BigDecimal("1.00"));
        account.withdrawal(new BigDecimal("1.00"));
        Assert.assertEquals(new BigDecimal("0.00"), account.getAmount());
    }

    @Test
    public void deposit() {
        var account = new Account();
        account.setAmount(new BigDecimal("1.00"));
        account.deposit(new BigDecimal("1.00"));
        Assert.assertEquals(new BigDecimal("2.00"), account.getAmount());
    }

    @Test
    public void transferTo() {
        var from = new Account();
        from.setAmount(new BigDecimal("1.00"));
        var to = new Account();
        to.setAmount(new BigDecimal("1.00"));
        from.transferTo(to, new BigDecimal("1.00"));
        Assert.assertEquals(new BigDecimal("0.00"), from.getAmount());
        Assert.assertEquals(new BigDecimal("2.00"), to.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferToException() {
        var from = new Account();
        from.setAmount(new BigDecimal("1.00"));
        var to = new Account();
        to.setAmount(new BigDecimal("1.00"));
        from.transferTo(to, new BigDecimal("2.00"));
    }
}
