package com.example.expensetracker.ui.expense_list

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Expense (
    @PrimaryKey val id: UUID,
    val value: Float,
    val description: String,
    val category: String,
    val date: Date, // for 'gift list' feature or other
    val isPaid: Boolean // for 'gift list' feature or other
)