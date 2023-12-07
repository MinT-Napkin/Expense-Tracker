package com.example.expensetracker.ui.expense_list.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.ui.expense_list.Expense
import kotlinx.coroutines.flow.Flow
import java.util.*

// DAO Data Access Object
// an interface that contains functions for each database operation you want to perform

// DAO, you can call any of the functions defined on it to interact with the database

@Dao
interface ExpenseDao
{
    // refer to page 409 for Flow**
    @Query("SELECT * FROM expense")
    fun getExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE id=(:id)")
    fun getExpense(id: UUID): Expense

    @Update
    fun updateExpense(expense: Expense)

    @Insert
    fun addExpense(expense: Expense)

    @Delete
    fun deleteExpense(expense: Expense)

}
