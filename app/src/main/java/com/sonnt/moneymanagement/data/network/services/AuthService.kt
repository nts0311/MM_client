package com.sonnt.moneymanagement.data.network.services

import com.sonnt.moneymanagement.data.network.request.AuthRequest
import com.sonnt.moneymanagement.data.network.request.UpdateFcmTokenRequest
import com.sonnt.moneymanagement.data.network.response.AuthenticationResponse
import com.sonnt.moneymanagement.data.network.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body body: AuthRequest): Response<AuthenticationResponse>

    @POST("auth/update-fcm-token")
    suspend fun updateFcmToken(@Body body: UpdateFcmTokenRequest): Response<BaseResponse<*>>
}