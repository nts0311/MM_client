package com.sonnt.moneymanagement.features.premium

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paypal.checkout.PayPalCheckout.setConfig
import com.paypal.checkout.approve.Approval
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.createorder.*
import com.paypal.checkout.merchanttoken.UpgradeLsatTokenResponse
import com.paypal.checkout.order.*
import com.paypal.checkout.paymentbutton.PayPalButton
import com.sonnt.moneymanagement.R

class PayPalActivity : AppCompatActivity() {
    private var payPalButton: PayPalButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paypal)
        val config = CheckoutConfig(
            application,
            YOUR_CLIENT_ID,
            Environment.SANDBOX,
            String.format("%s://paypalpay", "com.sonnt.moneymanagement"),
            CurrencyCode.USD,
            UserAction.PAY_NOW
        )
        setConfig(config)
        payPalButton = findViewById(R.id.payPalButton)
        payPalButton?.setup(
            object : CreateOrder {
                override fun create(createOrderActions: CreateOrderActions) {
                    val purchaseUnits = ArrayList<PurchaseUnit>()
                    purchaseUnits.add(
                        PurchaseUnit.Builder()
                            .amount(
                                Amount.Builder()
                                    .currencyCode(CurrencyCode.USD)
                                    .value(amount)
                                    .build()
                            )
                            .build()
                    )
                    val order = Order(
                        OrderIntent.CAPTURE,
                        AppContext.Builder()
                            .userAction(UserAction.PAY_NOW)
                            .build(),
                        purchaseUnits,
                        null
                    )
                    createOrderActions.create(order, null as CreateOrderActions.OnOrderCreated?)
                }
            },
            object : OnApprove {
                override fun onApprove(approval: Approval) {
                    approval.orderActions.capture(object : OnCaptureComplete {
                        override fun onCaptureComplete(result: CaptureOrderResult) {
                            Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result))
                            if (result.javaClass == CaptureOrderResult.Success::class.java) {
                                upgradeAccount()
                                val toast = Toast.makeText(
                                    applicationContext,
                                    "Purchase succesfull",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                                finish()
                            } else {
                                val toast = Toast.makeText(
                                    applicationContext,
                                    "Fail to purchase",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }
                        }
                    })
                }
            }
        )
    }

    private fun upgradeAccount() {
        //send to server
    }

    //get from server
    private val amount: String
        private get() =//get from server
            "25.0"

    companion object {
        private const val YOUR_CLIENT_ID =
            "AcdyCm1FnbbwW8h831tVIUkcPeQF547FnDuXgHqNquFK0K5-8-1a0kkHFhfAKtDww9pnhd1Iwe6BNEak"
    }
}