package com.sonnt.moneymanagement.data.repositories


import com.sonnt.moneymanagement.MMApplication
import com.sonnt.moneymanagement.constant.Constants
import com.sonnt.moneymanagement.constant.TimeRange
import com.sonnt.moneymanagement.data.entities.Transaction
import com.sonnt.moneymanagement.data.network.datasources.TransactionDatasource
import com.sonnt.moneymanagement.features.report.report_record_fragment.ChartEntryGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object TransactionRepository {
    //private val appDatabase = AppDatabase.getInstance(MMApplication.self)

    private val transactionDataSource = TransactionDatasource()

    //caching list of transactions of each wallet, avoiding database query
    private var fetchedRange: MutableMap<String, Flow<List<Transaction>>> = mutableMapOf()

    fun getTransaction(id: Long) = transactionDataSource.getTransaction(id)

    //Get the list of transactions in a specific wallet and specific period
    //If the list is not cached, fetch the list from database and cache it
    fun getTransactionsBetweenRange(
        start: Long,
        end: Long,
        walletId: Long
    ): Flow<List<Transaction>> {

        val key: String = if (walletId == 1L)
            "all-$start-$end"
        else
            "$walletId-$start-$end"

        return if (fetchedRange.containsKey(key))
            fetchedRange[key]!!
        else {
            val transactions =
//                if (walletId == 1L)
//                    transactionDataSource.getTransactionsBetweenRange(start, end)
//                else
                    transactionDataSource.getTransactionsBetweenRangeOfWallet(
                        start,
                        end,
                        walletId
                    )



            fetchedRange[key] = transactions.distinctUntilChanged()

            transactions
        }
    }

    suspend fun insertTransaction(transaction: Transaction): Int  {
        return transactionDataSource.insertTransaction(transaction)
    }
    suspend fun deleteTransaction(transaction: Transaction): Int {
        return transactionDataSource.deleteTransaction(transaction)
    }

    fun updateTransaction(newTransaction: Transaction, oldTransaction: Transaction) {

    }

    fun getBarData(start: Long, end: Long, walletId: Long, timeRange: TimeRange) =
        getTransactionsBetweenRange(start, end, walletId)
            .map { ChartEntryGenerator.getBarChartData(it, start, end, timeRange) }
            .flowOn(Dispatchers.Default)

    fun getPieEntries(
        start: Long,
        end: Long,
        walletId: Long,
        excludeSubCate: Boolean
    ) =
        getTransactionsBetweenRange(start, end, walletId)
            .map { ChartEntryGenerator.getPieEntries(it, excludeSubCate, MMApplication.self) }
            .flowOn(Dispatchers.Default)
}