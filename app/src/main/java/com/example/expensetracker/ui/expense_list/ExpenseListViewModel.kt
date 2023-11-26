package com.example.expensetracker.ui.expense_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ExpenseListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    val expenses = mutableListOf<Expense>()
    init {
        for (i in 0 until 100) {
            val expense = Expense(
                id = UUID.randomUUID(),
                value = i.toString().toFloat(),
                description ="Expense #$i",
                category = "Food",
                date = Date(),
                isPaid = i % 2 == 0
            )
            expenses += expense
        }
    }
}