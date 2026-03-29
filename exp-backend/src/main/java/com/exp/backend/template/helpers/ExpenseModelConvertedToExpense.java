package com.exp.backend.template.helpers;

import com.exp.backend.model.Expense;
import com.exp.backend.model.ExpenseModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseModelConvertedToExpense {
    public List<Expense> convertToExpense(List<ExpenseModel> expenseModels) {
        List<Expense> expenses = new ArrayList<>();
        for(ExpenseModel expenseModel : expenseModels) {
            expenses.add(
              new Expense(expenseModel.getExpenseId(),
                      expenseModel.getCategory(),
                      expenseModel.getExpenseName(),
                      expenseModel.getAmount(),
                      expenseModel.getLocalDateTime())
            );
        }
        return expenses;
    }
}
