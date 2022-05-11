package com.sonnt.moneymanagement.data.network.response

import com.sonnt.moneymanagement.data.entities.Wallet

data class GetUserWalletListResponse (
    var code: Int = 0,
    var message: String = "",
    var data: List<Wallet>? = listOf()
)