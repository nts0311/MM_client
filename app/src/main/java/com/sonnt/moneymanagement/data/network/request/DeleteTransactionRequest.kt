package com.sonnt.moneymanagement.data.network.request

data class DeleteTransactionRequest(
    var transactionId: Long,
    var walletId: Long,
    var amount: Double
)