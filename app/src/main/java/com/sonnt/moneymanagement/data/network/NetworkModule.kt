package com.sonnt.moneymanagement.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sonnt.moneymanagement.data.network.services.AuthService
import com.sonnt.moneymanagement.data.network.services.CategoryService
import com.sonnt.moneymanagement.data.network.services.TransactionService
import com.sonnt.moneymanagement.data.network.services.WalletService
import com.sonnt.moneymanagement.data.repositories.AuthRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    var moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://4cd5-2402-9d80-22a-b2af-f98d-fa9c-8514-e55f.ap.ngrok.io")
        .client(OkHttpClient.Builder().addInterceptor {chain ->
            val token = AuthRepository.jwtToken
            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${token}").build()
            chain.proceed(request)
        }.build())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    var transactionService: TransactionService = retrofit.create(TransactionService::class.java)
    var walletService: WalletService = retrofit.create(WalletService::class.java)
    var categoryService: CategoryService = retrofit.create(CategoryService::class.java)
    var authService: AuthService = retrofit.create(AuthService::class.java)
}