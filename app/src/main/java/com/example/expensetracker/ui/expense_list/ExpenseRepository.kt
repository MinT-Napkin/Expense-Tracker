package com.example.expensetracker.ui.expense_list

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.ui.expense_list.database.ExpenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

// Repository pattern
/* repository class encapsulates the logic for accessing data from a single
source or a set of sources. It determines how to fetch and store a particular
set of data, whether locally in a database or from a remote server */

// purpose of repository is to fetch and store data

private const val DATABASE_NAME = "expense-database"
class ExpenseRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val database: ExpenseDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            ExpenseDatabase::class.java,
            DATABASE_NAME
        )
        .allowMainThreadQueries()
        .build()

    // refers to ExpenseDao
    fun getExpenses(): Flow<List<Expense>> = database.expenseDao().getExpenses()

    fun getExpense(id: UUID): Expense = database.expenseDao().getExpense(id)

    fun updateExpense(expense: Expense) {
        coroutineScope.launch {
            database.expenseDao().updateExpense(expense)
        }
    }

    fun addExpense(expense: Expense) {
        database.expenseDao().addExpense(expense)
    }

    fun deleteExpense(expense: Expense) {
        database.expenseDao().deleteExpense(expense)
    }

    companion object {
        private var INSTANCE: ExpenseRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ExpenseRepository(context)
            }
        }
        fun get(): ExpenseRepository {
            return INSTANCE ?:
            throw IllegalStateException("ExpenseRepository must be initialized")
        }
    }
}