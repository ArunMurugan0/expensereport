package com.nelkinda.training

import java.util.Date

enum class ExpenseType {
    DINNER, BREAKFAST, CAR_RENTAL
}

class Expense {
    lateinit var type: ExpenseType
    var amount: Int = 0

    fun isMealExpense() =
        type == ExpenseType.DINNER || type == ExpenseType.BREAKFAST
}

class ExpenseReport {
    fun printReport(expenses: List<Expense>) {
        var total = 0

        println("Expenses ${Date()}")

        val mealExpenses = expenses.filter { it.isMealExpense() }.sumOf { it.amount }

        for (expense in expenses) {
            println(getExpenseName(expense) + "\t" + expense.amount + "\t" + getMealOverExpensesMarker(expense))

            total += expense.amount
        }

        println("Meal expenses: $mealExpenses")
        println("Total expenses: $total")
    }

    private fun getMealOverExpensesMarker(expense: Expense) =
        if (expense.type == ExpenseType.DINNER && expense.amount > 5000 || expense.type == ExpenseType.BREAKFAST && expense.amount > 1000) "X" else " "

    private fun getExpenseName(expense: Expense) = when (expense.type) {
        ExpenseType.DINNER -> "Dinner"
        ExpenseType.BREAKFAST -> "Breakfast"
        ExpenseType.CAR_RENTAL -> "Car Rental"
    }
}
