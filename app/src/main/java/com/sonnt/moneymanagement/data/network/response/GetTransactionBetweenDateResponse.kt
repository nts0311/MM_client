package com.sonnt.moneymanagement.data.network.response

import com.sonnt.moneymanagement.data.entities.Transaction

data class GetTransactionBetweenDateResponse (
    var code: Int = 0,
    var message: String = "",
    var data: TransactionPage? = null
)

data class TransactionPage(
    var page: Int = 0,
    var size: Int = 0,
    var data: List<Transaction> = listOf()
)