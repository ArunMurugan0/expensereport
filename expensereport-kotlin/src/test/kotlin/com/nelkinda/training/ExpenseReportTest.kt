package com.nelkinda.training

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.*

class ExpenseReportTest() {
    private val standardOut: PrintStream = System.out;
    private val outputStreamCaptor: ByteArrayOutputStream = ByteArrayOutputStream();

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    fun createExpense(type: ExpenseType, amount: Int): Expense {
        val expense = Expense()
        expense.type = type
        expense.amount = amount

        return expense
    }

    @Test
    fun `It should have a meal limit marker if the Dinner amount is greater than 5000`() {
        val expenses = listOf<Expense>(
            createExpense(ExpenseType.DINNER, 5001)
        )

        ExpenseReport().printReport(expenses)

        val expectedResult = "Expenses ${Date()}\n" + "Dinner\t5001\tX\n" + "Meal expenses: 5001\n" + "Total expenses: 5001\n"
        val actualOutput = outputStreamCaptor.toString()
        assertEquals(expectedResult, actualOutput)
    }

    @Test
    fun `It should not have a meal limit marker if the Dinner amount is less than or equal to 5000`() {
        val expenses = listOf<Expense>(
            createExpense(ExpenseType.DINNER, 5000)
        )

        ExpenseReport().printReport(expenses)

        val expectedResult = "Expenses ${Date()}\n" + "Dinner\t5000\t \n" + "Meal expenses: 5000\n" + "Total expenses: 5000\n"
        val actualOutput = outputStreamCaptor.toString()
        assertEquals(expectedResult, actualOutput)
    }

    @Test
    fun `It should not have a meal limit marker if the Breakfast amount is greater than 1000`() {
        val expenses = listOf<Expense>(
            createExpense(ExpenseType.BREAKFAST, 1001)
        )

        ExpenseReport().printReport(expenses)

        val expectedResult = "Expenses ${Date()}\n" + "Breakfast\t1001\tX\n" + "Meal expenses: 1001\n" + "Total expenses: 1001\n"
        val actualOutput = outputStreamCaptor.toString()
        assertEquals(expectedResult, actualOutput)
    }

    @Test
    fun `It should not have a meal limit marker if the Breakfast amount is less than or equal to 1000`() {
        val expenses = listOf<Expense>(
            createExpense(ExpenseType.BREAKFAST, 1000)
        )

        ExpenseReport().printReport(expenses)

        val expectedResult = "Expenses ${Date()}\n" + "Breakfast\t1000\t \n" + "Meal expenses: 1000\n" + "Total expenses: 1000\n"
        val actualOutput = outputStreamCaptor.toString()
        assertEquals(expectedResult, actualOutput)
    }

    @Test
    fun shouldPrintReport() {
        val expenses = listOf<Expense>(
            createExpense(ExpenseType.DINNER, 1000),
            createExpense(ExpenseType.BREAKFAST, 1000),
            createExpense(ExpenseType.CAR_RENTAL, 1000),
            createExpense(ExpenseType.DINNER, 6000),
            createExpense(ExpenseType.BREAKFAST, 2000),
        )

        ExpenseReport().printReport(expenses)

        val expectedResult = "Expenses ${Date()}\n" + "Dinner\t1000\t \n" +
                "Breakfast\t1000\t \n" +
                "Car Rental\t1000\t \n" +
                "Dinner\t6000\tX\n" +
                "Breakfast\t2000\tX\n" +
                "Meal expenses: 10000\n" +
                "Total expenses: 11000\n"
        val actualOutput = outputStreamCaptor.toString()
        assertEquals(expectedResult, actualOutput)
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }
}