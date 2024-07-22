package com.intern.converter.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.intern.converter.R
import com.intern.converter.databinding.FragmentDetailBinding
import com.intern.domain.models.ExchangeRate

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setData(getDataFromSafeArgs())
        return binding.root
    }

    private fun setData(exchangeRate: ExchangeRate) {
        with(binding) {
            initialCurrencyCode.text = exchangeRate.baseCurrency
            desiredCurrencyCode.text = exchangeRate.desiredCurrency
            initialAmount.text = exchangeRate.amount
            val convertedMoney = (exchangeRate.amount.toDouble() * exchangeRate.rate).toString()
            exchangedAmount.text = convertedMoney
            lastUpdate.text = exchangeRate.timeLastUpdateUtc.substringBefore('+')
            nextUpdate.text = exchangeRate.timeNextUpdateUtc.substringBefore('+')
        }
    }

    private fun getDataFromSafeArgs(): ExchangeRate {
        val args: DetailFragmentArgs by navArgs()
        return args.ExchangeRate
    }

}