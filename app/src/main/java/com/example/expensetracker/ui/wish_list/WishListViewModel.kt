package com.example.expensetracker.ui.wish_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetracker.ui.expense_list.Expense
import com.example.expensetracker.ui.expense_list.ExpenseRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WishListViewModel : ViewModel() {

    private val expenseRepository = ExpenseRepository.get()

    private val _expenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val expenses: StateFlow<List<Expense>>
        get() = _expenses.asStateFlow()

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    init {
        viewModelScope.launch {
            expenseRepository.getExpenses().collect {
                _expenses.value = it
            }
        }
    }
}