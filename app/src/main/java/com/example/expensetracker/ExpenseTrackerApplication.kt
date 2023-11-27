package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.ui.expense_list.ExpenseRepository

// refer to page 402
class ExpenseTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ExpenseRepository.initialize(this)
    }
}