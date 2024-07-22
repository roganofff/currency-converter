package com.intern.converter.di

import com.intern.converter.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            exchangeRatesUseCase = get(),
        )
    }
}