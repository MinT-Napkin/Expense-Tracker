package com.example.expensetracker.ui.wish_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.FragmentExpenseListBinding
import com.example.expensetracker.databinding.FragmentWishListBinding
import com.example.expensetracker.ui.expense_list.*
import com.example.expensetracker.ui.home.HomeViewModel
import kotlinx.coroutines.launch
import kotlin.math.exp

private const val TAG = "WishListFragment"
class WishListFragment : Fragment() {

    private var _binding: FragmentWishListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val wishListViewModel: WishListViewModel by viewModels()

    private val expenseListViewModel: ExpenseListViewModel by viewModels()

    private val homeListViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishListBinding.inflate(inflater, container, false)

        //pg 307
        binding.wishListRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    // calls coroutines here - refer to page 390!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                wishListViewModel.expenses.collect { expenses ->
                    val unpaidExpenses = expenses.filter { !it.isPaid }

                    binding.wishListRecyclerView.adapter =
                        WishListAdapter(unpaidExpenses,
                            ({ expenseId ->
                                val action = WishListFragmentDirections.actionFragmentWishListToFragmentEditExpense(expenseId)
                                findNavController().navigate(action)

                                val wishValueL = wishListViewModel.getExpense(expenseId).isPaid
                                Log.i("Wish", "$wishValueL")
                            }),
                            ({ expenseId ->
                                expenseListViewModel.deleteExpense(expenseId)
                            }),
                            ({ expenseId ->
                                wishListViewModel.updateExpenseIsPaid(expenseId)
                            }),
                        )
                }
            }
        }

        binding.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}