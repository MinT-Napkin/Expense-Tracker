package com.example.expensetracker.ui.wish_list

import com.example.expensetracker.ui.expense_list.Expense
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ListItemExpenseBinding
import com.example.expensetracker.databinding.ListItemWishBinding
import java.util.*


private const val TAG = "ExpenseListAdapter"
//refer to page 316
class WishHolder(
    val binding: ListItemWishBinding
) : RecyclerView.ViewHolder(binding.root)
{
    // refer to page 437 for expenseID explanation
    fun bind(expense: Expense, onExpenseClicked: (expenseId: UUID) -> Unit) {

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
    }
}

class WishListAdapter(
    private val expenses: List<Expense>,
    private val onExpenseClicked: (expenseId: UUID) -> Unit
) : RecyclerView.Adapter<WishHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : WishHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemWishBinding.inflate(inflater, parent, false)
        return WishHolder(binding)
    }

    override fun onBindViewHolder(holder: WishHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense, onExpenseClicked)
    }

    override fun getItemCount() = expenses.size
}
