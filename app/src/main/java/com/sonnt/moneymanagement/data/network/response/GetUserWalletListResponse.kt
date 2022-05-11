package com.sonnt.moneymanagement.data.network.response

import com.sonnt.moneymanagement.data.entities.Wallet

class GetUserWalletListResponse: BaseResponse<List<Wallet>>() {
    override var data: List<Wallet>? = listOf()
}