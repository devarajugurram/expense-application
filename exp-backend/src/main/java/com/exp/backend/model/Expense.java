package com.exp.backend.model;

import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.Objects;

public class Expense {
    private long expenseId;
    private String category;
    private String expenseName;
    private long amount;
    private LocalDateTime localDateTime;

    public Expense() {}

    public Expense(long expenseId, String category, String expenseName, long amount, LocalDateTime localDateTime) {
        this.expenseId = expenseId;
        this.category = category;
        this.expenseName = expenseName;
        this.amount = amount;
        this.localDateTime = localDateTime;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Expense expense)) return false;
        return expenseId == expense.expenseId
                && amount == expense.amount
                && Objects.equals(category, expense.category)
                && Objects.equals(expenseName, expense.expenseName)
                && Objects.equals(localDateTime, expense.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, category, expenseName, amount, localDateTime);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", category='" + category + '\'' +
                ", expenseName='" + expenseName + '\'' +
                ", amount=" + amount +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
