package com.sonnt.moneymanagement.data.network.response

import com.sonnt.moneymanagement.data.entities.Category

class GetAllCategoryResponse: BaseResponse<List<Category>>() {
    override var data: List<Category>? = listOf()
}