package com.example.expensetracker.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.expensetracker.databinding.FragmentAddExpenseBinding
import com.example.expensetracker.ui.expense_list.Expense
import java.util.*

private const val TAG = "ADD_EXPENSE_FRAGMENT"

class AddExpenseFragment : Fragment() {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var expense: Expense
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expense = Expense(
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
                expense = try {
                    // Attempt to convert text to a float
                    expense.copy(value = text.toString().toFloat())
                } catch (e: NumberFormatException) {
                    // Handle the case where text is not a valid float
                    Log.e(TAG,"INVALID FLOAT")
                    expense // Keep the expense unchanged
                }
            }

            expenseDatePaid.apply {
                //TODO: for 'gift list' feature
                text = expense.date.toString()
                isEnabled = false
            }

            expenseIsPaid.setOnCheckedChangeListener { _, isChecked ->
                //TODO: for 'gift list' feature
                expense = expense.copy(isPaid = true)
                expenseIsPaid.isEnabled = false
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}