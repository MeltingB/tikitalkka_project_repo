package com.meltingb.tikitalkka.base

import com.meltingb.tikitalkka.viewmodel.HomeViewModel
import com.meltingb.tikitalkka.viewmodel.PickViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { PickViewModel() }
}

val baseModules = listOf(viewModelModule)