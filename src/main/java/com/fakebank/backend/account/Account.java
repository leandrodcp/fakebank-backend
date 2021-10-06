package com.fakebank.backend.account;

import com.fakebank.backend.transaction.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount = new BigDecimal("0.00");

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @OneToMany(mappedBy="accountId", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    /**
     * @throws IllegalArgumentException if amount to transfer is greater than amount in account
     */
    public void transferTo(Account to, BigDecimal amount) {
        if (this.amount.compareTo(amount) < 0) {
            throw new IllegalArgumentException("amount to transfer is greater than amount in account");
        }
        withdrawal(amount);
        to.deposit(amount);
    }

    public void withdrawal(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(amount, account.amount) && Objects.equals(customerId, account.customerId) && Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, customerId, transactions);
    }
}
