package com.sonnt.moneymanagement.features.main_activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.messaging.FirebaseMessaging
import com.sonnt.moneymanagement.R
import com.sonnt.moneymanagement.constant.TimeRange
import com.sonnt.moneymanagement.constant.ViewType
import com.sonnt.moneymanagement.data.network.NetworkModule
import com.sonnt.moneymanagement.data.network.request.UpdateFcmTokenRequest
import com.sonnt.moneymanagement.data.repositories.AuthRepository
import com.sonnt.moneymanagement.databinding.ActivityMainBinding
import com.sonnt.moneymanagement.features.base.BaseActivity
import com.sonnt.moneymanagement.features.login.LoginActivity
import com.sonnt.moneymanagement.features.premium.PayPalActivity
import com.sonnt.moneymanagement.features.report.report_fragment.ReportFragment
import com.sonnt.moneymanagement.features.transactions.transaction_detail_activity.TransactionDetailActivity
import com.sonnt.moneymanagement.features.transactions.transactions_fragment.TransactionsFragment
import com.sonnt.moneymanagement.features.wallet.select_wallet_activity.SelectWalletActivity
import com.sonnt.moneymanagement.utils.NumberFormatter
import com.sonnt.moneymanagement.utils.createRangeSelectDialog
import com.sonnt.moneymanagement.utils.showRangeSelectDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    private var fragmentTransactions = TransactionsFragment()
    private var fragmentReport = ReportFragment()
    private var activeFragment: Fragment = fragmentTransactions
    private var rangeSelectDialog: AlertDialog? = null

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

        } else {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initScreen()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("FCM", token)

            GlobalScope.launch {
                NetworkModule.authService.updateFcmToken(UpdateFcmTokenRequest(fcmToken = token))
            }
        })

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "mm_noti_channel"
            val descriptionText = "tung nui"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("mm_noti_channel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun initScreen() {
        supportFragmentManager.apply {
            beginTransaction().add(R.id.fragment_container, fragmentReport, "frag_report")
                .hide(fragmentReport).commit()
            beginTransaction().add(
                R.id.fragment_container,
                fragmentTransactions,
                "frag_transactions"
            )
                .commit()
        }

        setSupportActionBar(binding.toolbar)
        setUpBottomNav()
        registerObservers()
        registerClickListener()

        rangeSelectDialog = createRangeSelectDialog(this) { startDate, endDate ->
            viewModel.onSelectCustomTimeRange(startDate, endDate)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.transactions_frag_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.range_month_item -> {
                viewModel.onTimeRangeChanged(TimeRange.MONTH)
            }
            R.id.range_week_item -> {
                viewModel.onTimeRangeChanged(TimeRange.WEEK)
            }
            R.id.range_year_item -> {
                viewModel.onTimeRangeChanged(TimeRange.YEAR)
            }

            R.id.range_custom_item -> showRangeSelectDialog(
                this,
                rangeSelectDialog,
                viewModel.startTime,
                viewModel.endTime
            )
            R.id.switch_view_mode_item -> {
                val newViewMode = viewModel.switchViewMode()

                if (newViewMode == ViewType.TRANSACTION)
                    item.title = getString(R.string.view_by) + " " + getString(R.string.category)
                else
                    item.title = getString(R.string.view_by) + " " + getString(R.string.transaction)
            }

            R.id.edit_wallet_item -> {

            }

            R.id.change_pie_mode -> return false

            R.id.upgrade_premium -> {
                val intent = Intent(this, PayPalActivity::class.java)
                startActivity(intent)
            }

            R.id.logout -> LoginActivity.logout()
        }

        return true
    }

    private fun setUpBottomNav() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.transactionsFragment -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(fragmentTransactions).commit()
                    activeFragment = fragmentTransactions
                    if (binding.tabLayout.visibility == View.GONE)
                        binding.tabLayout.visibility = View.VISIBLE
                    true
                }

                R.id.reportFragment -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(fragmentReport).commit()
                    activeFragment = fragmentReport
                    if (binding.tabLayout.visibility == View.GONE)
                        binding.tabLayout.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }
    }

    private fun registerClickListener() {
        //add transaction
        binding.addTransactionFab.setOnClickListener {
            val addTransaction = Intent(this@MainActivity, TransactionDetailActivity::class.java)
            addTransaction.putExtra(
                TransactionDetailActivity.WALLET_ID_PARAM,
                viewModel.currentWallet.value?.id
            )
            startActivity(addTransaction)
        }

        //select wallet
        binding.walletImg.setOnClickListener {
            val selectWalletActivity = Intent(this@MainActivity, SelectWalletActivity::class.java)
            startActivity(selectWalletActivity)
        }
    }

    private fun registerObservers() {
        viewModel.currentWallet.observe(this)
        {
            if (it != null) {
                binding.walletName.text = it.name
                binding.walletBalance.text = NumberFormatter.format(it.amount) + " " + it.currency
                binding.walletImg.setImageResource(it.imageId)

                if (!viewModel.initTabs) {
                    viewModel.initTabs = true
                    viewModel.getTabInfoList()
                }
            }
        }
    }

    fun getTabLayout(): TabLayout = binding.tabLayout
}