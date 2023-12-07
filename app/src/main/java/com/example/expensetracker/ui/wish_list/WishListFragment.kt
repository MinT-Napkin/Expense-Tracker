package com.example.expensetracker.ui.wish_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.FragmentExpenseListBinding
import com.example.expensetracker.ui.expense_list.ExpenseListAdapter
import com.example.expensetracker.ui.expense_list.ExpenseListFragmentDirections
import com.example.expensetracker.ui.expense_list.ExpenseListViewModel
import kotlinx.coroutines.launch

private const val TAG = "WishListFragment"
class WishListFragment : Fragment() {

    private var _binding: FragmentExpenseListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val wishListViewModel: WishListViewModel by viewModels()

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
                wishListViewModel.expenses.collect { expenses ->
                    val unpaidExpenses = expenses.filter { !it.isPaid }

                    binding.expenseRecyclerView.adapter =
                        WishListAdapter(unpaidExpenses) { expenseId ->
                            val action = ExpenseListFragmentDirections.actionFragmentGalleryToFragmentEditExpense(expenseId)
                            findNavController().navigate(action)
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}