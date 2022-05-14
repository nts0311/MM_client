package com.android.walletforest.transaction_detail_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.moneymanagement.data.repositories.CategoryRepository
import com.sonnt.moneymanagement.data.repositories.TransactionRepository
import com.sonnt.moneymanagement.data.repositories.WalletRepository
import com.sonnt.moneymanagement.data.entities.Transaction
import com.sonnt.moneymanagement.data.mm_context.MMContext
import com.sonnt.moneymanagement.features.base.BaseViewModel
import kotlinx.coroutines.launch

class TransactionDetailViewModel() : BaseViewModel() {
    private val currentId = 0L
    var transaction: LiveData<Transaction?> = MutableLiveData()
    val categories = CategoryRepository.categoryMap
    val wallets = WalletRepository.walletMap

    fun setTransactionId(id: Long) {
        if (currentId == id) return
        transaction = TransactionRepository.getTransaction(id)
    }

    fun updateTransaction(transaction: Transaction) {
        if (transaction == this.transaction.value)
            return

        TransactionRepository.updateTransaction(transaction, this.transaction.value!!)
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            val responseCode = TransactionRepository.insertTransaction(transaction)
            WalletRepository.refreshCurrentWallet()
        }

    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            TransactionRepository.deleteTransaction(transaction)
            WalletRepository.refreshCurrentWallet()
        }

    }
}