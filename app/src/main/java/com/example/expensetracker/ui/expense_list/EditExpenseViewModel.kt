package com.example.expensetracker.ui.expense_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class EditExpenseViewModel (expenseId: UUID) : ViewModel() {
    private val expenseRepository = ExpenseRepository.get()
    private val _expense: MutableStateFlow<Expense?> = MutableStateFlow(null)
    val expense: StateFlow<Expense?> = _expense.asStateFlow()

    init {
        viewModelScope.launch {
            _expense.value = expenseRepository.getExpense(expenseId)
        }
    }

    fun updateExpense(onUpdate: (Expense) -> Expense) {
        _expense.update { oldExpense ->
            oldExpense?.let { onUpdate(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        expense.value?.let { expenseRepository.updateExpense(it) }
    }
}

class ExpenseDetailViewModelFactory(
    private val expenseId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditExpenseViewModel(expenseId) as T
    }
}
