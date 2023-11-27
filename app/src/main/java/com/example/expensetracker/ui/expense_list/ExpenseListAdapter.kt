package com.example.expensetracker.ui.expense_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.ListItemExpenseBinding
import java.util.*

//refer to page 316
class ExpenseHolder(
    val binding: ListItemExpenseBinding
) : RecyclerView.ViewHolder(binding.root)
{
    // refer to page 437 for expenseID explanation
    fun bind(expense: Expense, onExpenseClicked: (expenseId: UUID) -> Unit) {
        binding.expenseDescription.text = expense.description
        binding.expenseDate.text = expense.date.toString()

        val valueText = expense.value.toString()
        binding.expenseValue.text = "$$valueText"

        binding.root.setOnClickListener {
            onExpenseClicked(expense.id)
        }

    }
}

class ExpenseListAdapter(
    private val expenses: List<Expense>,
    private val onExpenseClicked: (expenseId: UUID) -> Unit
) : RecyclerView.Adapter<ExpenseHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ExpenseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemExpenseBinding.inflate(inflater, parent, false)
        return ExpenseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense, onExpenseClicked)
    }

    override fun getItemCount() = expenses.size
}
