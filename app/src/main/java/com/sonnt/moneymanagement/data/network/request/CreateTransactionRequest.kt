package com.sonnt.moneymanagement.data.network.request

data class CreateTransactionRequest(
    var categoryId: Long,
    var walletId: Long,
    var type: String,
    var amount: Long,
    var note: String,
    var date: Long
)