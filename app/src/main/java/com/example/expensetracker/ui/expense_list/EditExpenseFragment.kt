package com.example.expensetracker.ui.expense_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentEditExpenseBinding
import kotlinx.coroutines.launch


private const val TAG = "EditExpenseFragment"

class EditExpenseFragment : Fragment() {

    private var _binding: FragmentEditExpenseBinding? = null
    private val binding get() = _binding!!

    private val args: EditExpenseFragmentArgs by navArgs()

    private val editExpenseViewModel: EditExpenseViewModel by viewModels {
        CrimeDetailViewModelFactory(args.expenseId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentEditExpenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            expenseValue.doOnTextChanged { text, _, _, _ ->
                //TODO: make text input only accept numbers
                editExpenseViewModel.updateExpense { oldExpense ->
                    try {
                        // Attempt to convert text to a float
                        oldExpense.copy(value = text.toString().toFloat())
                    } catch (e: NumberFormatException) {
                        // Handle the case where text is not a valid float
                        Log.e(TAG, "INVALID FLOAT")
                        oldExpense // Keep the expense unchanged
                    }
                }
            }

            expenseDescription.doOnTextChanged { text, _, _, _ ->
                //TODO: for 'gift list' feature
//                text = expense.date.toString()

                editExpenseViewModel.updateExpense { oldExpense ->
                    oldExpense.copy(description = text.toString())
                }
            }

            // CATEGORY DROPDOWN
            ArrayAdapter.createFromResource(
                requireContext(),
                com.example.expensetracker.R.array.categories_array,
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
                    editExpenseViewModel.updateExpense { oldExpense ->
                        oldExpense.copy(category = selectedItem)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case where nothing is selected

                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    editExpenseViewModel.expense.collect { expense ->
                        expense?.let { updateUi(it) }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(expense: Expense) {
        binding.apply {
            //TODO for editing current expenses in list
            if (expenseValue.text.toString() != expense.value.toString()) {
                expenseValue.setText(expense.value.toString())
            }

            if (expenseDescription.text.toString() != expense.description) {
                expenseDescription.setText(expense.description)
            }

            val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                com.example.expensetracker.R.array.categories_array,
                android.R.layout.simple_spinner_item
            )

            if (expense.category != null) {
                val spinnerPosition: Int = adapter.getPosition(expense.category)
                expenseCategorySpinner.setSelection(spinnerPosition)
            }
        }
    }

}