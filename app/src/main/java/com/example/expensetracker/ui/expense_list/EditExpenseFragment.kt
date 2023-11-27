package com.example.expensetracker.ui.expense_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
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
//                expense = try {
//                    // Attempt to convert text to a float
//                    expense.copy(value = text.toString().toFloat())
//                } catch (e: NumberFormatException) {
//                    // Handle the case where text is not a valid float
//                    Log.e(TAG,"INVALID FLOAT")
//                    expense // Keep the expense unchanged
//                }
            }

            expenseDatePaid.apply {
                //TODO: for 'gift list' feature
//                text = expense.date.toString()

                editExpenseViewModel.updateExpense { oldCrime ->
                    oldCrime.copy(description = text.toString())
                }


                isEnabled = false
            }

            expenseIsPaid.setOnCheckedChangeListener { _, isChecked ->
                //TODO: for 'gift list' feature
//                expense = expense.copy(isPaid = true)

                editExpenseViewModel.updateExpense { oldExpense ->
                    oldExpense.copy(isPaid = isChecked)
                }

                expenseIsPaid.isEnabled = false
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    editExpenseViewModel.expense.collect { crime ->
                        crime?.let { updateUi(it) }
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
            if (expenseDescription.text.toString() != expense.description) {
                expenseDescription.setText(expense.description)
            }

            //expenseDate. . .

            //expenseIsPaid. . .  etc etc
        }
    }

}