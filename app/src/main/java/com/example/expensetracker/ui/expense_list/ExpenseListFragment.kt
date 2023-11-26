package com.example.expensetracker.ui.expense_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.FragmentExpenseListBinding

private const val TAG = "ExpenseListFragment"
class ExpenseListFragment : Fragment() {

    private var _binding: FragmentExpenseListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val expenseListViewModel: ExpenseListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${expenseListViewModel.expenses.size}")
    }

    override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
                ViewModelProvider(this).get(ExpenseListViewModel::class.java)

        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)

        //pg 307
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(context)

        //319, SETS UP ADAPTER
        val expenses = expenseListViewModel.expenses
        val adapter = ExpenseListAdapter(expenses)
        binding.expenseRecyclerView.adapter = adapter

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}