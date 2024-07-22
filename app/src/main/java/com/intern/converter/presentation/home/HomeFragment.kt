package com.intern.converter.presentation.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blongho.country_data.World
import com.intern.converter.R
import com.intern.converter.databinding.FragmentHomeBinding
import com.intern.converter.presentation.home.adapter.SpinnerAdapter
import com.intern.domain.models.ConversionResult
import com.intern.domain.models.CountryData
import com.intern.domain.models.ExchangeRate
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()
    private var failureFlag = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setupDropDownMenuAdapter(requireContext())
        setupOnClickListener()
        setupObserver()
    }

    private fun setupOnClickListener() {
        with(binding) {
            exchangeBtn.setOnClickListener {
                val base = initialCurrencySpinner.selectedItem as CountryData
                Log.d("BASE", base.currencyCode)
                viewModel.loadExchangeRates(base.currencyCode)
            }
        }
    }

    private fun setupObserver() {
        viewModel.conversion.observe(viewLifecycleOwner) { result ->
            when (result) {
                is HomeViewModel.Result.Success -> onSuccess(result.value)
                is HomeViewModel.Result.Failure -> onFailure()
            }

        }
    }

    private fun setupDropDownMenuAdapter(context: Context) {
        val countries = getCountriesList()
        val adapter = SpinnerAdapter(context, countries)

        with(binding) {
            initialCurrencySpinner.adapter = adapter
            desiredCurrencySpinner.adapter = adapter
            initialCurrencySpinner.setSelection(0) // set default currencies for spinners as USD and EUR positions are known in advance
            desiredCurrencySpinner.setSelection(1)
        }
    }

    private fun onSuccess(conversionResult: ConversionResult) {
        with(binding) {
            val desired = desiredCurrencySpinner.selectedItem as CountryData
            val initial = initialCurrencySpinner.selectedItem as CountryData
            var amount = moneyEditText.text.toString()
            if (amount.isEmpty() || !amount.isDigitsOnly()) amount = "1"
            val rate = conversionResult.conversionRates.getValue(desired.currencyCode)
            val exchangeRate = ExchangeRate(
                rate,
                amount,
                conversionResult.timeLastUpdateUtc,
                conversionResult.timeNextUpdateUtc,
                initial.currencyCode,
                desired.currencyCode
            )
            Log.d("HELLO", exchangeRate.toString())

            navigateToNextScreen(exchangeRate)
        }
    }

    private fun onFailure() {
        with(binding) {
            noInternetLayout.visibility = View.VISIBLE
            exchangeBtn.isEnabled = false
            moneyEditText.isEnabled = false
        }
        failureFlag = true
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onConnectionRestored()
            }
        })
    }

    private fun getCountriesList(): MutableList<CountryData> {
        World.init(context)

        val countriesData: MutableList<CountryData> = mutableListOf()
        val toExcludeCodesList = listOf(
            "EUR", "USD", "AUD", "AQD", "BYR", "CYP", "ECS", "EEK", "KPW",
            "LTL", "MRO", "MTL", "SKK", "SSD", "STD", "ZMK", "ZWD"
        )
        val worldCountries = World.getAllCountries().filter { // filtering repeated and non-existing codes
            !toExcludeCodesList.contains(it.currency.code)
        }
        worldCountries.forEach {
            countriesData.add(CountryData(it.name, it.currency.code, it.flagResource))
        }

        val australia = World.getCountryFrom("AU") // adding corrected currencies back
        countriesData.add(CountryData(
            australia.name, australia.currency.code, australia.flagResource))
        val belarus = World.getCountryFrom("BY")
        countriesData.add(CountryData(
            belarus.name, "BYN", belarus.flagResource))

        countriesData.sortBy { it.currencyCode } // USD and EUR should be on the top of the list as the most common currencies

        // adding USA and EU and their currencies back because they were previously excluded
        val usa = World.getCountryFrom("US")
        countriesData.add(0, CountryData(
            usa.name, usa.currency.code, R.drawable.us_flag))
        countriesData.add(1, CountryData(
            "European Union", "EUR", R.drawable.eu_flag))

        return countriesData
    }

    private fun onConnectionRestored() {
        if (failureFlag) {
            activity?.runOnUiThread {
                with(binding) {
                    noInternetLayout.visibility = View.GONE
                    exchangeBtn.isEnabled = true
                    moneyEditText.isEnabled = true
                    failureFlag = false
                }
            }
        }
    }

    private fun navigateToNextScreen(rateSafeArg: ExchangeRate) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(rateSafeArg)
        findNavController().navigate(action)
    }

}