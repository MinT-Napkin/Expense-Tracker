package com.example.expensetracker.ui.expense_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.FragmentExpenseListBinding
import kotlinx.coroutines.launch

private const val TAG = "ExpenseListFragment"
class ExpenseListFragment : Fragment() {

    private var _binding: FragmentExpenseListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val expenseListViewModel: ExpenseListViewModel by viewModels()

    override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)

        //pg 307
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    // calls coroutines here - refer to page 390!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                expenseListViewModel.expenses.collect { expenses ->
                    binding.expenseRecyclerView.adapter =
                        ExpenseListAdapter(expenses,
                            { expenseId ->
                                val action = ExpenseListFragmentDirections.actionFragmentGalleryToFragmentEditExpense(expenseId)
                                findNavController().navigate(action)
                            },
                            { expense ->
                                expenseListViewModel.deleteExpense(expense)
                            }
                        )
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}