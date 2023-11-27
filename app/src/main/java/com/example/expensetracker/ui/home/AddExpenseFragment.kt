package com.example.expensetracker.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.expensetracker.databinding.FragmentAddExpenseBinding
import com.example.expensetracker.ui.expense_list.Expense
import com.example.expensetracker.ui.expense_list.ExpenseListViewModel
import com.example.expensetracker.ui.expense_list.ExpenseRepository
import java.util.*

private const val TAG = "AddExpenseFragment"

class AddExpenseFragment : Fragment() {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    private val expenseListViewModel: ExpenseListViewModel by viewModels()

    private lateinit var newExpense: Expense

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newExpense = Expense(
            id = UUID.randomUUID(),
            value = 10.toFloat(),
            description = "Expense #0",
            category = "Food",
            date = Date(),
            isPaid = true
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentAddExpenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            expenseValue.doOnTextChanged { text, _, _, _ ->
                //TODO: make text input only accept numbers
                newExpense = try {
                    // Attempt to convert text to a float
                    newExpense.copy(value = text.toString().toFloat())
                } catch (e: NumberFormatException) {
                    // Handle the case where text is not a valid float
                    Log.e(TAG,"INVALID FLOAT")
                    newExpense // Keep the expense unchanged
                }
            }

            expenseDatePaid.apply {
                //TODO: for 'gift list' feature
                text = newExpense.date.toString()
                isEnabled = false
            }

            expenseIsPaid.setOnCheckedChangeListener { _, isChecked ->
                //TODO: for 'gift list' feature
                newExpense = newExpense.copy(isPaid = true)
                expenseIsPaid.isEnabled = false
            }

            expenseAddConfirm.setOnClickListener{
                expenseListViewModel.addExpense(newExpense)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}