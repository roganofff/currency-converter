package com.intern.converter.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.blongho.country_data.Country
import com.intern.converter.R
import com.intern.converter.databinding.SpinnerItemBinding

class SpinnerAdapter(
    private val context: Context,
    private val countries: List<Country>
) : ArrayAdapter<Country>(context, R.layout.spinner_item, countries) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getSpinnerItem(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getSpinnerItem(position, convertView, parent)
    }

    private fun getSpinnerItem(position: Int, convertView: View?, parent: ViewGroup): View {
        var spinnerItem: View? = convertView
        if (spinnerItem == null) {
            spinnerItem = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        }

        val binding = SpinnerItemBinding.bind(spinnerItem!!)

        binding.itemCountryTv.text = countries[position].currency.code
        binding.itemCountryImage.setImageResource(countries[position].flagResource)

        return spinnerItem
    }
}