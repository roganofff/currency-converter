package com.intern.converter.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intern.domain.models.ConversionResult
import com.intern.domain.usecases.ExchangeRatesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class HomeViewModel(
    private val exchangeRatesUseCase: ExchangeRatesUseCase
) : ViewModel() {

    private var coroutineExceptionHandler: CoroutineExceptionHandler

    private var _conversion: MutableLiveData<Result<ConversionResult>> = MutableLiveData()
    val conversion: LiveData<Result<ConversionResult>> get() = _conversion

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _conversion.value = Result.Failure(throwable)
        }
        loadExchangeRates("USD")
    }

    fun getExchangeRates(
        amount: String,
        baseCurrency: String,
        desiredCurrency: String
    ) {

        loadExchangeRates(baseCurrency)
    }

    private fun loadExchangeRates(baseCurrency: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _conversion.value = Result.Success(exchangeRatesUseCase.getConversion(baseCurrency = baseCurrency))
        }
    }

    sealed class Result<out T : Any> {
        data class Success<out T : Any>(val value: T) : Result<T>()
        data class Failure(val throwable: Throwable) : Result<Nothing>()
    }

}