package com.sonnt.moneymanagement.data.entities

import com.sonnt.moneymanagement.R

data class Category(
    var id:Long,
    var parentId :Long,
    var name: String,
    var type : String = "",
    var imageId:Int = R.drawable.ic_category_foodndrink
) {

}