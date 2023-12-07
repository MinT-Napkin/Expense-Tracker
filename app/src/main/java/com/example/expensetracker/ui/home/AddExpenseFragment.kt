package com.example.expensetracker.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentAddExpenseBinding
import com.example.expensetracker.ui.expense_list.Expense
import com.example.expensetracker.ui.expense_list.ExpenseListViewModel
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

            expenseDescription.doOnTextChanged{ text, _, _, _ ->
                newExpense = newExpense.copy(description = text.toString())
            }

            // CATEGORY DROPDOWN
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.categories_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears.
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner.
                expenseCategorySpinner.adapter = adapter
            }

            expenseCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    // Use the selectedItem text as needed
                    Log.d(TAG, "Selected item: $selectedItem")
                    newExpense = newExpense.copy(category = selectedItem)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case where nothing is selected

                }
            }

            expenseDatePaid.apply {
                //TODO: for 'gift list' feature
                text = newExpense.date.toString()
                isEnabled = false
            }

            expenseIsPaid.setOnCheckedChangeListener { _, isChecked ->
                //TODO: for 'gift list' feature
                newExpense = newExpense.copy(isPaid = !isChecked)
            }

            expenseAddConfirm.setOnClickListener {
                // Close the keyboard
                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

                // Perform the action (addExpense in your case)
                expenseListViewModel.addExpense(newExpense)

                // Navigate back to the previous fragment (Assuming you're using Navigation Component)
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}