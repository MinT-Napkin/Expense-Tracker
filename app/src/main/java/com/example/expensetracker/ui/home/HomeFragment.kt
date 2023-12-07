package com.example.expensetracker.ui.home

import android.app.Dialog
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.FragmentHomeBinding
import com.example.expensetracker.ui.expense_list.ExpenseListViewModel
import ir.mahozad.android.PieChart
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import com.example.expensetracker.R
import com.example.expensetracker.ui.expense_list.ExpenseListAdapter
import com.example.expensetracker.ui.expense_list.ExpenseListFragmentDirections
import ir.mahozad.android.DimensionResource
import ir.mahozad.android.component.Alignment
import ir.mahozad.android.unit.Dimension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map


class HomeFragment : Fragment() {

    // private var btnShowDialog: Button? = null

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = checkNotNull(_binding) {
        "Cannot access binding because it is null."
    }

    private val expenseListViewModel: ExpenseListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
          textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            addExpenseBtn.setOnClickListener{
                //TODO
                val action = HomeFragmentDirections.actionFragmentHomeToAddExpenseFragment()
                findNavController().navigate(action)
            }

            val expensesList = expenseListViewModel.expenses

            val categoryCountFlow: Flow<Map<String, Int>> = expensesList.map { expenses ->
                expenses.groupBy { it.category }
                    .mapValues { (_, list) -> list.size }
            }

            val totalCountFlow: Flow<Int> = expensesList.map { it.size }

            val percentagesFlow: Flow<Map<String, Float>> = combine(
                categoryCountFlow,
                totalCountFlow
            ) { categoryCount, totalCount ->
                categoryCount.mapValues { (_, count) -> count.toFloat() / totalCount }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    percentagesFlow.collect { percentagesMap ->
                        val personalPer = percentagesMap["Personal"] ?: 0.0f
                        val billsPer = percentagesMap["Bills"] ?: 0.0f
                        val utilitiesPer = percentagesMap["Utilities"] ?: 0.0f
                        val transportationPer = percentagesMap["Transportation"] ?: 0.0f
                        val foodPer = percentagesMap["Food"] ?: 0.0f
                        val entertainmentPer = percentagesMap["Entertainment"] ?: 0.0f
                        val giftsPer = percentagesMap["Gifts"] ?: 0.0f
                        val otherPer = percentagesMap["Other"] ?: 0.0f

                        pieChart.apply {
                            slices = listOf(
                                PieChart.Slice(personalPer, Color.rgb(214, 152, 158), legend = "Personal"),
                                PieChart.Slice(billsPer, Color.rgb(171, 152, 158), legend = "Bills"),
                                PieChart.Slice(utilitiesPer, Color.rgb(171, 152, 214), legend = "Utilities"),
                                PieChart.Slice(transportationPer, Color.rgb(171, 214, 214), legend = "Transportation"),
                                PieChart.Slice(foodPer, Color.rgb(244, 232, 215), legend = "Food"),
                                PieChart.Slice(entertainmentPer, Color.rgb(170, 213, 220), legend = "Entertainment"),
                                PieChart.Slice(giftsPer, Color.rgb(222, 244, 244), legend = "Gift(s)"),
                                PieChart.Slice(otherPer, Color.rgb(244, 222, 220), legend = "Others"),
                            )
                        }
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