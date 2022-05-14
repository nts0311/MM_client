package com.sonnt.moneymanagement.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.sonnt.moneymanagement.MMApplication
import com.sonnt.moneymanagement.data.entities.Wallet
import com.sonnt.moneymanagement.data.network.datasources.WalletDataSource
import com.sonnt.moneymanagement.data.network.datasources.httpRequest
import com.sonnt.moneymanagement.data.network.request.CreateWalletRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

object WalletRepository {
    private val walletDatasource = WalletDataSource()//AppDatabase.getInstance(MMApplication.self).walletDao

    private var _walletsMap: MutableMap<Long, Wallet> = mutableMapOf()
    val walletMap: Map<Long, Wallet> = _walletsMap

    private val scope = CoroutineScope(Dispatchers.Main)

//    private var _currentWalletId: MutableLiveData<Long> = MutableLiveData()
//    var currentWallet: LiveData<Wallet> = Transformations.switchMap(_currentWalletId)
//    {
//        walletDatasource.getWalletById(it).asLiveData()
//    }

    var currentWallet = MutableLiveData<Wallet>()

    var job: Job? = null

    fun setCurrentWallet(walletId: Long) {



        currentWallet.value = _walletsMap[walletId]
    }

    fun refreshCurrentWallet() {
        if (currentWallet.value!=null && job == null)
        {
            job = scope.launch {
                getWalletById(currentWallet.value!!.id).collect {
                    if (it != null) {
                        currentWallet.value = it
                    }

                    job = null
                }
            }
        }
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
        val response = walletDatasource.insertWallet(wallet)
        return response
    }

    suspend fun updateWallet(wallet: Wallet): Int {
        return 200
    }

    suspend fun deleteWallet(wallet: Wallet): Int {
        walletDatasource.deleteWallet(wallet)

        return 200
    }

    fun getWalletById(id: Long) = walletDatasource.getWalletById(id)

    fun getWallets() = walletDatasource.getWallets().map {
        updateWalletData(it)
        it
    }
}