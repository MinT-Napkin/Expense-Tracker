package com.example.expensetracker.ui.expense_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.databinding.FragmentExpenseListBinding

class ExpenseListFragment : Fragment() {

    private var _binding: FragmentExpenseListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View {
    val galleryViewModel =
            ViewModelProvider(this).get(ExpenseListViewModel::class.java)

    _binding = FragmentExpenseListBinding.inflate(inflater, container, false)
    val root: View = binding.root

//    val textView: TextView = binding.textGallery
//    galleryViewModel.text.observe(viewLifecycleOwner) {
//      textView.text = it
//    }

    return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}