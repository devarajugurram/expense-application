package com.exp.backend.controller;

import com.exp.backend.model.Expense;
import com.exp.backend.model.ExpenseModel;
import com.exp.backend.service.ExpenditureService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ExpenditureController {

    private final ExpenditureService expenditureService;

    public ExpenditureController(ExpenditureService expenditureService) {
        this.expenditureService = expenditureService;
    }

    @PostMapping("/expense/add")
    protected ResponseEntity<Map<String,Object>> addExpense(
            @RequestBody ExpenseModel expenseModel,
            HttpServletRequest request) {
        return expenditureService.addExpense(expenseModel,request);
    }

    @GetMapping("/expense/getAll")
    protected ResponseEntity<List<Expense>> getAllExpenses(
            @RequestParam("date") String date, HttpServletRequest request) {
        return expenditureService.getAllExpenses(date,request);
    }

    @PatchMapping("/expense/edit/{id}")
    protected ResponseEntity<Map<String,Object>> editExpenses(
            @PathVariable long id,
            @RequestBody Expense expense) {
        return expenditureService.editExpenses(id,expense);
    }

    @PatchMapping("/expense/delete/{id}")
    protected ResponseEntity<Map<String,Object>> deleteExpense(@PathVariable long id) {
        return expenditureService.deleteExpense(id);
    }
}
