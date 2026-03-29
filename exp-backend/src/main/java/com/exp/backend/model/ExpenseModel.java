package com.exp.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "expenses")
public class ExpenseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private long expenseId;

    @Column(name = "expense_category")
    private String category;

    @Column(name = "expense_name")
    private String expenseName;

    @Column(name = "expense_amount")
    private long amount;

    @Column(name = "expense_created_time")
    private LocalDateTime localDateTime;

    @Column(name = "isActive")
    private boolean isActive = true;


    @Column(name = "user_id")
    private long userId;

    public ExpenseModel(){}
    public ExpenseModel(String category, String expenseName, long amount) {
        this.category = category;
        this.expenseName = expenseName;
        this.amount = amount;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ExpenseModel that)) return false;
        return expenseId == that.expenseId
                && amount == that.amount
                && isActive == that.isActive
                && Objects.equals(category, that.category)
                && Objects.equals(expenseName, that.expenseName)
                && Objects.equals(localDateTime, that.localDateTime)
                && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, category, expenseName, amount, localDateTime, isActive, userId);
    }

    @Override
    public String toString() {
        return "ExpenseModel{" +
                "expenseId=" + expenseId +
                ", category='" + category + '\'' +
                ", expenseName='" + expenseName + '\'' +
                ", amount=" + amount +
                ", localDateTime=" + localDateTime +
                ", isActive=" + isActive +
                ", userId='" + userId + '\'' +
                '}';
    }
}
