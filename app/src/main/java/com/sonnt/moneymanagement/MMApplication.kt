package com.sonnt.moneymanagement

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.sonnt.moneymanagement.data.network.NetworkModule
import com.sonnt.moneymanagement.data.network.request.UpdateFcmTokenRequest
import com.sonnt.moneymanagement.features.base.BaseActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MMApplication: Application(), Application.ActivityLifecycleCallbacks {
    override fun onCreate() {
        super.onCreate()
        initSelf()



        self = this
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        currentActivity = p0
    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }

    private fun initSelf() {
        this.registerActivityLifecycleCallbacks(this)
    }

    companion object {
        var currentActivity: Activity? = null
        lateinit var self: Context


    }
}