package com.sonnt.moneymanagement.data.mm_context

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sonnt.moneymanagement.constant.TimeRange
import com.sonnt.moneymanagement.constant.ViewType
import com.sonnt.moneymanagement.data.entities.Transaction
import com.sonnt.moneymanagement.data.entities.Wallet
import com.sonnt.moneymanagement.features.main_activity.TabInfo

//MM specific context

object MMContext {
    var currentPage: Int = 0

    private var _tabInfoList = MutableLiveData<List<TabInfo>>()
    var tabInfoList: LiveData<List<TabInfo>> = _tabInfoList

    fun setTabInfoList(list: List<TabInfo>) {
        _tabInfoList.value = list
    }


    private var _timeRange = MutableLiveData<TimeRange>()
    var timeRange: LiveData<TimeRange> = _timeRange

    fun setTimeRange(timeRange: TimeRange) {
        _timeRange.value = timeRange
    }

    var viewMode = MutableLiveData(ViewType.TRANSACTION)

    var addedTransaction = MutableLiveData<Transaction?>()
    var addedWallet = MutableLiveData<Wallet?>()
}