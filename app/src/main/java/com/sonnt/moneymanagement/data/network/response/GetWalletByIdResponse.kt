package com.sonnt.moneymanagement.data.network.response

import com.sonnt.moneymanagement.data.entities.Wallet

data class GetWalletByIdResponse (
    var code: Int = 0,
    var message: String = "",
    var data: Wallet? = null
)