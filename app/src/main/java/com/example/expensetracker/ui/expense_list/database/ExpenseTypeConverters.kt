package com.example.expensetracker.ui.expense_list.database

import androidx.room.TypeConverter
import java.util.*

class ExpenseTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}
