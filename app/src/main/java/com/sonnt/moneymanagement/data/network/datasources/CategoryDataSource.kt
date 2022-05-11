package com.sonnt.moneymanagement.data.network.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.sonnt.moneymanagement.data.entities.Category
import com.sonnt.moneymanagement.data.network.NetworkModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CategoryDataSource {

    private val categoryService = NetworkModule.categoryService

    fun getCategories(): LiveData<List<Category>> = getCategoriesFlow().asLiveData()

    fun getCategoriesFlow(): Flow<List<Category>> = flow {
        val response = httpRequest { categoryService.getAllCategories() }
        emit(response?.data ?: listOf())
    }

    fun getCategoriesByType(type: String): LiveData<List<Category>> =
        getCategoriesFlow().map { it.filter { category -> category.type == type } }
            .asLiveData()

    fun insertCategory(category: Category) {

    }
}