package com.example.expensetracker.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.ui.expense_list.Expense
import com.example.expensetracker.ui.expense_list.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private val expenseRepository = ExpenseRepository.get()

    private val _expenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    private val expenses: StateFlow<List<Expense>>
        get() = _expenses.asStateFlow()

    init {
        viewModelScope.launch {
            expenseRepository.getExpenses().collect {
                _expenses.value = it
                Log.d("test", "collected: $${_expenses.value}")
            }
        }
    }

    fun getTotalExpenses(): Float {
        var totalExpenses = 0F
        viewModelScope.launch {
            val expensesList = _expenses.value // Retrieve the current list of expenses
            Log.d("test", "expense list: $$expensesList")
            // Filter the list to get only the paid expenses
            val paidExpenses = expensesList.filter { it.isPaid }
            Log.d("test", "paid expenses: $$paidExpenses")
            // Calculate total expenses from the paid expenses list
            totalExpenses = paidExpenses.sumOf { it.value.toDouble() }.toFloat()
            Log.d("test", "total added expenses : $$totalExpenses")
        }
        return totalExpenses
    }


}