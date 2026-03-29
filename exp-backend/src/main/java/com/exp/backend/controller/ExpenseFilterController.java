package com.exp.backend.controller;

import com.exp.backend.model.Expense;
import com.exp.backend.service.ExpenseFilterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ExpenseFilterController {

    private final ExpenseFilterService expenseFilterService;

    public ExpenseFilterController(ExpenseFilterService expenseFilterService) {
        this.expenseFilterService = expenseFilterService;
    }

    @GetMapping("/expense/category/{id}")
    protected ResponseEntity<List<Expense>> categoryBasedRequest(@PathVariable("id") long id) {
        return expenseFilterService.categoryBasedRequest(id);
    }
}
