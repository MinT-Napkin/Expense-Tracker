package com.example.expensetracker.ui.expense_list.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.ui.expense_list.Expense

@Database(entities = [ Expense::class ], version=1)
@TypeConverters(ExpenseTypeConverters::class)
abstract class ExpenseDatabase : RoomDatabase()
{
    // registers DAO in database
    abstract fun expenseDao(): ExpenseDao
}
