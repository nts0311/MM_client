package com.sonnt.moneymanagement.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.sonnt.moneymanagement.MMApplication
import com.sonnt.moneymanagement.data.entities.Wallet
import com.sonnt.moneymanagement.data.network.datasources.WalletDataSource
import com.sonnt.moneymanagement.data.network.request.CreateWalletRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

object WalletRepository {
    private val walletDatasource = WalletDataSource()//AppDatabase.getInstance(MMApplication.self).walletDao

    private var _walletsMap: MutableMap<Long, Wallet> = mutableMapOf()
    val walletMap: Map<Long, Wallet> = _walletsMap

    private val scope = CoroutineScope(Dispatchers.Default)

    private var _currentWalletId: MutableLiveData<Long> = MutableLiveData()
    var currentWallet: LiveData<Wallet> = Transformations.switchMap(_currentWalletId)
    {
        walletDatasource.getWalletById(it).asLiveData()
    }

    fun setCurrentWallet(walletId: Long) {
        _currentWalletId.value = walletId
    }

    init {
//        scope.launch {
//            val walletList = walletDatasource.getWallets().first()
//
//            for (wallet in walletList) {
//                _walletsMap[wallet.id] = wallet
//            }
//        }
    }

    fun updateWalletData(walletList: List<Wallet>) {
        for (wallet in walletList) {
            _walletsMap[wallet.id] = wallet
        }
    }

    suspend fun insertWallet(wallet: Wallet): Int {
        return walletDatasource.insertWallet(wallet)
    }

    suspend fun updateWallet(wallet: Wallet): Int {
        return 200
    }

    suspend fun deleteWallet(wallet: Wallet): Int {
        return 200
    }

    fun getWalletById(id: Long) = walletDatasource.getWalletById(id)

    fun getWallets() = walletDatasource.getWallets()
}