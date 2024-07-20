package com.intern.converter.presentation.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.blongho.country_data.Country
import com.blongho.country_data.World
import com.intern.converter.R
import com.intern.converter.databinding.FragmentHomeBinding
import com.intern.converter.presentation.home.adapter.SpinnerAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setDropDownMenuLists(requireContext())
    }

    private fun setDropDownMenuLists(context: Context) {
        World.init(context)
        val countries = World.getAllCountries()
        val adapter = SpinnerAdapter(context, countries)
//        getCountriesList()
        binding.initialCurrencySpinner.adapter = adapter
        binding.desiredCurrencySpinner.adapter = adapter
    }

//    private fun getCountriesList(): MutableList<Country> {
//        World.init(context)
//        val countries = World.getAllCountries()
//        countries.forEach {
//            Log.d("WORLD", "${it.id}, ${it.currency.name}, ${it.currency.country}, ${it.currency.code}")
//        }
//        return countries
//    }

}