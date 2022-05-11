package com.sonnt.moneymanagement.data.network.services

import com.sonnt.moneymanagement.data.network.request.CreateWalletRequest
import com.sonnt.moneymanagement.data.network.response.GetUserWalletListResponse
import com.sonnt.moneymanagement.data.network.response.GetWalletByIdResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WalletService {
    @GET("wallet/list")
    suspend fun getUserWalletList(): Response<GetUserWalletListResponse>

    @GET("wallet/get-by-id")
    suspend fun getWalletById(@Query("walletId") walletId: Long): Response<GetWalletByIdResponse>

    @POST("wallet/create")
    suspend fun createWallet(@Body cody: CreateWalletRequest): Response<*>
}