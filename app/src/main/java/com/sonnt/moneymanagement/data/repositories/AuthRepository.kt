package com.sonnt.moneymanagement.data.repositories

import com.sonnt.moneymanagement.data.network.NetworkModule
import com.sonnt.moneymanagement.data.network.datasources.httpRequest
import com.sonnt.moneymanagement.data.network.request.AuthRequest
import com.sonnt.moneymanagement.data.network.response.AuthInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object AuthRepository {
    var jwtToken: String = ""
        private set
    var userId: Long = 0L
        private set

    private val authService = NetworkModule.authService

    suspend fun login(username: String, password: String): Flow<AuthInfo?> = flow {
        val body = AuthRequest(username, password)
        val response = httpRequest { authService.login(body) }

        if(response?.data != null) {
            jwtToken = response.data!!.jwtToken
            userId = response.data!!.userId
        }

        emit(response?.data)
    }
}