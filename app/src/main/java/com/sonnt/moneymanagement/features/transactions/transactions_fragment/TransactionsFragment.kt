package com.sonnt.moneymanagement.features.transactions.transactions_fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.sonnt.moneymanagement.features.base.viewpager2_fragment.TabFragmentStateAdapter
import com.sonnt.moneymanagement.features.base.viewpager2_fragment.ViewPager2FragViewModel
import com.sonnt.moneymanagement.features.base.viewpager2_fragment.ViewPager2Fragment

class TransactionsFragment : ViewPager2Fragment()
{
    override val viewModel: ViewPager2FragViewModel by viewModels()
    override lateinit var viewPagerAdapter: TabFragmentStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewPagerAdapter = TransactionsFragmentStateAdapter(this)

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}