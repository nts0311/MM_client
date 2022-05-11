package com.sonnt.moneymanagement.data.network.response

import com.sonnt.moneymanagement.data.entities.Category

data class GetAllCategoryResponse(
    var code: Int = 0,
    var message: String = "",
    var data: List<Category>? = listOf()
)