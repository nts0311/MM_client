package com.sonnt.moneymanagement.features.transactions.select_category_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sonnt.moneymanagement.constant.Constants
import com.sonnt.moneymanagement.data.repositories.CategoryRepository
import com.sonnt.moneymanagement.features.base.BaseViewModel

class CategorySelectFragViewModel() : BaseViewModel() {
    private val categoryType = MutableLiveData(Constants.TYPE_EXPENSE)
    val categories = Transformations.switchMap(categoryType) {
        CategoryRepository.getCategoriesByType(it)
    }
    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    fun setCategoryType(type: String) {
        categoryType.value = type
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}