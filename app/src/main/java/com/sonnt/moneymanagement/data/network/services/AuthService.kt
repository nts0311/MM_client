package com.sonnt.moneymanagement.data.network.services

import com.sonnt.moneymanagement.data.network.request.AuthRequest
import com.sonnt.moneymanagement.data.network.response.AuthenticationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body body: AuthRequest): Response<AuthenticationResponse>
}