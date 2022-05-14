package com.sonnt.moneymanagement.data.network.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.sonnt.moneymanagement.data.entities.Transaction
import com.sonnt.moneymanagement.data.network.NetworkModule
import com.sonnt.moneymanagement.data.network.request.CreateTransactionRequest
import com.sonnt.moneymanagement.data.network.request.DeleteTransactionRequest
import com.sonnt.moneymanagement.data.network.request.GetTransactionBetweenDateRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionDatasource {
    private val transactionService = NetworkModule.transactionService

    fun getTransaction(id: Long): LiveData<Transaction?> = flow {
        val response = httpRequest { transactionService.getTransactionsById(id) }
        emit(response?.data)
    }.asLiveData()

    fun getTransactionsBetweenRangeOfWallet(
        start: Long,
        end: Long,
        walletId: Long
    ): Flow<List<Transaction>> = flow {
        // No paging lol
        val body = GetTransactionBetweenDateRequest(walletId, start, end, 0, 10000)
        val response = httpRequest { transactionService.getTransactionsBetweenDateOfWallet(body) }
        emit(response?.data?.data ?: listOf())
    }

    fun getTransactionsBetweenRange(start: Long, end: Long): Flow<List<Transaction>> =
        flow {
            val response = httpRequest { transactionService.getTransactionsBetweenDate(start, end) }
            emit(response?.data?.data ?: listOf())
        }

    fun updateTransaction(transaction: Transaction): Int {
        return 200
    }

    suspend fun insertTransaction(transaction: Transaction): Int {
        val body = CreateTransactionRequest(
            transaction.categoryId,
            transaction.walletId,
            transaction.type,
            transaction.amount,
            transaction.note,
            transaction.date
        )

        val response = transactionService.createTransaction(body)

        return response.code()
    }

    suspend fun deleteTransaction(transaction: Transaction): Int {
        val body = DeleteTransactionRequest(
            transactionId = transaction.id,
            walletId = transaction.walletId,
            amount = transaction.amount.toDouble()
        )

        httpRequest { transactionService.deleteTransaction(body) }

        return 0
    }
}