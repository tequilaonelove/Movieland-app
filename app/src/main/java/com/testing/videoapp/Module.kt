package com.testing.videoapp

import com.testing.videoapp.data.api.provideReviewsApi
import com.testing.videoapp.data.provideOkHttpClient
import com.testing.videoapp.data.provideRetrofit
import com.testing.videoapp.data.repository.provideReviewsRepository
import com.testing.videoapp.viewmodel.ReviewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModules = module {
    viewModel { ReviewsViewModel(get()) }
}

private val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideRetrofit(get()) }
}

private val repositoryModule = module {
    single { provideReviewsApi(get()) }
    single { provideReviewsRepository(get()) }
}

val appModules = listOf(networkModule, repositoryModule, viewModules)