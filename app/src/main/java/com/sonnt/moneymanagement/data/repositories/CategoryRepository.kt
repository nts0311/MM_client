package com.sonnt.moneymanagement.data.repositories

import androidx.lifecycle.LiveData
import com.sonnt.moneymanagement.data.entities.Category
import com.sonnt.moneymanagement.data.network.datasources.CategoryDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

object CategoryRepository {

    private val categoryDataSource = CategoryDataSource()

    private var _categoriesMap: MutableMap<Long, Category> = mutableMapOf()
    var categoryMap: Map<Long, Category> = _categoriesMap

    init {
        CoroutineScope(Dispatchers.Default).launch {
            val walletList = categoryDataSource.getCategoriesFlow().first()

            for (category in walletList) {
                _categoriesMap[category.id] = category
            }
        }
    }

    fun updateCategoriesMap(categories: List<Category>) {
        for (category in categories) {
            if (!_categoriesMap.containsKey(category.id))
                _categoriesMap[category.id] = category
        }
    }

    fun getCategoriesLiveData(): LiveData<List<Category>> = categoryDataSource.getCategories()

    fun getCategoriesByType(type: String) = categoryDataSource.getCategoriesByType(type)
}