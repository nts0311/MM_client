package com.sonnt.moneymanagement.data.network.services

import com.sonnt.moneymanagement.data.network.response.GetAllCategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {
    @GET("category/get-all")
    suspend fun getAllCategories(): Response<GetAllCategoryResponse>
}