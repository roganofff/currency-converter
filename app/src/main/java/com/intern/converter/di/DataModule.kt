package com.intern.converter.di

import com.intern.data.repository.Repository
import com.intern.domain.repository.RepositoryInterface
import org.koin.dsl.module

val dataModule = module {
    single<RepositoryInterface> { Repository() }
}