package com.arjeta.expensetracker;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // allow Vue dev server
@RequestMapping("/api/expenses")
public class ExpenseController {
    private List<Expense> expenses = new ArrayList<>();

    @GetMapping
    public List<Expense> getExpenses() {
        return expenses;
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        expenses.add(expense);
        return expense;
    }
}
