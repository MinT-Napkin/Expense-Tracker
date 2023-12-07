package com.example.expensetracker.ui.expense_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
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
    val deleteButton: Button = binding.deleteBtn
    // refer to page 437 for expenseID explanation
    fun bind(expense: Expense, onExpenseClicked: (expenseId: UUID) -> Unit, onDeleteClicked: (expenseId: UUID) -> Unit) {

        binding.apply{

//            Log.i(TAG, expense.description)
            expenseDescription.text = expense.description
            expenseDate.text = expense.date.toString()

            val valueText = expense.value.toString()
            expenseValue.text = "$$valueText"

            root.setOnClickListener {
                onExpenseClicked(expense.id)
            }

            val category = expense.category
//            Log.i(TAG, "$category")
            // handle category image
            when (category) { //TODO unique image for each category
                "Personal" -> {
                    expenseCategoryImage.setImageResource(R.drawable.personal)
                }
                "Bills" -> {
                    expenseCategoryImage.setImageResource(R.drawable.bills)
                }
                "Utilities" -> {
                    expenseCategoryImage.setImageResource(R.drawable.utilities)
                }
                "Transportation" -> {
                    expenseCategoryImage.setImageResource(R.drawable.transportation)
                }
                "Food" -> {
                    expenseCategoryImage.setImageResource(R.drawable.food)
                }
                "Entertainment" -> {
                    expenseCategoryImage.setImageResource(R.drawable.entertainment)
                }
                "Gift(s)" -> {
                    expenseCategoryImage.setImageResource(R.drawable.gift)
                }
                "Other Fees" -> {
                    expenseCategoryImage.setImageResource(R.drawable.other_fees)
                }
                else -> {
                    expenseCategoryImage.setImageResource(R.drawable.testimage)
                }
            }
        }
        deleteButton.setOnClickListener {
            onDeleteClicked(expense.id)
        }
    }
}

class ExpenseListAdapter(
    private val expenses: List<Expense>,
    private val onExpenseClicked: (expenseId: UUID) -> Unit,

) : RecyclerView.Adapter<ExpenseHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemExpenseBinding.inflate(inflater, parent, false)
        return ExpenseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense, onExpenseClicked,onDeleteClicked)

        // Set up click listener for delete button
        holder.deleteButton.setOnClickListener {
//            onDeleteClicked(expense.id)
        }
    }

    override fun getItemCount() = expenses.size
}
