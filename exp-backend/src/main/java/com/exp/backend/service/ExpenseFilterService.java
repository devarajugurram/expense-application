package com.exp.backend.service;

import com.exp.backend.model.Expense;
import com.exp.backend.repo.ExpenseRepository;
import com.exp.backend.template.helpers.ExpenseModelConvertedToExpense;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseFilterService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseModelConvertedToExpense expenseModelConvertedToExpense;

    public ExpenseFilterService(ExpenseRepository expenseRepository,
                                ExpenseModelConvertedToExpense expenseModelConvertedToExpense) {
        this.expenseRepository = expenseRepository;
        this.expenseModelConvertedToExpense = expenseModelConvertedToExpense;
    }

    public ResponseEntity<List<Expense>> categoryBasedRequest(long id) {
        List<Expense> expenses = expenseModelConvertedToExpense.convertToExpense(expenseRepository.findAllById(id));
        return ResponseEntity.status(HttpStatus.OK).body(
          expenses
        );
    }
}
