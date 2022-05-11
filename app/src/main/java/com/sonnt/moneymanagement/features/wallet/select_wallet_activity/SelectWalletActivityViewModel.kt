package com.sonnt.moneymanagement.features.wallet.select_wallet_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sonnt.moneymanagement.data.repositories.WalletRepository

class SelectWalletActivityViewModel() : ViewModel()
{
    val walletList = WalletRepository.getWallets().asLiveData()

    fun selectWallet(walletId: Long) {
        WalletRepository.setCurrentWallet(walletId)
    }
}