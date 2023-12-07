package com.example.expensetracker.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.databinding.FragmentHomeBinding
import com.example.expensetracker.ui.expense_list.ExpenseListViewModel
import ir.mahozad.android.PieChart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


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

        val textView2: TextView = binding.textHome2
        homeViewModel.text2.observe(viewLifecycleOwner) {
            textView2.text = it
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

            val categoryValueFlow: Flow<Map<String, Float>> = expensesList.map { expenses ->
                expenses.groupBy { it.category }
                    .mapValues { (_, list) -> list.sumByDouble { it.value.toDouble() }.toFloat() }
            }

            val categoryCountFlow: Flow<Map<String, Int>> = expensesList.map { expenses ->
                expenses.groupBy { it.category }
                    .mapValues { (_, list) -> list.size }
            }

            val totalValueFlow: Flow<Float> = expensesList.map { expenses ->
                expenses.sumByDouble { it.value.toDouble() }.toFloat()
            }

            val percentagesFlow: Flow<Map<String, Float>> = combine(
                categoryValueFlow,
                totalValueFlow
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
                        val giftsPer = percentagesMap["Gift(s)"] ?: 0.0f
                        val otherPer = percentagesMap["Other"] ?: 0.0f

                        pieChart.apply {
                            slices = listOf(
                                PieChart.Slice(personalPer, Color.rgb(255, 105, 97), legend = "Personal"),
                                PieChart.Slice(billsPer, Color.rgb(255, 180, 128), legend = "Bills"),
                                PieChart.Slice(utilitiesPer, Color.rgb(248, 243, 141), legend = "Utilities"),
                                PieChart.Slice(transportationPer, Color.rgb(66, 214, 164), legend = "Transportation"),
                                PieChart.Slice(foodPer, Color.rgb(8, 202, 209), legend = "Food"),
                                PieChart.Slice(entertainmentPer, Color.rgb(89, 173, 246), legend = "Entertainment"),
                                PieChart.Slice(giftsPer, Color.rgb(157, 148, 255), legend = "Gift(s)"),
                                PieChart.Slice(otherPer, Color.rgb(199, 128, 232), legend = "Others"),
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