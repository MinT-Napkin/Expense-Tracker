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

    private val usdToEuroRate = 0.93
    private val usdToPoundRate = 0.79
    private val usdToYenRate = 147.17
    private val usdToRupeeRate = 83.27

    private val poundToUsdRate = 1.26
    private val poundToEuroRate = 1.17
    private val poundToYenRate = 185.30
    private val poundToRupeeRate = 104.89

    private val yenToUsdRate = 0.0068
    private val yenToEuroRate = 0.0063
    private val yenToPoundRate = 0.0054
    private val yenToRupeeRate = 0.57

    private val rupeeToUsdRate = 0.012
    private val rupeeToPoundRate = 0.0095
    private val rupeeToEuroRate = 0.011
    private val rupeeToYenRate = 1.77

    private val euroToUsdRate = 1.08
    private val euroToPoundRate = 0.86
    private val euroToYenRate = 158.84
    private val euroToRupeeRate = 89.88

    private val conversionOptions = arrayOf(
        "Euro to USD",
        "Euro to Pound",
        "Euro to Yen",
        "Euro to Rupee",

        "Pound to USD",
        "Pound to Euro",
        "Pound to Yen",
        "Pound to Rupee",

        "Rupee to USD",
        "Rupee to Euro",
        "Rupee to Yen",
        "Rupee to Pound",

        "USD to Euro",
        "USD to Pound",
        "USD to Yen",
        "USD to Rupee",

        "Yen to USD",
        "Yen to Euro",
        "Yen to Pound",
        "Yen to Rupee"
    )
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
            "Euro to USD" -> amount * euroToUsdRate
            "Euro to Pound" -> amount * euroToPoundRate
            "Euro to Yen" -> amount * euroToYenRate
            "Euro to Rupee" -> amount * euroToRupeeRate

            "Pound to USD" -> amount * poundToUsdRate
            "Pound to Euro" -> amount * poundToEuroRate
            "Pound to Yen" -> amount * poundToYenRate
            "Pound to Rupee" -> amount * poundToRupeeRate

            "Rupee to USD" -> amount * rupeeToUsdRate
            "Rupee to Euro" -> amount * rupeeToEuroRate
            "Rupee to Yen" -> amount * rupeeToYenRate
            "Rupee to Pound" -> amount * rupeeToPoundRate

            "USD to Euro" -> amount * usdToEuroRate
            "USD to Pound" -> amount * usdToPoundRate
            "USD to Yen" -> amount * usdToYenRate
            "USD to Rupee" -> amount * usdToRupeeRate

            "Yen to USD" -> amount * yenToUsdRate
            "Yen to Euro" -> amount * yenToEuroRate
            "Yen to Pound" -> amount * yenToPoundRate
            "Yen to Rupee" -> amount * yenToRupeeRate

            else -> amount // Default: No conversion
        }
    }

    private fun displayConvertedAmount(convertedAmount: Double, resultTextView: TextView) {
        resultTextView.text = getString(R.string.converted_amount, convertedAmount)
    }
}
