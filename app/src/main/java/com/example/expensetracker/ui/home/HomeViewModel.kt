package com.example.expensetracker.ui.home

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

    private var budget: Float = 1000.0f
    private var remainingBudget: Float = 10.0f

    private val expenseRepository = ExpenseRepository.get()

    private val _expenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val expenses: StateFlow<List<Expense>>
        get() = _expenses.asStateFlow()


    private val _text = MutableLiveData<String>().apply {
        value = "Budget: $$budget"
    }
    private val _text2 = MutableLiveData<String>().apply {
        value = "After Expenses: $$remainingBudget"
    }

    val text: LiveData<String> = _text
    val text2: LiveData<String> = _text2

    init {
        viewModelScope.launch {
            expenseRepository.getExpenses().collect {
                _expenses.value = it
            }
        }

        Log.d("test", "collected: $${_expenses.value}")
    }

    fun updateBudget(num: Float) {
        budget = num

        updateUi()
    }

    fun updateUi() {
        viewModelScope.launch {
            _text.value = "Budget: $$budget" // Update the displayed budget

            val expensesList = _expenses.value // Retrieve the current list of expenses

            // Filter the list to get only the paid expenses
            val paidExpenses = expensesList.filter { it.isPaid }

            // Calculate total expenses from the paid expenses list
            val totalExpenses: Float = paidExpenses.sumByDouble { it.value.toDouble() }.toFloat()

            // Calculate remaining budget after deducting expenses
            remainingBudget = (budget - totalExpenses)

            _text2.value = "After Expenses: $$remainingBudget" // Update the remaining budget text
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