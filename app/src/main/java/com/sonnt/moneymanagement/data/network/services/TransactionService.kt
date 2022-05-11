package com.sonnt.moneymanagement.data.network.services

import com.sonnt.moneymanagement.data.network.request.CreateTransactionRequest
import com.sonnt.moneymanagement.data.network.request.GetTransactionBetweenDateRequest
import com.sonnt.moneymanagement.data.network.response.GetTransactionBetweenDateResponse
import com.sonnt.moneymanagement.data.network.response.GetTransactionByIdResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface TransactionService {
    @POST("transaction/get-between-date-of-wallet")
    suspend fun getTransactionsBetweenDateOfWallet(@Body body: GetTransactionBetweenDateRequest): Response<GetTransactionBetweenDateResponse>

    @POST("transaction/get-between-date")
    suspend fun getTransactionsBetweenDate(
        @Query("start") start: Long,
        @Query("end") end: Long
    ): Response<GetTransactionBetweenDateResponse>

    @POST("transaction/get-by-id")
    suspend fun getTransactionsById(@Query("transactionId") id: Long): Response<GetTransactionByIdResponse>

    @POST("transaction/create")
    suspend fun createTransaction(@Body body: CreateTransactionRequest): Response<*>
}