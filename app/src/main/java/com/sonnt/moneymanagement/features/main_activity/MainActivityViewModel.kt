package com.sonnt.moneymanagement.features.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonnt.moneymanagement.R
import com.sonnt.moneymanagement.constant.TimeRange
import com.sonnt.moneymanagement.constant.ViewType
import com.sonnt.moneymanagement.data.repositories.WalletRepository
import com.sonnt.moneymanagement.data.entities.Category
import com.sonnt.moneymanagement.data.entities.Wallet
import com.sonnt.moneymanagement.data.mm_context.MMContext
import com.sonnt.moneymanagement.data.repositories.CategoryRepository
import com.sonnt.moneymanagement.utils.toEpoch
import com.sonnt.moneymanagement.utils.toLocalDate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.absoluteValue

class MainActivityViewModel() : ViewModel() {
    var startTime: Long = 0L
        private set
    var endTime: Long = System.currentTimeMillis()
        private set

    var currentWallet: LiveData<Wallet> = WalletRepository.currentWallet

    private var timeRange = TimeRange.MONTH

    //determine if the tabs has been initialized and displayed
    var initTabs = false
    //var initFirstWallet = false

    init {
        initTimeRange()
        initData()
        //WalletRepository.setCurrentWallet(1L)
    }

    private fun initTimeRange() {

        val ld = toLocalDate(endTime)

        startTime = toEpoch(
            LocalDate.of(ld.year, ld.monthValue, 1)
                .minusMonths(18)
        )
    }

    fun initData() {
        viewModelScope.launch {
            WalletRepository.getWallets().collect {
                WalletRepository.updateWalletData(it)

                if (it.isNotEmpty()) {
                    WalletRepository.setCurrentWallet(it.first().id)
                }
            }

            CategoryRepository.getCategories().collect {
                CategoryRepository.updateCategoriesMap(it)
            }
        }
    }

    fun updateCategories(categories: List<Category>) {
        //repository.updateCategoriesMap(categories)
    }

    fun getTabInfoList() {
        if (currentWallet.value == null)
            return

        viewModelScope.launch {
            val tabInfoList = TabInfoUtils.getTabInfoList(startTime,endTime,timeRange,currentWallet.value!!.id)
                .first()
            MMContext.setTabInfoList(tabInfoList)
        }
    }

    fun onSelectCustomTimeRange(start: Long, end: Long) {
        if (startTime == start && endTime == end)
            return

        startTime = start
        endTime = end
        timeRange = TimeRange.CUSTOM
        MMContext.setTimeRange(TimeRange.CUSTOM)
        getTabInfoList()
    }

    //not include CUSTOM range
    fun onTimeRangeChanged(timeRange: TimeRange) {
        if (timeRange.value == this.timeRange.value)
            return

        if (this.timeRange == TimeRange.CUSTOM && timeRange != TimeRange.CUSTOM)
            initTimeRange()

        this.timeRange = timeRange
        MMContext.setTimeRange(timeRange)
        getTabInfoList()
    }

    fun switchViewMode(): ViewType {
        val currentViewMode = MMContext.viewMode

        val newViewMode = if (currentViewMode.value == ViewType.TRANSACTION)
            ViewType.CATEGORY
        else
            ViewType.TRANSACTION

        currentViewMode.value = newViewMode
        return newViewMode
    }
}