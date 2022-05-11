package com.sonnt.moneymanagement.data.network.response

import com.sonnt.moneymanagement.data.entities.Transaction

data class GetTransactionByIdResponse (
    var code: Int = 0,
    var message: String = "",
    var data: Transaction? = null
)