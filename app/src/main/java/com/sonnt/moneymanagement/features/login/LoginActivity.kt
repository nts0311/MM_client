package com.sonnt.moneymanagement.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.sonnt.moneymanagement.MMApplication
import com.sonnt.moneymanagement.R
import com.sonnt.moneymanagement.data.repositories.AuthRepository
import com.sonnt.moneymanagement.databinding.ActivityLoginBinding
import com.sonnt.moneymanagement.databinding.ActivityMainBinding
import com.sonnt.moneymanagement.features.main_activity.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setup()
    }

    private fun setup() {
        binding.btnLogin.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            lifecycleScope.launch {
                AuthRepository.login(username, password).collect {
                    if (it != null) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this@LoginActivity, "Wrong auth info", Toast.LENGTH_LONG).show()
                    }
                }
            }


        }
    }

    companion object {
        fun logout() {
            if(MMApplication.currentActivity!!.javaClass == LoginActivity::class.java)
                return

            val intent = Intent(MMApplication.self, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            MMApplication.self.startActivity(intent)
        }
    }
}