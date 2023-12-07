package com.example.expensetracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.expensetracker.R

interface OnBudgetUpdatedListener {
    fun onBudgetUpdated(budget: Float)
}

class UpdateBudgetDialogFragment : DialogFragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_budget_dialog, container, false)
        // Handle UI elements and user input here
        val confirmButton = view.findViewById<Button>(R.id.confirmButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val budgetInput = view.findViewById<EditText>(R.id.budgetInput)

        confirmButton.setOnClickListener {
            val enteredBudget = budgetInput.text.toString().toFloatOrNull() ?: 0.0f
            // Pass the entered budget value to the parent fragment/activity
            (requireActivity() as? OnBudgetUpdatedListener)?.onBudgetUpdated(enteredBudget)
            homeViewModel.updateBudget(enteredBudget)
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        return view
    }
}

