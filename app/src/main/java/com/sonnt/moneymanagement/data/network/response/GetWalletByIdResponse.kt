package com.sonnt.moneymanagement.data.network.response

import com.sonnt.moneymanagement.data.entities.Wallet

class GetWalletByIdResponse: BaseResponse<Wallet>() {
    override var data: Wallet? = null
}