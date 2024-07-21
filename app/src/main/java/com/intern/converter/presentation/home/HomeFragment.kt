package com.intern.converter.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.blongho.country_data.World
import com.intern.converter.R
import com.intern.converter.databinding.FragmentHomeBinding
import com.intern.converter.presentation.home.adapter.SpinnerAdapter
import com.intern.domain.models.CountryData

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setDropDownMenuAdapter(requireContext())
    }

    private fun setDropDownMenuAdapter(context: Context) {
        val countries = getCountriesList()
        val adapter = SpinnerAdapter(context, countries)

        binding.initialCurrencySpinner.adapter = adapter
        binding.desiredCurrencySpinner.adapter = adapter
        binding.initialCurrencySpinner.setSelection(0) // set default currencies for spinners as USD and EUR positions are known in advance
        binding.desiredCurrencySpinner.setSelection(1)
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

}