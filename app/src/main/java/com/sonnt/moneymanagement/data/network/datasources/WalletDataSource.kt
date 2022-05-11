package com.sonnt.moneymanagement.data.network.datasources

import com.sonnt.moneymanagement.data.entities.Wallet
import com.sonnt.moneymanagement.data.network.NetworkModule
import com.sonnt.moneymanagement.data.network.request.CreateWalletRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



class WalletDataSource {
    private val walletService = NetworkModule.walletService

    fun getWallets(): Flow<List<Wallet>> = flow {
        val response = httpRequest { walletService.getUserWalletList() }
        emit(response?.data ?: listOf())
    }

    fun getWalletById(id: Long): Flow<Wallet?> = flow {
        val response = httpRequest { walletService.getWalletById(id) }
        emit(response?.data)
    }

    suspend fun insertWallet(wallet: Wallet): Int {
        val body = CreateWalletRequest(userId = 1, name = wallet.name, "", amount = wallet.amount, currency = wallet.currency)
        val response = walletService.createWallet(body)

        return response.code()
    }

    fun updateWallet(wallet: Wallet) {

    }

    fun deleteWallet(wallet: Wallet) {

    }
}