package com.sonnt.moneymanagement.features.report.report_record_fragment

import androidx.lifecycle.*
import com.sonnt.moneymanagement.constant.TimeRange
import com.sonnt.moneymanagement.data.repositories.TransactionRepository
import com.sonnt.moneymanagement.data.repositories.WalletRepository
import com.sonnt.moneymanagement.features.report.common.BarChartData
import com.sonnt.moneymanagement.features.report.common.PieChartData

class ReportRecordViewModel() : ViewModel() {

    var barData: LiveData<List<BarChartData>> = MutableLiveData()
    var pieChartData: LiveData<PieChartData> = MutableLiveData()

    var currentWallet = WalletRepository.currentWallet
    var excludeSubCate = false

    fun setTimeRange(startTime: Long, endTime: Long, timeRange: String, walletId: Long) {
        barData = TransactionRepository.getBarData(startTime, endTime, walletId, TimeRange.valueOf(timeRange)).asLiveData()
    }

    fun getPieEntries(startTime: Long, endTime: Long, walletId: Long) {
        pieChartData =
            TransactionRepository.getPieEntries(startTime, endTime, walletId, excludeSubCate).asLiveData()
    }
}