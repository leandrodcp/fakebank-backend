package com.fakebank.backend.transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Transaction {

    public static final int WITHDRAWAL = 0;
    public static final int DEPOSIT = 1;

    @Id
    @GeneratedValue
    private Long id;

    private Integer type;

    @Column(nullable = false)
    private BigDecimal amount = new BigDecimal("0.00");

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    public Transaction(){
    }

    public Transaction(Integer type, BigDecimal amount, Long accountId) {
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(amount, that.amount) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, amount, accountId);
    }
}
