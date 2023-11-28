package com.example.expensetracker.ui.expense_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ListItemExpenseBinding
import java.util.*


private const val TAG = "ExpenseListAdapter"
//refer to page 316
class ExpenseHolder(
    val binding: ListItemExpenseBinding
) : RecyclerView.ViewHolder(binding.root)
{
    // refer to page 437 for expenseID explanation
    fun bind(expense: Expense, onExpenseClicked: (expenseId: UUID) -> Unit) {

        binding.apply{

            Log.i(TAG, expense.description)
            expenseDescription.text = expense.description
            expenseDate.text = expense.date.toString()

            val valueText = expense.value.toString()
            expenseValue.text = "$$valueText"

            root.setOnClickListener {
                onExpenseClicked(expense.id)
            }

            // handle category image
            when (expense.category) { //TODO unique image for each category
                "Food" -> {
//                    expenseCategoryImage.setImageResource(R.drawable.testimage2)
                }
                "Entertainment" -> {

                }
                "Other Fees" -> {

                }
                else -> {
                    expenseCategoryImage.setImageResource(R.drawable.testimage)
                }
            }
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
