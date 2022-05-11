package com.sonnt.moneymanagement.data.network.response

import com.sonnt.moneymanagement.data.entities.Transaction

class GetTransactionByIdResponse: BaseResponse<Transaction>() {
    override var data: Transaction? = null
}