package com.sonnt.moneymanagement.data.entities

import com.sonnt.moneymanagement.R

data class Wallet(
    var id:Long,
    var name:String,
    var imageId:Int = R.drawable.icon,
    var amount: Long,
    var currency: String
) {

}