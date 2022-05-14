package com.sonnt.moneymanagement.data.network.datasources

import com.sonnt.moneymanagement.features.login.LoginActivity
import retrofit2.Response

suspend fun <T> httpRequest(call: suspend () -> Response<T>): T? {
    val response = call()

    return if(response.isSuccessful) {
        response.body()
    }
    else {
        if (response.code() == 401 || response.code() == 403) {

            LoginActivity.logout()
        }
        null
    }
}
