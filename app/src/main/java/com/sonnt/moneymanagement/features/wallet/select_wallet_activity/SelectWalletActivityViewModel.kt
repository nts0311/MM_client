package com.sonnt.moneymanagement.features.wallet.select_wallet_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.moneymanagement.data.entities.Wallet
import com.sonnt.moneymanagement.data.repositories.WalletRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SelectWalletActivityViewModel() : ViewModel()
{
    var walletList = MutableLiveData<List<Wallet>>()

    fun getWalletList() {
        viewModelScope.launch {
            WalletRepository.getWallets().collect {
                walletList.value = it
            }
        }

    }

    //val walletList = WalletRepository.getWallets().asLiveData()

    fun selectWallet(walletId: Long) {
        WalletRepository.setCurrentWallet(walletId)
    }
}