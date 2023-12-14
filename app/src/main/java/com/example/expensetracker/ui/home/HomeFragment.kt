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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.databinding.FragmentHomeBinding
import com.example.expensetracker.ui.expense_list.ExpenseListViewModel
import ir.mahozad.android.PieChart
import ir.mahozad.android.unit.Dimension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    // private var btnShowDialog: Button? = null

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private val expenseListViewModel: ExpenseListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                val action = HomeFragmentDirections.actionFragmentHomeToAddExpenseFragment()
                findNavController().navigate(action)
            }

            // budget handling

            val expensesList = expenseListViewModel.expenses

            val categoryValueFlow: Flow<Map<String, Float>> = expensesList.map { expenses ->
                expenses.filter { it.isPaid }
                        .groupBy { it.category }
                        .mapValues { (_, list) -> list.sumByDouble { it.value.toDouble() }.toFloat() }
            }

            val totalValueFlow: Flow<Float> = expensesList.map { expenses ->
                expenses.filter { it.isPaid }
                        .sumByDouble { it.value.toDouble() }.toFloat()
            }

            val percentagesFlow: Flow<Map<String, Float>> = combine(
                categoryValueFlow,
                totalValueFlow
            ) { categoryCount, totalCount ->
                categoryCount.mapValues { (_, count) -> count.toFloat() / totalCount }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    //part of pie graph
                    percentagesFlow.collect { percentagesMap ->
                        val personalPer = percentagesMap["Personal"] ?: 0.0f
                        val billsPer = percentagesMap["Bills"] ?: 0.0f
                        val utilitiesPer = percentagesMap["Utilities"] ?: 0.0f
                        val transportationPer = percentagesMap["Transportation"] ?: 0.0f
                        val foodPer = percentagesMap["Food"] ?: 0.0f
                        val entertainmentPer = percentagesMap["Entertainment"] ?: 0.0f
                        val giftsPer = percentagesMap["Gift(s)"] ?: 0.0f
                        val otherPer = percentagesMap["Others"] ?: 0.0f
//
//                        val slicesList = listOf(
//                            PieChart.Slice(personalPer, Color.rgb(214, 152, 158), legend = "Personal", label = "Personal"),
//                            PieChart.Slice(billsPer, Color.rgb(171, 152, 158), legend = "Bills", label = "Bills"),
//                            PieChart.Slice(utilitiesPer, Color.rgb(171, 152, 214), legend = "Utilities", label = "Utilities"),
//                            PieChart.Slice(transportationPer, Color.rgb(171, 214, 214), legend = "Transportation", label = "Transportation"),
//                            PieChart.Slice(foodPer, Color.rgb(244, 232, 215), legend = "Food", label = "Food"),
//                            PieChart.Slice(entertainmentPer, Color.rgb(170, 213, 220), legend = "Entertainment", label = "Entertainment"),
//                            PieChart.Slice(giftsPer, Color.rgb(222, 244, 244), legend = "Gift(s)", label = "Gift(s)"),
//                            PieChart.Slice(otherPer, Color.rgb(244, 222, 220), legend = "Others", label = "Others"),
//                        )
//
//                        val filteredSlices = slicesList.filter { it.value > 0.0f }

                        pieChart.apply {
                            slices = listOf(
                                PieChart.Slice(personalPer, Color.rgb(251, 210, 6), legend = "Personal", label = personalPer.toString()),
                                PieChart.Slice(billsPer, Color.rgb(254, 175, 138), legend = "Bills"),
                                PieChart.Slice(utilitiesPer, Color.rgb(253, 122, 140), legend = "Utilities"),
                                PieChart.Slice(transportationPer, Color.rgb(204, 137, 214), legend = "Transportation"),
                                PieChart.Slice(foodPer, Color.rgb(191, 207, 240), legend = "Food"),
                                PieChart.Slice(entertainmentPer, Color.rgb(156, 231, 201), legend = "Entertainment"),
                                PieChart.Slice(giftsPer, Color.rgb(77, 198, 86), legend = "Gift(s)"),
                                PieChart.Slice(otherPer, Color.rgb(166, 170, 178), legend = "Others"),
                            )
                            labelsColor = Color.BLACK
                            labelsSize = Dimension.DP(12.0f)
                            startAngle = 0
                            labelType = PieChart.LabelType.INSIDE
                            isLegendEnabled = true
                            legendsColor = Color.BLACK
                            legendsSize = Dimension.DP(15.0f)
                        }
                    }
                }
            } // coroutine end

            setBtn.setOnClickListener {
                val dialog = UpdateBudgetDialogFragment()
                dialog.show(requireActivity().supportFragmentManager, "UpdateBudgetDialog")
            }

            updateBtn.setOnClickListener{
                homeViewModel.updateUi()
            }


        } // binding end

    }

    override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
    }
}