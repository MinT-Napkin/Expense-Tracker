package com.example.expensetracker.ui.expense_list

import java.util.*

data class Expense (
    val id: UUID,
    val value: Float,
    val description: String,
    val category: String,
    val date: Date, // for 'gift list' feature or other
    val isPaid: Boolean // for 'gift list' feature or other
)