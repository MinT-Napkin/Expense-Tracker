package com.example.expensetracker.ui.settings
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.expensetracker.R


class CurrencyConversionFragment : Fragment() {

    private val usdToEuroRate = 0.92
    private val usdToPoundRate = 0.79
    private val euroToUsdRate = 1.09
    private val poundToUsdRate = 1.27
    private val conversionOptions = arrayOf("USD to Euro", "USD to Pound", "Euro to USD", "Pound to USD")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_converter, container, false)
    }

    // Inside onViewCreated in CurrencyConverterFragment.kt

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val amountEditText: EditText = view.findViewById(R.id.amountEditText)
        val convertButton: Button = view.findViewById(R.id.convertButton)
        val conversionSpinner: Spinner = view.findViewById(R.id.conversionSpinner)
        val resultTextView: TextView = view.findViewById(R.id.resultTextView)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, conversionOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        conversionSpinner.adapter = adapter

        convertButton.setOnClickListener {
            val amountStr = amountEditText.text.toString()
            val amount = amountStr.toDoubleOrNull() ?: return@setOnClickListener

            val selectedConversion = conversionSpinner.selectedItem?.toString() ?: return@setOnClickListener
            val convertedAmount = convertCurrency(amount, selectedConversion)
            displayConvertedAmount(convertedAmount, resultTextView)
        }
    }


    private fun convertCurrency(amount: Double, selectedConversion: String): Double {
        return when (selectedConversion) {
            "USD to Euro" -> amount * usdToEuroRate
            "USD to Pound" -> amount * usdToPoundRate
            "Euro to USD" -> amount * euroToUsdRate
            "Pound to USD" -> amount * poundToUsdRate
            else -> amount // Default: No conversion
        }
    }

    private fun displayConvertedAmount(convertedAmount: Double, resultTextView: TextView) {
        resultTextView.text = getString(R.string.converted_amount, convertedAmount)
    }
}
