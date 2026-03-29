package com.exp.backend.service;

import com.exp.backend.exceptions.local.ExpenseNotAvailableException;
import com.exp.backend.exceptions.local.ExpenseNotCreatedException;
import com.exp.backend.exceptions.local.ExpenseNotFoundException;
import com.exp.backend.model.Expense;
import com.exp.backend.model.ExpenseModel;
import com.exp.backend.repo.ExpenseRepository;
import com.exp.backend.template.helpers.ExpenseModelConvertedToExpense;
import com.exp.backend.template.helpers.HelperComponentForToken;
import com.exp.backend.template.helpers.ResponseHelperMethods;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.CookieStore;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.exp.backend.template.strings.TemplateStrings.*;

@Service
public class ExpenditureService {

    private final ExpenseRepository expenseRepository;
    private final ResponseHelperMethods responseHelperMethods;
    private final ExpenseModelConvertedToExpense expenseModelConvertedToExpense;

    /**
     *
     * @param expenseRepository
     * @param responseHelperMethods
     * @param expenseModelConvertedToExpense
     */
    public ExpenditureService(ExpenseRepository  expenseRepository,
                              ResponseHelperMethods responseHelperMethods,
                              ExpenseModelConvertedToExpense expenseModelConvertedToExpense) {
        this.expenseRepository = expenseRepository;
        this.responseHelperMethods = responseHelperMethods;
        this.expenseModelConvertedToExpense = expenseModelConvertedToExpense;
    }

    /**
     *
     *
     * @param expenseModel
     * @param request
     * @return
     */
    public ResponseEntity<Map<String,Object>> addExpense(
            ExpenseModel expenseModel,
            HttpServletRequest request) throws ExpenseNotCreatedException{
        try{
            expenseModel.setLocalDateTime(LocalDateTime.now());
            long id = extractToken(request);
            expenseModel.setUserId(id);
            expenseRepository.save(expenseModel);
        }catch(Exception ex) {
            throw new ExpenseNotCreatedException(EXPENSE_NOT_CREATED);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                responseHelperMethods.getRegistrationResponseHelper(EXPENSE_CREATED,HttpStatus.CREATED, LocalDateTime.now())
        );
    }

    /**
     *
     *
     * @param date
     * @param request
     * @return
     */


    public ResponseEntity<List<Expense>> getAllExpenses(String date, HttpServletRequest request) {
        YearMonth yearMonth = YearMonth.parse(date);

        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.plusMonths(1).atDay(1).atStartOfDay();

        try {
            long userId = extractToken(request);

            List<ExpenseModel> monthList =
                    expenseRepository.findAllByDate(startDate, endDate, userId);
            List<Expense> month = expenseModelConvertedToExpense.convertToExpense(monthList);
            return ResponseEntity.status(HttpStatus.OK).body(month);

        } catch (Exception ex) {

            throw new ExpenseNotFoundException("Expense Not Found");
        }
    }

    /**
     *
     * @param id
     * @param expense
     * @return
     */

    public ResponseEntity<Map<String,Object>> editExpenses(long id,
                                                           Expense expense) {
        try{
            Optional<ExpenseModel> expenseModel = expenseRepository.findById(id);
            if(expenseModel.isEmpty()) throw new RuntimeException("Expense Not Available.");
            ExpenseModel model = expenseModel.get();
            model.setExpenseName(expense.getExpenseName());
            model.setAmount(expense.getAmount());
            model.setCategory(expense.getCategory());
            expenseRepository.save(model);
        }catch(Exception ex) {
            throw new ExpenseNotAvailableException(EXPENSE_NOT_AVAILABLE);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseHelperMethods.getRegistrationResponseHelper(
                        EXPENSE_UPDATED_SUCCESSFULLY,
                        HttpStatus.OK,
                        LocalDateTime.now()
                ));
    }


    /**
     *
     * @param id
     * @return
     */


    public ResponseEntity<Map<String,Object>> deleteExpense(long id) {
        try{
            Optional<ExpenseModel> expenseModel = expenseRepository.findById(id);
            if(expenseModel.isEmpty()) throw new RuntimeException("Expense Not Available.");
            ExpenseModel model = expenseModel.get();
            model.setActive(!model.isActive());
            expenseRepository.save(model);
        }catch(Exception ex) {
            throw new ExpenseNotAvailableException(EXPENSE_NOT_AVAILABLE);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseHelperMethods.getRegistrationResponseHelper(
                        EXPENSE_DELETED_SUCCESSFULLY,
                        HttpStatus.OK,
                        LocalDateTime.now()
                ));
    }


    /**
     *
     *
     * @param request
     * @return
     */

    public long extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if("AUTHENTICATION_ID".equals(cookie.getName())) {
                String resultant = cookie.getValue().substring(6,cookie.getValue().indexOf("2103X"));
                return Long.valueOf(resultant);
            }
        }
        return 0;
    }
}
