package com.example.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;


@Entity(name = "account")
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Column(name = "CurrencyCode")
    private String currencyCode;
    @Column(name = "Balance")
    private BigDecimal balance;

    public AccountEntity() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity accountEntity = (AccountEntity) o;
        return Objects.equals(id, accountEntity.id) &&
                Objects.equals(currencyCode, accountEntity.currencyCode) &&
                Objects.equals(balance, accountEntity.balance);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, currencyCode, balance);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountEntity{");
        sb.append("id=").append(id);
        sb.append(", currencyCode='").append(currencyCode).append('\'');
        sb.append(", balance='").append(balance).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
