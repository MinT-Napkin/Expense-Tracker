package com.example.expensetracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Budget: $1000"
    }
    private val _text2 = MutableLiveData<String>().apply {
        value = "After Expenses: $825"
    }
    val text: LiveData<String> = _text
    val text2: LiveData<String> = _text2

    fun updateText(newText: String) {
        _text.value = newText
    }

    fun updateText2(newText: String) {
        _text2.value = newText
    }

}