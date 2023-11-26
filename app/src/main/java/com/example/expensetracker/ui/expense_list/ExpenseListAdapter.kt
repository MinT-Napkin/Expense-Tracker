package com.example.expensetracker.ui.expense_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.ListItemExpenseBinding

//refer to page 316
class ExpenseHolder(
    val binding: ListItemExpenseBinding
) : RecyclerView.ViewHolder(binding.root)
{

}

class ExpenseListAdapter(
    private val expenses: List<Expense>
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
        holder.apply {
            binding.expenseDescription.text = expense.description
            binding.expenseDate.text = expense.date.toString()

            val valueText = expense.value.toString()
            binding.expenseValue.text = "$$valueText"
        }
    }

    override fun getItemCount() = expenses.size
}
